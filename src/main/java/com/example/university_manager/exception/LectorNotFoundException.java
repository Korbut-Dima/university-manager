package com.example.university_manager.exception;

public class LectorNotFoundException extends RuntimeException{
    public LectorNotFoundException(String message) {
        super(message);
    }
}
