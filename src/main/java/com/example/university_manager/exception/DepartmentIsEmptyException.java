package com.example.university_manager.exception;

public class DepartmentIsEmptyException extends RuntimeException{
    public DepartmentIsEmptyException(String message) {
        super(message);
    }
}
