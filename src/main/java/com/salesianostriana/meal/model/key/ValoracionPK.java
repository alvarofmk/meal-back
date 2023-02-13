package com.salesianostriana.meal.model.key;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class ValoracionPK implements Serializable {

    public UUID userId;
    public UUID platoId;

}
