package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Valoracion;
import com.salesianostriana.meal.model.dto.plato.PlatoRequestDTO;
import com.salesianostriana.meal.model.dto.plato.RateRequestDTO;
import com.salesianostriana.meal.repository.PlatoRepository;
import com.salesianostriana.meal.repository.ValoracionRepository;
import com.salesianostriana.meal.search.Criteria;
import com.salesianostriana.meal.search.SpecBuilder;
import com.salesianostriana.meal.search.Utilities;
import com.salesianostriana.meal.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository repository;
    private final ValoracionRepository valoracionRepository;

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

    public void deleteById(UUID id){
        repository.deleteById(id);
    }

    public Page<Plato> findByRestaurant(UUID id, Pageable pageable){
        Page<Plato> result = repository.findByRestaurant(id ,pageable);
        if(result.getTotalElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

    public Plato edit(UUID id, PlatoRequestDTO platoRequestDTO){
        return repository.findById(id).map(p -> {
            p.setDescripcion(platoRequestDTO.getDescripcion());
            p.setPrecio(platoRequestDTO.getPrecio());
            p.setNombre(platoRequestDTO.getNombre());
            p.setImgUrl(platoRequestDTO.getImgUrl());
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Page<Plato> search(List<Criteria> criterios, Pageable pageable){
        SpecBuilder<Plato> builder = new SpecBuilder<>(criterios, Plato.class);
        Specification<Plato> spec = builder.build();
        return repository.findAll(spec, pageable);
    }

    public Plato rate(UUID id, RateRequestDTO rateRequestDTO, User loggedUser) {
        return repository.findFirstById(id).map(p -> {
            Valoracion nueva = Valoracion.builder()
                    .nota(rateRequestDTO.getNota())
                    .comentario(rateRequestDTO.getComentario())
                    .build();
            nueva.addUser(loggedUser);
            nueva.addPlato(p);
            valoracionRepository.save(nueva);
            p.setValoracionMedia(p.getValoraciones().stream().mapToDouble(v -> v.getNota()).sum() / p.getValoraciones().size());
            return repository.save(p);
        }).orElseThrow(() -> new EntityNotFoundException());
    }
}
