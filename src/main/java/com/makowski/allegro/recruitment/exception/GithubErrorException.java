package com.makowski.allegro.recruitment.exception;

/**
 * Created by Mateusz Makowski on 13.11.2016.
 */
public class GithubErrorException extends RuntimeException {
    public GithubErrorException(String message) {
        super(message);
    }
}
