package com.company;

import java.util.*;

public class Main {
    static ArrayList<Card> cardDeck;
    static ArrayList<Player> playerList = new ArrayList<>();
    static ArrayList<Donald> donaldList = new ArrayList<>();
    static int maxDonaldNumber = 0;
    static Donald selectedDonald;
    static boolean noDonald = true;
    static Scanner input = new Scanner(System.in);
    static int teamDonaldScore;
    static int teamOthersScore;
    static int decidingScore;
    static int firstPlayerPos;
    static ArrayList<Player> playerOrder = new ArrayList<>();

    public static void main(String[] args) {


        initialisingGame();
        //donald round
        donaldRoundAndCheckCard();
        if (!noDonald)
            chooseDonald();
        pickDonalColorAndTeammate();

        //playing round
        System.out.println("Welcome to the playing phase, the game details are as below:");
        StringBuilder donald = new StringBuilder("Team Donald: ");
        StringBuilder others = new StringBuilder("Team Others: ");
        for (Player player: playerList) {
            if (player.team == Team.DONALD)
                donald.append(player.name).append(" ");
            else
                others.append(player.name).append(" ");
        }
        System.out.println(donald);
        System.out.println(others);
        if(noDonald) decidingScore = 0;
        else decidingScore = selectedDonald.number;
        teamDonaldScore = decidingScore+6;
        teamOthersScore = 8-decidingScore;
        System.out.printf("Number of winning rounds for Team Donald: %s \n",teamDonaldScore);
        System.out.printf("Number of winning rounds for Team Others: %s \n",teamOthersScore);

        int round = 1;

        boolean donanldActivated = false;
        Color biggestColor;

        while (teamDonaldScore > 0 || teamOthersScore > 0){
            boolean firstPlay = false;
            System.out.printf("Round : %s \n", round);
            for (int i=firstPlayerPos; i<playerList.size();i++) playerOrder.add(playerList.get(i));
            for (int i=0; i<firstPlayerPos; i++) playerOrder.add(playerList.get(i));

            StringBuilder order = new StringBuilder("The order of player: ");
            for (Player player : playerOrder)
                order.append(player.name).append(" => ");
            order.delete(order.length()-4,order.length()-1);
            System.out.println(order);

            for (Player player : playerOrder){
                System.out.printf("%s's turn", player.name);
                System.out.println("What would you like to do?");
                System.out.println("1: Play card");
                System.out.println("2: Show hand card");
                System.out.println("3: Skip");
                int ans = input.nextInt();
                switch (ans){
                    case 1:
                        if (firstPlay){
                            firstPlay = false;
                            System.out.printf("Which card do you want to play? [0-%s]", player.cards.size());
                            System.out.println(player.checkCard());
                            int chosen = input.nextInt();
                            Card chosenCard = player.cards.get(chosen);
                            if (!noDonald){
                                if (chosenCard.colour == selectedDonald.color && player == selectedDonald.player){
                                    donanldActivated = true;
                                    biggestColor = selectedDonald.color;
                                }
                            }
                        }
                        else{}
                    case 2:
                        player.checkCard();
                    case 3:
                        break;
                }
            }

            round ++;
        }

    }

