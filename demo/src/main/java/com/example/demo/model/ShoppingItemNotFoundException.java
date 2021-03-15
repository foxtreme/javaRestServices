package com.example.demo.model;

class ShoppingItemNotFoundException extends RuntimeException{
    ShoppingItemNotFoundException(Long id){
        super("Could not find Shopping Item "+id);
    }
}
