package com.salesianostriana.meal.model.dto.plato;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.view.View;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PlatoResponseDTO {

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class})
    private String nombre;

    @JsonView({View.PlatoView.PlatoDetailView.class})
    private String descripcion;

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class})
    private double precio;

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class})
    private String imgUrl;

    public static PlatoResponseDTO of(Plato plato){
        return PlatoResponseDTO.builder()
                .nombre(plato.getNombre())
                .descripcion(plato.getDescripcion())
                .precio(plato.getPrecio())
                .imgUrl(plato.getImgUrl())
                .build();
    }

}
