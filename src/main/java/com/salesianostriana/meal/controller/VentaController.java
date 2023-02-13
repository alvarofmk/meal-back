package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.venta.VentaRequestDTO;
import com.salesianostriana.meal.model.dto.venta.VentaResponseDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/venta")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService service;

    @GetMapping("/")
    public PageDTO<VentaResponseDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<VentaResponseDTO> result = new PageDTO<>();
        return result.of(service.findAll(pageable).map(VentaResponseDTO::of));
    }

    @GetMapping("/{id}")
    public VentaResponseDTO findById(@PathVariable UUID id){
        return VentaResponseDTO.of(service.findById(id));
    }
/*
    @GetMapping("/user/{id}")
    public VentaResponseDTO findByUser(@PathVariable UUID id){
        return VentaResponseDTO.of(service.findByUser(id));
    }
*/

    @PostMapping("/")
    public VentaResponseDTO create(@Valid @RequestBody VentaRequestDTO ventaDto){
        return VentaResponseDTO.of(service.proccessVenta(ventaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
/*
    @PutMapping("/{id}")
    public VentaResponseDTO edit(@PathVariable UUID id, @Valid @RequestBody VentaRequestDTO ventaDto){
        return VentaResponseDTO.of(service.edit(id, ventaDto));
    }
  */
}
