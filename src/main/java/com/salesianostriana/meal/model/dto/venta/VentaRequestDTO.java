package com.salesianostriana.meal.model.dto.venta;

import com.salesianostriana.meal.model.BaseUser;
import com.salesianostriana.meal.model.LineaVenta;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@Value
public class VentaRequestDTO {

    private UUID idUser;
    private List<LineaVentaRequestDTO> detalle;

}
