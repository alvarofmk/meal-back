package com.salesianostriana.meal.model;

import com.salesianostriana.meal.model.key.LineaVentaPK;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LineaVenta {

    @EmbeddedId
    private LineaVentaPK lineaVentaPK = new LineaVentaPK();

    @ManyToOne
    @MapsId("platoId")
    private Plato plato;
    private int cantidad;
    private double subTotal;

    @ManyToOne
    @MapsId("ventaId")
    private Venta venta;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineaVenta that = (LineaVenta) o;
        return lineaVentaPK.equals(that.lineaVentaPK);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lineaVentaPK);
    }
}
