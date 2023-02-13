package com.salesianostriana.meal.model.dto.plato;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.Positive;

@Builder
@Value
public class RateRequestDTO {

    @Positive
    private double nota;
    private String comentario;

}
