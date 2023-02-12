package com.salesianostriana.meal.controller;

import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.Plato.GenericPlatoResponseDTO;
import com.salesianostriana.meal.model.dto.Plato.PlatoRequestDTO;
import com.salesianostriana.meal.model.dto.Plato.SinglePlatoResponseDTO;
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
    public PageDTO<GenericPlatoResponseDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<GenericPlatoResponseDTO> result = new PageDTO<>();
        return result.of(service.findAll(pageable).map(GenericPlatoResponseDTO::of));
    }

    @GetMapping("/{id}")
    public SinglePlatoResponseDTO findById(@PathVariable UUID id){
        return SinglePlatoResponseDTO.of(service.findById(id));
    }

    @PostMapping("/")
    public SinglePlatoResponseDTO create(@Valid @RequestBody PlatoRequestDTO PlatoDto){
        return SinglePlatoResponseDTO.of(service.add(PlatoDto.toPlato()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public GenericPlatoResponseDTO edit(@PathVariable UUID id, @Valid @RequestBody PlatoRequestDTO PlatoDto){
        return GenericPlatoResponseDTO.of(service.edit(id, PlatoDto));
    }
    
}
