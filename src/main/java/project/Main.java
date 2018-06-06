package project;

import project.Impl.Blackjack;

public class Main {
    public static void main(String[] args){
        IBlackjack game = new Blackjack();
        game.start();
    }
}
