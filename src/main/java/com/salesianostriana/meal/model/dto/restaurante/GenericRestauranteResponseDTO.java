package com.salesianostriana.meal.model.dto.restaurante;

import com.salesianostriana.meal.model.Restaurante;
import lombok.Builder;
import lombok.Value;

import java.time.LocalTime;

@Builder
@Value
public class GenericRestauranteResponseDTO {

    private String nombre;
    private String coverImgUrl;
    private LocalTime apertura;
    private LocalTime cierre;

    public static GenericRestauranteResponseDTO of(Restaurante restaurante){
        return GenericRestauranteResponseDTO.builder()
                .nombre(restaurante.getNombre())
                .coverImgUrl(restaurante.getCoverImgUrl())
                .apertura(restaurante.getApertura())
                .cierre(restaurante.getCierre())
                .build();
    }

}
