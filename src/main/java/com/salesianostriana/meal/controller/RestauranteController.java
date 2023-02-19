package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.Restaurante;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteResponseDTO;
import com.salesianostriana.meal.model.dto.restaurante.RestauranteRequestDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.service.RestauranteFinderService;
import com.salesianostriana.meal.service.RestauranteService;
import com.salesianostriana.meal.service.storage.MediaUrlResource;
import com.salesianostriana.meal.service.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/restaurante")
@RequiredArgsConstructor
public class RestauranteController {

    private final RestauranteService service;
    private final RestauranteFinderService finderService;
    private final StorageService storageService;

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
    public ResponseEntity<?> create(@AuthenticationPrincipal User loggedUser,
                                                         @RequestPart("file") MultipartFile file,
                                                         @Valid @RequestPart("body") RestauranteRequestDTO restauranteDto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestauranteResponseDTO.of(service.add(restauranteDto.toRestaurante(), loggedUser, file)));
    }

    @GetMapping("/{id}/img/")
    public ResponseEntity<Resource> getImage(@PathVariable UUID id){
        Restaurante restaurante = finderService.findById(id);
        if(restaurante.getCoverImgUrl() == null) throw new EntityNotFoundException();
        MediaUrlResource resource =
                (MediaUrlResource) storageService.loadAsResource(restaurante.getCoverImgUrl());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

    @PutMapping("/{id}/img/")
    public RestauranteResponseDTO changeImage(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @RequestPart("file") MultipartFile file){
        return RestauranteResponseDTO.of(service.changeImg(loggedUser, id, file));
    }

    @DeleteMapping("/{id}/img/")
    public RestauranteResponseDTO deleteImg(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        return RestauranteResponseDTO.of(service.deleteImg(loggedUser, id));
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

    @GetMapping("/managed")
    @JsonView(View.RestauranteView.RestauranteGenericView.class)
    public PageDTO<RestauranteResponseDTO> findManaged(@AuthenticationPrincipal User loggedUser,
                                                       @PageableDefault(page = 0, size = 10) Pageable pageable){
        PageDTO<RestauranteResponseDTO> result = new PageDTO<>();
        return result.of(service.findManaged(loggedUser, pageable).map(RestauranteResponseDTO::of));
    }

}
