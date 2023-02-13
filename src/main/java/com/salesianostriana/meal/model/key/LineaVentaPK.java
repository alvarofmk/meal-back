package com.salesianostriana.meal.model.key;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class LineaVentaPK implements Serializable {

    private UUID ventaId;
    private UUID platoId;

}
