package project.Impl;

import project.Constants;
import project.IBlackjack;
import project.models.cards.Deck;
import project.IPlayer;
import project.models.game.GameResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Implementation of blackjack game
 * @author František Holubec
 */
public class Blackjack implements IBlackjack {

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

        boolean running = true;
        while(running){
            System.out.println("\tSetup game");
            initPlayers();
            initDeck();

            System.out.println("\tGame started");
            printHelp();
            printCardInfo();
            while(!players.isEmpty()){
                System.out.println("\t\tNew Round");
                placeBets();
                dealInitCards();
                printAllPlayers();
                turnOfPlayers();
                turnOfDealer();
                System.out.println("\t\tRound over");
                checkResults();
                checkForContinue();
                returnAllCardsToDeck();
                System.out.println();
            }
            System.out.println("\t\tGAME OVER");
            System.out.print("Play again? (Yes/No): ");
            running = askYesOrNo();
        }
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
        System.out.println();
    }

    @Override
    public void initPlayers(){
        createNewPlayer();
        while (players.size() < Constants.MAX_PLAYERS) {
            System.out.println("Players: " + players.size() + "/" + Constants.MAX_PLAYERS);
            System.out.print("Add player? (Yes/No): ");
            if(askYesOrNo()){
                createNewPlayer();
            } else {
                System.out.println();
                return;
            }
        }
        System.out.println();
    }

    @Override
    public void placeBets() {
        for(IPlayer player : players) {
            System.out.println(player.getName() + " - credit: " + player.getCredit());
            System.out.print("Place bet (only factors of " + Constants.BET_STEP + ", min: " + Constants.MINIMAL_BET + "): ");
            int bet = READER.nextInt();
            while (!player.placeBet(bet)){
                invalidInput();
                System.out.print("Place valid bet: ");
                bet = READER.nextInt();
            }
            System.out.println("Bet of " + player.getCurrentBet() + " has been set\n");
        }
    }

    @Override
    public void checkResults() {
        for (IPlayer player : players){
            GameResult result = getResult(player, dealer);
            System.out.println(player.getName() + " - " + result + " (Credit: " + player.getCredit() + ")");
            player.finishRound(result.getWinRatio());
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
    public void checkForContinue(){
        List<IPlayer> leavingIPlayers = new ArrayList<>();
        for(IPlayer player : players) {
            if(!player.canPlaceBet()){
                System.out.println(player.getName() + " cannot play anymore.");
                leavingIPlayers.add(player);
            } else {
                System.out.print(player.getName() + ", do you want to continue? (Yes/No): ");
                if(!askYesOrNo()){
                    leavingIPlayers.add(player);
                }
            }
        }
        players.removeAll(leavingIPlayers);
    }

    @Override
    public void returnAllCardsToDeck(){
        for(IPlayer player : players){
            player.returnCards();
        }
        dealer.returnCards();
        deck.returnAllCards();
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
                        player.surrender();
                        playing = false;
                        break;
                    default:
                        invalidInput();
                        break;
                }
            }
            System.out.println();
        }
    }

    private void printHelp(){
        System.out.println("Available commands: \n" +
                " Help: Show printHelp\n" +
                " Hit: Take another card from the dealer\n" +
                " Stand: Take no more model\n" +
                " Surrender: Surrender the round\n");
    }

    private void printCardInfo(){
        System.out.println("Card suits: \n" +
                " S - Spades\n" +
                " H - Hearts\n" +
                " D - Diamonds\n" +
                " C - Clubs\n");
    }

    private boolean askYesOrNo(){
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
