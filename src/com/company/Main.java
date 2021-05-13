package com.company;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here

        Scanner input = new Scanner(System.in);

        Player[] playerList = new Player[4];
        ArrayList<Card> cardDeck = new ArrayList<>();

        String[] allValue = {"1","2","3","4","5","6","7","8","9","10","A","B","C"};
        Color[] allColors = Color.values();
        for (Color color: allColors){
            for (String value: allValue)
                cardDeck.add(new Card(color,value));
        }

        System.out.println("Welcome to Donald Card game!");

        playerList = nameInput(input);

    }

    static Player[] nameInput(Scanner input){
        Player[] playerList = new Player[4];
        for (int i=0; i<4; i++){
            Player player1 = new Player();
            System.out.printf("Player %d, please enter your name: ", i+1);
            String name = input.nextLine();
            while (name.isBlank()){
                System.out.println("Your input is invalid, please try again.");
                System.out.printf("Player %d, please enter your name: ", i+1);
                name = input.nextLine();
            }
            player1.name = name;
            playerList[i] = player1;
        }
        return playerList;
    }
}
