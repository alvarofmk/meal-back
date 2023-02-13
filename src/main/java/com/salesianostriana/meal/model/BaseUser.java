package com.salesianostriana.meal.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BaseUser {

    private String nombre;
    private String email;
    private String username;

}
