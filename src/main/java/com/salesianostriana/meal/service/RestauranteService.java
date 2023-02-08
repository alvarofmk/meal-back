package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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

    public Restaurante add(Restaurante restaurante){
        return repository.save(restaurante);
    }
}
