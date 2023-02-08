package com.salesianostriana.meal.controller;

import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteResponseDTO;
import com.salesianostriana.meal.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;
    @GetMapping("/")
    public Page<Restaurante> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return service.findAll(pageable);
    }

}
