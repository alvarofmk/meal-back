package com.salesianostriana.meal.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Venta {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Builder.Default
    private LocalDateTime fecha = LocalDateTime.now();

    @ManyToOne
    private BaseUser baseUser;

    @OneToMany(mappedBy = "venta", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<LineaVenta> detalle = new ArrayList<>();
    private double total;

    public void addLinea(LineaVenta linea){
        detalle.add(linea);
        linea.setVenta(this);
    }

    public void removeLinea(LineaVenta lineaVenta){
        lineaVenta.setVenta(null);
        detalle.remove(lineaVenta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venta venta = (Venta) o;
        return id.equals(venta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
