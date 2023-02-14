package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.repository.RestauranteRepository;
import com.salesianostriana.meal.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestauranteService {

    private final RestauranteRepository repository;

    public Page<Restaurante> findAll(Pageable pageable){
        Page<Restaurante> result = repository.findAll(pageable);
        if(result.getTotalElements() == 0){
            throw new EntityNotFoundException();
        }
        return result;
    }

    public Restaurante findById(UUID id) {
        return repository.findById(id).orElseThrow(() ->new EntityNotFoundException());
    }

    public Restaurante add(Restaurante restaurante, User loggedUser){
        restaurante.setRestaurantAdmin(loggedUser);
        return repository.save(restaurante);
    }

    public void deleteById(UUID id, User loggedUser){
        Optional<Restaurante> restaurante = repository.findById(id);
        if(restaurante.isEmpty()) {
            throw new EntityNotFoundException();
        }else if(!restaurante.get().getRestaurantAdmin().equals(loggedUser)){

        }

        repository.deleteById(id);
    }

    public Restaurante edit(UUID id, RestauranteRequestDTO restauranteRequestDTO){
        return repository.findById(id).map(r -> {
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
