package com.salesianostriana.meal.service;

import com.salesianostriana.meal.error.exception.NotOwnerException;
import com.salesianostriana.meal.error.exception.RestaurantInUseException;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.repository.RestauranteRepository;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.security.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository repository;
    private final PlatoService platoService;
    private final UserService userService;

    public Page<Restaurante> findAll(Pageable pageable){
        Page<Restaurante> result = repository.findAll(pageable);
        if(result.getTotalElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

    public Restaurante add(Restaurante restaurante, User loggedUser){
        restaurante.setRestaurantAdmin(loggedUser);
        return repository.save(restaurante);
    }

    @Transactional
    public void deleteById(UUID id, final  User loggedUser){
        repository.findById(id).map(r -> {
            userService.checkOwnership(r, loggedUser.getId());
            if(platoService.hasAnyPlatos(r)){
                throw new RestaurantInUseException();
            }
            repository.deleteById(id);
            return r;
        });
    }

    @Transactional
    public Restaurante edit(UUID id, RestauranteRequestDTO restauranteRequestDTO, final User loggedUser){
        return repository.findById(id).map(r -> {
            userService.checkOwnership(r, loggedUser.getId());
            r.setApertura(restauranteRequestDTO.getApertura());
            r.setCierre(restauranteRequestDTO.getCierre());
            r.setNombre(restauranteRequestDTO.getNombre());
            r.setDescripcion(restauranteRequestDTO.getDescripcion());
            r.setCoverImgUrl(restauranteRequestDTO.getCoverImgUrl());
            return repository.save(r);
        }).orElseThrow(() -> new EntityNotFoundException());
    }

    public Restaurante findWithMenu(UUID id) {
        return repository.findOneWithMenu(id).orElseThrow(() -> new EntityNotFoundException());
    }
}
