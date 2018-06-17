package project.Impl;

import project.common.game.ConsoleHelpers;
import project.common.game.Constants;
import project.IGame;
import project.common.cards.Deck;
import project.IPlayer;
import project.common.game.GameResult;

import java.util.List;
import java.util.Scanner;

/**
 * Implementation of blackjack game
 * @author František Holubec
 */
public class Game implements IGame {

    private static final Scanner READER = new Scanner(System.in);

    private IPlayer dealer;
    private List<IPlayer> players;
    private Deck deck;

    public Game(IPlayer dealer, List<IPlayer> players, Deck deck) {
        this.dealer = dealer;
        this.players = players;
        this.deck = deck;
    }

    @Override
    public void play() {
        System.out.println("\t\tNew Round");
        placeBets();
        dealInitCards();
        printAllPlayers();
        turnOfPlayers();
        turnOfDealer();
        System.out.println("\t\tRound over");
        checkResults();
        returnAllCardsToDeck();
        System.out.println();
    }

    private void placeBets() {
        for(IPlayer player : players){
            System.out.println(player.getName() + " - credit: " + player.getCredit());
            System.out.print("Place bet (only factors of " + Constants.BET_STEP + ", min: " + Constants.MINIMAL_BET + "): ");
            int bet = READER.nextInt();
            while (!player.placeBet(bet)){
                ConsoleHelpers.invalidInput();
                System.out.print("Place valid bet: ");
                bet = READER.nextInt();
            }
            System.out.println("Bet of " + player.getCurrentBet() + " has been set\n");
        }
    }

    private void checkResults() {
        for (IPlayer player : players){
            GameResult result = getResult(player, dealer);
            player.finishRound(result.getWinRatio());
            System.out.println(player.getName() + " - " + result + " (Credit: " + player.getCredit() + ")");
        }
    }

    private void dealInitCards(){
        for(IPlayer player : players) {
            player.takeCard(deck.getTopCard());
        }
        dealer.takeCard(deck.getTopCard());
        for(IPlayer player : players) {
            player.takeCard(deck.getTopCard());
        }
    }

    private void returnAllCardsToDeck(){
        for(IPlayer player : players){
            player.returnCards();
        }
        dealer.returnCards();
        deck.returnAllCards();
    }

    private void printAllPlayers(){
        System.out.println("Players:");
        System.out.println(dealer);
        for(IPlayer player : players){
            System.out.println(player);
        }
    }

    private void turnOfDealer(){
        boolean continueTurn = true;
        for(IPlayer player : players){
            continueTurn = continueTurn && (player.busted() || player.surrendered());
        }
        if(continueTurn){
            return;
        }
        System.out.println("Turn of " + dealer.getName());
        while(dealer.getPoints() < Constants.DEALERS_HIT_LIMIT){
            dealer.takeCard(deck.getTopCard());
            if(dealer.busted()){
                System.out.println("*BUSTED*");
            }
            System.out.println(dealer);
        }
    }

    private void turnOfPlayers(){
        for(IPlayer player : players) {
            System.out.println("Turn of " + player.getName());
            System.out.println(player);
            boolean playing = true;
            while(playing){
                if(player.getPoints() == Constants.BLACKJACK_VALUE){
                    break;
                }
                System.out.print("Enter command: ");
                String command = READER.next().toLowerCase();
                switch (command){
                    case "printHelp":
                        ConsoleHelpers. printHelp();
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
                        player.surrender();
                        playing = false;
                        break;
                    default:
                        ConsoleHelpers.invalidInput();
                        break;
                }
            }
            System.out.println();
        }
    }

    public static GameResult getResult(IPlayer player, IPlayer dealer){
        if(player.surrendered()){
            return GameResult.Surrendered;
        }else if (player.busted() || (player.getPoints() < dealer.getPoints() && !dealer.busted())){
            return GameResult.Lose;
        } else if(dealer.busted()){
            return GameResult.Win;
        } else if (player.getPoints() == dealer.getPoints() || player.hasBlackjack() && dealer.hasBlackjack()){
            return GameResult.Tie;
        }else if (player.hasBlackjack() && !dealer.hasBlackjack()){
            return  GameResult.Blackjack;
        }else {
            return GameResult.Win;
        }
    }
}
