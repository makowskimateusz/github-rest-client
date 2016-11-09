package com.makowski.allegro.recruitment.exception;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
public class NotFoundRepositoryOrUserException extends RuntimeException {
    public NotFoundRepositoryOrUserException(String message) {
        super(message);
    }
}
