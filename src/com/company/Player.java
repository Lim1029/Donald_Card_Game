package com.company;

import java.util.ArrayList;

enum Team{
    DONALD, OTHERS
}

public class Player {
    String name;
    int number;
    ArrayList<Card> cards;
    Team team = Team.OTHERS;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        cards = new ArrayList<>();
    }

    String checkCard(){
        String cardList = "";
        for (Card card: cards){
            cardList += String.format("%s %s | ",card.colour,card.value);
        }
//        cardList += "\n";
        return cardList;
    }
}
