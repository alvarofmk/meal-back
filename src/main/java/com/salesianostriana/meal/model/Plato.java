package com.salesianostriana.meal.model;

import com.salesianostriana.meal.model.converter.IngredienteConverter;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
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
public class Plato {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(columnDefinition = "uuid")
    private UUID id;

    private String nombre;
    private String descripcion;
    private double precio;
    private String imgUrl;

    @Builder.Default
    @Convert(converter = IngredienteConverter.class)
    private List<String> ingredientes = new ArrayList<>();
    private boolean sinGluten;

    @ManyToOne
    private Restaurante restaurante;

    public void addRestaurante(Restaurante restaurante){
        this.restaurante = restaurante;
        restaurante.getPlatos().add(this);
    }

    public void removeRestaurante(){
        restaurante.getPlatos().remove(this);
        this.restaurante = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plato plato = (Plato) o;
        return id.equals(plato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
