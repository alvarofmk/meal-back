package com.salesianostriana.meal.service;

import com.salesianostriana.meal.repository.PlatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlatoService {

    private final PlatoRepository repository;

}
