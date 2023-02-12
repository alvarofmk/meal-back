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

    @NotBlank(message = "{RestauranteRequestDTO.nombre.notBlank}")
    private String nombre;

    @NotBlank(message = "{RestauranteRequestDTO.descripcion.notBlank}")
    @Size(max = 200, message = "{RestauranteRequestDTO.descripcion.size}")
    private String descripcion;

    @URL(message = "{RestauranteRequestDTO.coverImgUrl}")
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