    static void initialisingGame(){
        System.out.println("Welcome to Donald Card game!");
        nameInput();
        System.out.println("Constructing the cards in the deck...");
        cardDeck = generateCardDeck();
//        delay(2000);
        System.out.println("52 cards have been constructed");
//        delay(500);
        System.out.println("Shuffling the card deck");
        Collections.shuffle(cardDeck);
//        delay(2000);
        System.out.println("Completed!");
//        delay(500);
        System.out.println("Assigning the cards to the player...");
        populatePlayerCards();
//        delay(2000);
        System.out.println("Done!\n");
//        delay(1000);
    }
    static void nameInput(){
//        Player[] playerList = new Player[4];
        for (int i=0; i<4; i++){
            System.out.printf("Player %d, please enter your name: ", i+1);
            String name = input.nextLine();
            while (name.isBlank()){
                System.out.println("Your input is invalid, please try again.");
                System.out.printf("Player %d, please enter your name: ", i+1);
                name = input.nextLine();
            }
            playerList.add(new Player(name,i+1));
        }
    }
    static ArrayList<Card> generateCardDeck(){
        ArrayList<Card> cardDeck = new ArrayList<>();
        String[] allValue = {"1","2","3","4","5","6","7","8","9","10","A","B","C"};
        Color[] allColors = Color.values();
        for (Color color: allColors){
            for (String value: allValue)
                cardDeck.add(new Card(color,value));
        }

        return cardDeck;
    }
    static void populatePlayerCards(){
        for (int i = 1; i<=cardDeck.size(); i++){
            int playerNumber = i%4;
            if (playerNumber == 0)
                playerNumber = 4;
            playerList.get(playerNumber-1).cards.add(cardDeck.get(i - 1));
        }
    }
    static void pickDonaldNumber(Player player, Scanner input){
        boolean done = false;
        while (!done){
            System.out.print("Enter the number of donald:[1-7]: ");
            try{
                int ans = input.nextInt();
                if (ans >= 1 && ans <=7){
                    donaldList.add(new Donald(ans,player));
                    maxDonaldNumber = Math.max(ans,maxDonaldNumber);
                    done = true;
                } else{
                    System.out.println("Invalid Input. Please try again.");
                }
            } catch (Exception e){
                System.out.println("Invalid Input. Please try again.");
            }
            input.nextLine();
        }


    }
    static void chooseDonald(){
        ArrayList<Donald> donaldCandidates = new ArrayList<>();
        for (Donald donald: donaldList){
            if (donald.number == maxDonaldNumber)
                donaldCandidates.add(donald);
        }
        Collections.shuffle(donaldCandidates);
        selectedDonald = donaldCandidates.get(0);
    }
    static void pickDonalColorAndTeammate(){
        if (noDonald){
            firstPlayerPos = 0;
            System.out.println("There is no donald in this game");
            System.out.printf("%s, please choose your teammate [1-3]: \n", playerList.get(0).name);
//            System.out.println("Please choose your teammate [1-3]: ");
            StringBuilder option = new StringBuilder("|");
            HashMap<Integer,Player> opt = new HashMap<>();
            for (int i=1; i<playerList.size();i++){
                opt.put(i, playerList.get(i));
                option.append(playerList.get(i).name).append(": ").append(i).append("|");
            }
            System.out.println(option);
            int teammate = input.nextInt();
            playerList.get(teammate).team = Team.DONALD;
            playerList.get(0).team = Team.DONALD;
        }
        else{
            firstPlayerPos = playerList.indexOf(selectedDonald.player);
            System.out.printf("Donald number is %d \n", selectedDonald.number);
            System.out.printf("Donald player is %s \n", selectedDonald.player.name);
            System.out.printf("%s, please select Donald color[1-5] \n",selectedDonald.player.name);
            Color[] allColors = Color.values();
            StringBuilder option = new StringBuilder("|");
            int optionNumber = 1;
            for (Color color: allColors){
                option.append(color.name()).append(" ").append(optionNumber).append("|");
                optionNumber++;
            }
            option.append("NO DONALD 5|");
            System.out.println(option);

            switch (input.nextInt()){
                case 1:
                    selectedDonald.color = Color.GREEN;
                    break;
                case 2:
                    selectedDonald.color = Color.BlUE;
                    break;
                case 3:
                    selectedDonald.color = Color.YElLOW;
                    break;
                case 4:
                    selectedDonald.color = Color.RED;
                case 5:
                    selectedDonald.isNoColor = true;
            }

            System.out.println("Please choose your teammate [1-3]: ");
            option = new StringBuilder("|");
            optionNumber = 1;
            HashMap<Integer,Player> opt = new HashMap<>();
            for (Player player: playerList){
                if (player != selectedDonald.player){
                    opt.put(optionNumber,player);
                    option.append(player.name).append(": ").append(optionNumber).append("|");
                    optionNumber++;
                }
            }
            System.out.println(option);
            int teammate = input.nextInt();
            int playerPos = playerList.indexOf(opt.get(teammate));
            playerList.get(playerPos).team = Team.DONALD;
            selectedDonald.player.team = Team.DONALD;
            }
        }
    static void donaldRoundAndCheckCard(){
        for (Player player: playerList){
            //check card
            boolean correct = false;
            while(!correct){
                System.out.printf("%s want to check your card?[YES/NO]: ",player.name);
                String ans = input.nextLine();
                if (ans.equalsIgnoreCase("yes")){
                    System.out.println(player.checkCard());
                    correct = true;
                } else if (ans.equalsIgnoreCase("no"))
                    correct = true;
                else {
                    System.out.println("Your input is invalid, please try again.");
                }

            }

            //call for donald
            correct = false;
            while(!correct){
                System.out.printf("%s call for donald?[YES/NO]: ",player.name);
                String ans = input.nextLine();
                if (ans.equalsIgnoreCase("yes")){
                    noDonald = false;
                    pickDonaldNumber(player,input);
                    correct = true;
                } else if (ans.equalsIgnoreCase("no"))
                    correct = true;
                else {
                    System.out.println("Your input is invalid, please try again.");
                }

            }
            System.out.println();

        }
    }

    static void delay(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
