package com.salesianostriana.meal.error.exception;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(){
        super("La contraseña no es correcta");
    }

}
