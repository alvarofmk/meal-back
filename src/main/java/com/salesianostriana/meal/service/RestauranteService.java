package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

}
