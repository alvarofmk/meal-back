package com.salesianostriana.meal.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

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

}
