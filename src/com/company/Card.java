package com.company;

enum Color {
    GREEN, BlUE, YElLOW, RED
}

public class Card {
    String value;
    Color colour;

    public Card(Color colour, String value){
        this.colour = colour;
        this.value = value;
    }
}
