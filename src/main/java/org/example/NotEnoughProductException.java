package org.example;

public class NotEnoughProductException extends RuntimeException{

    public NotEnoughProductException(String message) {
        super(message);
    }
}
