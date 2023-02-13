package com.salesianostriana.meal.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BaseUser {

    private String nombre;
    private String email;
    @Id
    private String username;

    @ManyToMany
    @Builder.Default
    private List<Plato> favoritos = new ArrayList<>();
    

}
