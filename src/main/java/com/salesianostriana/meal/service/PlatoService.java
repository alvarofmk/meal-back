package com.salesianostriana.meal.service;

import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.repository.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository repository;

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
    

}
