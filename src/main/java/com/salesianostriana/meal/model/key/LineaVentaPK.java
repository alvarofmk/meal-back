package com.salesianostriana.meal.model.key;

import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class LineaVentaPK {

    private UUID ventaId;
    private UUID platoId;

}
