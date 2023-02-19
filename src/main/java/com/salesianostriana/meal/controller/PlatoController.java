package com.salesianostriana.meal.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.error.exception.InvalidSearchException;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.dto.PageDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import com.salesianostriana.meal.model.dto.plato.PlatoRequestDTO;
import com.salesianostriana.meal.model.dto.plato.RateRequestDTO;
import com.salesianostriana.meal.model.view.View;
import com.salesianostriana.meal.search.Criteria;
import com.salesianostriana.meal.search.Utilities;
import com.salesianostriana.meal.security.user.User;
import com.salesianostriana.meal.service.PlatoService;
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
import java.security.InvalidAlgorithmParameterException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/plato")
@RequiredArgsConstructor
public class PlatoController {

    private final PlatoService service;
    private final StorageService storageService;


    @GetMapping("/")
    @JsonView(View.PlatoView.PlatoGenericView.class)
    public PageDTO<PlatoResponseDTO> search(@RequestParam(value = "search", defaultValue = "")String search,
                                             @PageableDefault(page = 0, size = 10) Pageable pageable){
        List<Criteria> criterios = Utilities.extractCriteria(search);
        if (!criterios.stream().allMatch(c -> Utilities.checkParam(c.getKey(), Plato.class)))
            throw new InvalidSearchException();
        PageDTO<PlatoResponseDTO> result = new PageDTO<>();
        return result.of(service.search(criterios, pageable).map(PlatoResponseDTO::of));
    }

    @JsonView(View.PlatoView.PlatoGenericView.class)
    @GetMapping("/restaurante/{id}")
    public PageDTO<PlatoResponseDTO> findByRestaurant(@RequestParam(value = "search", defaultValue = "")String search,
                                                      @PageableDefault(page = 0, size = 10) Pageable pageable, @PathVariable UUID id){
        List<Criteria> criterios = Utilities.extractCriteria(search);
        criterios.add(new Criteria("restaurante", ":", id));
        if (!criterios.stream().allMatch(c -> Utilities.checkParam(c.getKey(), Plato.class)))
            throw new InvalidSearchException();
        PageDTO<PlatoResponseDTO> result = new PageDTO<>();
        return result.of(service.search(criterios, pageable).map(PlatoResponseDTO::of));
    }

    @JsonView(View.PlatoView.PlatoDetailView.class)
    @GetMapping("/{id}")
    public PlatoResponseDTO findById(@PathVariable UUID id){
        return PlatoResponseDTO.of(service.findById(id));
    }

    @GetMapping("/{id}/img/")
    public ResponseEntity<Resource> getImage(@PathVariable UUID id){
        Plato plato = service.findById(id);
        if(plato.getImgUrl() == null) throw new EntityNotFoundException();
        MediaUrlResource resource =
                (MediaUrlResource) storageService.loadAsResource(plato.getImgUrl());

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", resource.getType())
                .body(resource);
    }

    @PutMapping("/{id}/img/")
    public PlatoResponseDTO changeImage(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @RequestPart("file") MultipartFile file){
        return PlatoResponseDTO.of(service.changeImg(loggedUser, id, file));
    }

    @DeleteMapping("/{id}/img/")
    public PlatoResponseDTO deleteImg(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        return PlatoResponseDTO.of(service.deleteImg(loggedUser, id));
    }

    @JsonView(View.PlatoView.PlatoDetailView.class)
    @PostMapping("/{restaurantId}")
    public ResponseEntity<PlatoResponseDTO> create(@AuthenticationPrincipal User loggedUser,
                                   @RequestPart("file") MultipartFile file,
                                   @Valid @RequestPart("body") PlatoRequestDTO PlatoDto,
                                   @PathVariable UUID restaurantId){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(PlatoResponseDTO.of(service.add(PlatoDto.toPlato(), restaurantId, loggedUser, file)));
    }

    @PutMapping("/{id}")
    public PlatoResponseDTO edit(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody PlatoRequestDTO PlatoDto){
        return PlatoResponseDTO.of(service.edit(id, PlatoDto, loggedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        service.deleteById(id, loggedUser);
        return ResponseEntity.noContent().build();
    }


    @JsonView(View.PlatoView.PlatoDetailView.class)
    @PostMapping("/rate/{id}")
    public PlatoResponseDTO rate(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody RateRequestDTO rateDTO){
        return PlatoResponseDTO.of(service.rate(id, rateDTO, loggedUser));
    }

    @JsonView(View.PlatoView.PlatoDetailView.class)
    @DeleteMapping("/rate/{id}")
    public PlatoResponseDTO deleteRating(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id){
        return PlatoResponseDTO.of(service.deleteRating(id, loggedUser));
    }

    @JsonView(View.PlatoView.PlatoDetailView.class)
    @PutMapping("/rate/{id}")
    public PlatoResponseDTO changeRating(@AuthenticationPrincipal User loggedUser, @PathVariable UUID id, @Valid @RequestBody RateRequestDTO rateDTO){
        return PlatoResponseDTO.of(service.changeRating(id, loggedUser, rateDTO));
    }


}
