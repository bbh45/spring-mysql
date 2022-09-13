package com.example.demomysql.util;

public class BadPersonRequestException extends Exception {

    String message;
    public BadPersonRequestException(String message){
        this.message = message;
    }
}
