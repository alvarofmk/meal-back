package com.salesianostriana.meal.model.dto.restaurante;

import com.salesianostriana.meal.model.Restaurante;
import lombok.Builder;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Builder
@Value
public class RestauranteRequestDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    @Size(max = 200)
    private String descripcion;

    @URL
    private String coverImgUrl;
    private LocalTime apertura;
    private LocalTime cierre;

    public Restaurante toRestaurante(){
        return Restaurante.builder()
                .nombre(this.nombre)
                .descripcion(this.descripcion)
                .coverImgUrl(this.coverImgUrl)
                .apertura(this.apertura)
                .cierre(this.cierre)
                .build();
    }

}
