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
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
        return PlatoResponseDTO.of(service.rate(id, rateDTO, loggedUser));
    }


}
