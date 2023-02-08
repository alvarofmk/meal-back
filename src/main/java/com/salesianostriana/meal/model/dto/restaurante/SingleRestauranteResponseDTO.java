package com.salesianostriana.meal.model.dto.restaurante;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.Restaurante;
import lombok.Builder;
import lombok.Value;

import java.time.LocalTime;
import java.util.List;

@Builder
@Value
public class SingleRestauranteResponseDTO {

    private String nombre;
    private String descripcion;
    private String coverImgUrl;

    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Plato> platos;
    private LocalTime apertura;
    private LocalTime cierre;

    public static SingleRestauranteResponseDTO of(Restaurante restaurante){
        return SingleRestauranteResponseDTO.builder()
                .nombre(restaurante.getNombre())
                .coverImgUrl(restaurante.getCoverImgUrl())
                .apertura(restaurante.getApertura())
                .cierre(restaurante.getCierre())
                .descripcion(restaurante.getDescripcion())
                .platos(restaurante.getPlatos())
                .build();
    }

}
