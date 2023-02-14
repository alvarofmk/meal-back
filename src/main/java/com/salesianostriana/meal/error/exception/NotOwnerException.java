package com.salesianostriana.meal.error.exception;

public class NotOwnerException extends BadRequestException{
    public NotOwnerException(String s) {
        super("Solo se pueden gestionar los restaurantes que se hayan creado");
    }
}
