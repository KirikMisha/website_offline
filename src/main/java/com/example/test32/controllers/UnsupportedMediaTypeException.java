package com.example.test32.controllers;

public class UnsupportedMediaTypeException extends Throwable {
    public String getContentType() {
        return null;
    }
}
