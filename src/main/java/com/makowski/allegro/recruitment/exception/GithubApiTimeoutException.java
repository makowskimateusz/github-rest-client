package com.makowski.allegro.recruitment.exception;

/**
 * Created by Mateusz Makowski on 11.11.2016.
 */
public class GithubApiTimeoutException extends RuntimeException {
    public GithubApiTimeoutException(String message){
        super(message);
    }
}
