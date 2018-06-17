package project.Impl;

import project.common.game.ConsoleHelpers;
import project.common.game.Constants;
import project.IGame;
import project.IPlayer;
import project.ISession;
import project.common.cards.Deck;

import java.util.ArrayList;
import java.util.List;

public class Session implements ISession {

    private IPlayer dealer;
    private List<IPlayer> players;
    private Deck deck;

    public Session() {
        ConsoleHelpers.printTitle();
        System.out.println("\tSetup game");
        players = initPlayers();
        deck = initDeck();
        dealer = new Player("Dealer");
    }

    public Session(IPlayer dealer, List<IPlayer> players, Deck deck) {
        this.dealer = dealer;
        this.players = players;
        this.deck = deck;
    }

    @Override
    public void start(){
        boolean running = true;
        while(running){
            System.out.println("\tGame started");
            ConsoleHelpers.printHelp();
            ConsoleHelpers.printCardInfo();

            while(!players.isEmpty()){
                IGame game = new Game(dealer, players, deck);
                game.play();
                checkForContinue();
            }
            System.out.println("\t\tGAME OVER");
            running = ConsoleHelpers.askYesOrNo("Play again?");
        }
    }

    private static Deck initDeck(){
        System.out.print("Enter number of card decks to use (max: " + Deck.MAX_DECKS_IN_ONE + "): ");
        int cnt = ConsoleHelpers.READER.nextInt();
        if(cnt > Deck.MAX_DECKS_IN_ONE){
            System.out.println("Input over limit, using: " + Deck.MAX_DECKS_IN_ONE);
        }
        Deck deck = new Deck(cnt);
        deck.shuffle();
        System.out.println();
        return deck;
    }


    private static List<IPlayer> initPlayers(){
        List<IPlayer> players = new ArrayList<>();
        players.add(createNewPlayer());
        while (players.size() < Constants.MAX_PLAYERS) {
            System.out.println("Players: " + players.size() + "/" + Constants.MAX_PLAYERS);
            if(ConsoleHelpers.askYesOrNo("Add player?")){
                players.add(createNewPlayer());
            } else {
                break;
            }
        }
        System.out.println();
        return players;
    }

    private static IPlayer createNewPlayer(){
        System.out.print("Enter name: ");
        String name = ConsoleHelpers.READER.next();
        return new Player(name);
    }


    private void checkForContinue(){
        List<IPlayer> leavingIPlayers = new ArrayList<>();
        for(IPlayer player : players) {
            if(!player.canPlaceBet()){
                System.out.println(player.getName() + " cannot play anymore.");
                leavingIPlayers.add(player);
            } else {
                if(!ConsoleHelpers.askYesOrNo(player.getName() + ", do you want to continue?")){
                    leavingIPlayers.add(player);
                }
            }
        }
        players.removeAll(leavingIPlayers);
    }

}
