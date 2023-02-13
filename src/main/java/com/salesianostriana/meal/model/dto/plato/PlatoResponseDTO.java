package com.salesianostriana.meal.model.dto.plato;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.view.View;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Value
public class PlatoResponseDTO {

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class, View.RestauranteView.RestauranteDetailView.class})
    private UUID id;

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class, View.RestauranteView.RestauranteDetailView.class})
    private String nombre;

    @JsonView({View.PlatoView.PlatoDetailView.class, View.RestauranteView.RestauranteDetailView.class})
    private String descripcion;

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class, View.RestauranteView.RestauranteDetailView.class})
    private double precio;

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class, View.RestauranteView.RestauranteDetailView.class})
    private String imgUrl;

    @JsonView({View.PlatoView.PlatoDetailView.class, View.RestauranteView.RestauranteDetailView.class})
    private List<String> ingredientes;

    @JsonView({View.PlatoView.PlatoGenericView.class, View.PlatoView.PlatoDetailView.class, View.RestauranteView.RestauranteDetailView.class})
    private boolean sinGluten;

    public static PlatoResponseDTO of(Plato plato){
        return PlatoResponseDTO.builder()
                .id(plato.getId())
                .nombre(plato.getNombre())
                .descripcion(plato.getDescripcion())
                .precio(plato.getPrecio())
                .imgUrl(plato.getImgUrl())
                .ingredientes(plato.getIngredientes())
                .sinGluten(plato.isSinGluten())
                .build();
    }

}
