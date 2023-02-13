package com.salesianostriana.meal.error.exception;

public class InvalidSearchException extends RuntimeException{

    public InvalidSearchException(){
        super("Los parámetros de búsqueda no son válidos");
    }

}
