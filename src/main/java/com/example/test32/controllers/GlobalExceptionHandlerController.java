package com.example.test32.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "An error occurred: " + ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ModelAndView handleUnsupportedMediaTypeException(UnsupportedMediaTypeException ex) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage", "Content-Type '" + ex.getContentType() + "' is not supported.");
        return modelAndView;
    }
}
