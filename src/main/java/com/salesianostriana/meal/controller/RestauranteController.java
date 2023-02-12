package com.salesianostriana.meal.controller;

import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.restaurante.GenericRestauranteResponseDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.model.dto.restaurante.SingleRestauranteResponseDTO;
import com.salesianostriana.meal.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;
    @GetMapping("/")
    public PageDTO<GenericRestauranteResponseDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<GenericRestauranteResponseDTO> result = new PageDTO<>();
        return result.of(service.findAll(pageable).map(GenericRestauranteResponseDTO::of));
    }

    @GetMapping("/{id}")
    public SingleRestauranteResponseDTO findById(@PathVariable UUID id){
        return SingleRestauranteResponseDTO.of(service.findById(id));
    }

    @PostMapping("/")
    public SingleRestauranteResponseDTO create(@Valid @RequestBody RestauranteRequestDTO restauranteDto){
        return SingleRestauranteResponseDTO.of(service.add(restauranteDto.toRestaurante()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public GenericRestauranteResponseDTO edit(@PathVariable UUID id, @Valid @RequestBody RestauranteRequestDTO restauranteDto){
        return GenericRestauranteResponseDTO.of(service.edit(id, restauranteDto));
    }

}
