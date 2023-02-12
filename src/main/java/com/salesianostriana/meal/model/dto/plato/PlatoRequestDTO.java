package com.salesianostriana.meal.model.dto.plato;

import com.salesianostriana.meal.model.Plato;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Builder
@Value
public class PlatoRequestDTO {

    @NotBlank(message = "")
    private String nombre;

    @NotBlank(message = "")
    private String descripcion;

    @Positive(message = "")
    private double precio;

    @URL(message = "")
    private String imgUrl;

    public Plato toPlato(){
        return Plato.builder()
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .imgUrl(imgUrl)
                .build();
    }

}
