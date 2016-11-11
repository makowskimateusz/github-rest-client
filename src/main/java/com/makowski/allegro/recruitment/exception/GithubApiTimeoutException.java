package com.makowski.allegro.recruitment.exception;

import java.net.SocketTimeoutException;

/**
 * Created by Mateusz Makowski on 11.11.2016.
 */
public class GithubApiTimeoutException extends SocketTimeoutException {
    public GithubApiTimeoutException(String message){
        super(message);
    }
}
