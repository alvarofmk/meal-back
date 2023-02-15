package com.salesianostriana.meal.service;

import com.salesianostriana.meal.error.exception.AlreadyRatedException;
import com.salesianostriana.meal.error.exception.NotOwnerException;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.dto.plato.PlatoRequestDTO;
import com.salesianostriana.meal.model.dto.plato.RateRequestDTO;
import com.salesianostriana.meal.model.key.ValoracionPK;
import com.salesianostriana.meal.repository.PlatoRepository;
import com.salesianostriana.meal.repository.ValoracionRepository;
import com.salesianostriana.meal.search.Criteria;
import com.salesianostriana.meal.search.SpecBuilder;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.service.UserService;
import com.salesianostriana.meal.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository repository;
    private final ValoracionRepository valoracionRepository;
    private final UserService userService;

    private final StorageService storageService;

    public Page<Plato> findAll(Pageable pageable){
        Page<Plato> result = repository.findAll(pageable);
        if(result.getTotalElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

    public Plato findById(UUID id) {
        return repository.findById(id).orElseThrow(() ->new EntityNotFoundException());
    }

    public Plato add(Plato plato){
        return repository.save(plato);
    }

    @Transactional
    public Plato add(Plato plato, UUID restaurantId, User loggedUser, MultipartFile file) {
        Optional<User> owner = userService.findAdminOf(loggedUser.getId());
        if(owner.isPresent()) {
            loggedUser = owner.get();
            List<Restaurante> restaurantes = loggedUser.getAdministra().stream().filter(r -> r.getId().equals(restaurantId)).toList();
            if (restaurantes.isEmpty()) {
                throw new NotOwnerException();
            }
            plato.setImgUrl(storageService.store(file));
            plato.addRestaurante(restaurantes.get(0));
            return repository.save(plato);
        }else throw new NotOwnerException();
    }

    @Transactional
    public void deleteById(UUID id, User loggedUser){
        Optional<User> owner = userService.findAdminOf(loggedUser.getId());
        if(owner.isPresent()) {
            loggedUser = owner.get();
            Optional<Plato> result = repository.findById(id);
            if (result.isPresent()){
                if (loggedUser.getAdministra().stream().noneMatch(r -> r.getId().equals(result.get().getRestaurante().getId()))) {
                    throw new NotOwnerException();
                }
                repository.deleteRatings(id);
                repository.deleteById(id);
            }
        }else throw new NotOwnerException();
    }

    public Page<Plato> searchByRestaurant(UUID id, Pageable pageable){
        Page<Plato> result = repository.findByRestaurant(id, pageable);
        if(result.getTotalElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

    @Transactional
    public Plato edit(UUID id, PlatoRequestDTO platoRequestDTO, User loggedUser) {
        Optional<User> owner = userService.findAdminOf(loggedUser.getId());
        if (owner.isPresent()) {
            User finalLoggedUser = owner.get();
            return repository.findById(id).map(p -> {
                if (finalLoggedUser.getAdministra().stream().noneMatch(r -> r.getId().equals(p.getRestaurante().getId()))) {
                    throw new NotOwnerException();
                }
                p.setDescripcion(platoRequestDTO.getDescripcion());
                p.setPrecio(platoRequestDTO.getPrecio());
                p.setNombre(platoRequestDTO.getNombre());
                p.setIngredientes(platoRequestDTO.getIngredientes());
                p.setSinGluten(platoRequestDTO.isSinGluten());
                return repository.save(p);
            }).orElseThrow(() -> new EntityNotFoundException());
        }else throw new NotOwnerException();
    }

    public Page<Plato> search(List<Criteria> criterios, Pageable pageable){
        SpecBuilder<Plato> builder = new SpecBuilder<>(criterios, Plato.class);
        Specification<Plato> spec = builder.build();
        return repository.findAll(spec, pageable);
    }

    public Plato rate(UUID id, RateRequestDTO rateRequestDTO, User loggedUser) {
        Optional<Valoracion> valOpt = valoracionRepository.findById(new ValoracionPK(loggedUser.getId(), id));
        if (valOpt.isPresent())
            throw new AlreadyRatedException();
        return repository.findFirstById(id).map(p -> {
            ValoracionPK pk = new ValoracionPK(loggedUser.getId(), p.getId());
            Valoracion nueva = Valoracion.builder()
                    .pk(pk)
                    .nota(rateRequestDTO.getNota())
                    .comentario(rateRequestDTO.getComentario())
                    .usuario(loggedUser)
                    .build();
            nueva.addPlato(p);
            valoracionRepository.save(nueva);
            p.setValoracionMedia(p.getValoraciones().stream().mapToDouble(v -> v.getNota()).sum() / p.getValoraciones().size());
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Plato deleteRating(UUID id, User loggedUser) {
        Optional<Valoracion> valOpt = valoracionRepository.findById(new ValoracionPK(loggedUser.getId(), id));
        if (valOpt.isEmpty())
            throw new EntityNotFoundException();
        Valoracion v = valOpt.get();
        return repository.findFirstById(id).map(p -> {
            p.getValoraciones().remove(v);
            p.setValoracionMedia(p.getValoraciones().stream().mapToDouble(val -> val.getNota()).sum() / p.getValoraciones().size());
            valoracionRepository.delete(v);
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Plato changeRating(UUID id, User loggedUser, RateRequestDTO rateRequestDTO) {
        Optional<Valoracion> valOpt = valoracionRepository.findById(new ValoracionPK(loggedUser.getId(), id));
        if (valOpt.isEmpty())
            throw new EntityNotFoundException();
        Valoracion v = valOpt.get();
        return repository.findFirstById(id).map(p -> {
            v.setComentario(rateRequestDTO.getComentario());
            v.setNota(rateRequestDTO.getNota());
            p.getValoraciones().remove(v);
            p.getValoraciones().add(v);
            p.setValoracionMedia(p.getValoraciones().stream().mapToDouble(val -> val.getNota()).sum() / p.getValoraciones().size());
            valoracionRepository.save(v);
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public boolean hasAnyPlatos(Restaurante restaurante){
        return repository.existsByRestaurante(restaurante);
    }

    public void changeImg(User loggedUser, UUID id, MultipartFile file) {
        repository.findById(id).map(p -> {

        }).orElseThrow(() -> new EntityNotFoundException());
    }
}
