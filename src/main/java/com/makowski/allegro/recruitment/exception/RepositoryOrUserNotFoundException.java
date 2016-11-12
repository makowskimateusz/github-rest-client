package com.makowski.allegro.recruitment.exception;

/**
 * Created by Mateusz Makowski on 09.11.2016.
 */
public class RepositoryOrUserNotFoundException extends RuntimeException {
    public RepositoryOrUserNotFoundException(String message) {
        super(message);
    }
}
