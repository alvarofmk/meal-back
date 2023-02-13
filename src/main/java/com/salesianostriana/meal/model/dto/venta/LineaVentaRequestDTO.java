package com.salesianostriana.meal.model.dto.venta;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class LineaVentaRequestDTO {

    private UUID idPlato;
    private int cantidad;

}
