package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteResponseDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;
    @GetMapping("/")
    @JsonView(View.RestauranteView.RestauranteGenericView.class)
    public PageDTO<RestauranteResponseDTO> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<RestauranteResponseDTO> result = new PageDTO<>();
        return result.of(service.findAll(pageable).map(RestauranteResponseDTO::of));
    }

    @GetMapping("/{id}")
    @JsonView(View.RestauranteView.RestauranteDetailView.class)
    public RestauranteResponseDTO findById(@PathVariable UUID id){
        return RestauranteResponseDTO.of(service.findWithMenu(id));
    }

    @PostMapping("/")
    @JsonView(View.RestauranteView.RestauranteDetailView.class)
    public RestauranteResponseDTO create(@AuthenticationPrincipal User loggedUser, @Valid @RequestBody RestauranteRequestDTO restauranteDto){
        return RestauranteResponseDTO.of(service.add(restauranteDto.toRestaurante(), loggedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        service.deleteById(id, loggedUser);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @JsonView(View.RestauranteView.RestauranteDetailView.class)
    public RestauranteResponseDTO edit(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody RestauranteRequestDTO restauranteDto){
        return RestauranteResponseDTO.of(service.edit(id, restauranteDto, loggedUser));
    }

}
