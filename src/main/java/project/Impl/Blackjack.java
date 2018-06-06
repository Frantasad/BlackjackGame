package project.Impl;

import project.IBlackjack;
import project.model.Deck;
import project.IPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author František Holubec
 */
public class Blackjack implements IBlackjack {
    public static final int BLACKJACK_VALUE = 21;
    public static final int DEALERS_HIT_LIMIT = 17;
    public static final int MAX_PLAYERS = 6;
    private static final Scanner READER = new Scanner(System.in);

    private IPlayer dealer;
    private List<IPlayer> players;
    private Deck deck;

    public Blackjack() {
        dealer = new Player("Dealer");
        players = new ArrayList<>();
    }

    @Override
    public void start(){
        printTitle();

        System.out.println("\tSetup game");
        initPlayers();
        initDeck();

        System.out.println();
        System.out.println("\tGame started");
        printHelp();
        while(!players.isEmpty()){
            System.out.println("\t\tNew Round");
            dealInitCards();
            printAllPlayers();
            turnOfPlayers();
            turnOfDealer();
            askToContinue();
        }
    }

    private void askToContinue(){
        List<IPlayer> leavingIPlayers = new ArrayList<>();
        for(IPlayer player : players) {
            System.out.print(player.getName() + ", do you want to continue? (Yes/No): ");
            if(!AskYesOrNo()){
                leavingIPlayers.add(player);
            }
        }
        players.removeAll(leavingIPlayers);
    }

    @Override
    public void dealInitCards(){
        for(IPlayer player : players) {
            player.takeCard(deck.getTopCard());
        }
        dealer.takeCard(deck.getTopCard());
        for(IPlayer player : players) {
            player.takeCard(deck.getTopCard());
        }
    }

    @Override
    public void printAllPlayers(){
        System.out.println("Players:");
        System.out.println(dealer);
        for(IPlayer player : players){
            System.out.println(player);
        }
    }

    private void turnOfDealer(){
        System.out.println("Turn of " + dealer.getName());
        while(dealer.getPoints() < DEALERS_HIT_LIMIT){
            dealer.takeCard(deck.getTopCard());
            System.out.println(dealer);
        }
    }

    private void turnOfPlayers(){
        for(IPlayer player : players) {
            System.out.println("Turn of " + player.getName());
            boolean playing = true;
            while(playing){
                System.out.print("Enter command: ");
                String command = READER.next().toLowerCase();
                switch (command){
                    case "printHelp":
                        printHelp();
                        break;
                    case "hit":
                        player.takeCard(deck.getTopCard());
                        if(player.busted()){
                            playing = false;
                            System.out.println("*BUSTED*");
                        }
                        System.out.println(player);
                        break;
                    case "stand":
                        playing = false;
                        break;
                    case "surrender":
                        break;
                    default:
                        invalidInput();
                        break;
                }
            }
        }
        returnAllCardsToDeck();
    }

    @Override
    public void printHelp(){
        System.out.println("Available commands: \n" +
                " Help: Show printHelp\n" +
                " Hit: Take another card from the dealer\n" +
                " Stand: Take no more model\n" +
                " Surrender: Surrender the round\n" +
                " Exit: Leave the game after this round\n");
    }

    @Override
    public void initDeck(){
        System.out.print("Enter number of card decks to use (max: " + Deck.MAX_DECKS_IN_ONE + "): ");
        int cnt = READER.nextInt();
        if(cnt > Deck.MAX_DECKS_IN_ONE){
            System.out.println("Input over limit, using: " + Deck.MAX_DECKS_IN_ONE);
        }
        deck = new Deck(cnt);
        deck.shuffle();
    }

    private boolean AskYesOrNo(){
        while(true){
            String answer = READER.next().toLowerCase();
            switch (answer) {
                case "yes":
                    return true;
                case "no":
                    return false;
                default:
                    invalidInput();
                    break;
            }
        }
    }

    @Override
    public void initPlayers(){
        createNewPlayer();
        while (players.size() < MAX_PLAYERS) {
            System.out.println("Players: " + players.size() + "/" + MAX_PLAYERS);
            System.out.print("Add player? (Yes/No): ");
            if(AskYesOrNo()){
                createNewPlayer();
            } else {
                return;
            }
        }
    }

    @Override
    public void returnAllCardsToDeck(){
        for(IPlayer player : players){
            player.returnCards();
        }
        dealer.returnCards();
        deck.returnAllCards();
    }

    private void createNewPlayer(){
        System.out.print("Enter name: ");
        String name = READER.next();
        players.add(new Player(name));
    }

    private void invalidInput(){
        System.out.println("Invalid input");
    }

    private void printTitle(){
        System.out.println("\t\t.-----------.");
        System.out.println("\t\t| BLACKJACK |");
        System.out.println("\t\t'-----------'\n");
    }


}
