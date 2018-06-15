package project;

import project.Impl.Blackjack;

/**
 * Run and play Blackjack
 * @author František Holubec
 */
public class Main {
    public static void main(String[] args){
        IBlackjack game = new Blackjack();
        game.start();
    }
}
