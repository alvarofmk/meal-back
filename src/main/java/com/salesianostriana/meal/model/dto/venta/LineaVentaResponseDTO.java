package com.salesianostriana.meal.model.dto.venta;

import com.salesianostriana.meal.model.LineaVenta;
import com.salesianostriana.meal.model.Plato;
import com.salesianostriana.meal.model.dto.plato.PlatoResponseDTO;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class LineaVentaResponseDTO {

    private PlatoResponseDTO plato;
    private int cantidad;
    private double subTotal;

    public static LineaVentaResponseDTO of(LineaVenta lineaVenta){
        return LineaVentaResponseDTO.builder()
                .plato(PlatoResponseDTO.of(lineaVenta.getPlato()))
                .cantidad(lineaVenta.getCantidad())
                .subTotal(lineaVenta.getSubTotal())
                .build();
    }

}
