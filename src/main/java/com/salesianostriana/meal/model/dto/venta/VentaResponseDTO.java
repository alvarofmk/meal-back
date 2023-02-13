package com.salesianostriana.meal.model.dto.venta;

import com.salesianostriana.meal.model.BaseUser;
import com.salesianostriana.meal.model.LineaVenta;
import com.salesianostriana.meal.model.Venta;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Value
public class VentaResponseDTO {

    private UUID id;
    private LocalDateTime fecha;
    private BaseUser baseUser;
    private List<LineaVentaResponseDTO> detalle;
    private double total;

    public static VentaResponseDTO of(Venta venta){
        return VentaResponseDTO.builder()
                .baseUser(venta.getBaseUser())
                .fecha(venta.getFecha())
                .id(venta.getId())
                .detalle(venta.getDetalle().stream().map(LineaVentaResponseDTO::of).toList())
                .total(venta.getTotal())
                .build();
    }

}
