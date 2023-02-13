package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoRequestDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.service.PlatoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService service;


    @GetMapping("/")
    @JsonView(View.PlatoView.PlatoGenericView.class)
    public PageDTO<PlatoResponseDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<PlatoResponseDTO> result = new PageDTO<>();
        return result.of(service.findAll(pageable).map(PlatoResponseDTO::of));
    }

    @JsonView(View.PlatoView.PlatoDetailView.class)
    @GetMapping("/{id}")
    public PlatoResponseDTO findById(@PathVariable UUID id){
        return PlatoResponseDTO.of(service.findById(id));
    }

    @JsonView(View.PlatoView.PlatoGenericView.class)
    @GetMapping("/restaurante/{id}")
    public PageDTO<PlatoResponseDTO> findByRestaurant(@PageableDefault(page = 0, size = 10) Pageable pageable, @PathVariable UUID id){
        PageDTO<PlatoResponseDTO> result = new PageDTO<>();
        return result.of(service.findByRestaurant(id, pageable).map(PlatoResponseDTO::of));
    }

    @JsonView(View.PlatoView.PlatoDetailView.class)
    @PostMapping("/")
    public PlatoResponseDTO create(@Valid @RequestBody PlatoRequestDTO PlatoDto){
        return PlatoResponseDTO.of(service.add(PlatoDto.toPlato()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public PlatoResponseDTO edit(@PathVariable UUID id, @Valid @RequestBody PlatoRequestDTO PlatoDto){
        return PlatoResponseDTO.of(service.edit(id, PlatoDto));
    }
    
}
