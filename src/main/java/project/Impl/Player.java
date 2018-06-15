package project.Impl;

import project.Constants;
import project.models.cards.Card;
import project.models.cards.Rank;
import project.IPlayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of player that can play blackjack
 * @author František Holubec
 */
public class Player implements IPlayer {


    private String name;
    private List<Card> cardsInHand;
    private int points;

    private int bet;
    private int credit;

    private boolean surrendered;

    public Player(String name) {
        this.name = name;
        cardsInHand = new ArrayList<>();
        credit = Constants.INITIAL_CREDIT;
        bet = 0;
        surrendered = false;
    }

    private int calculatePoints() {
        int points = 0;
        List<Card> aces = new ArrayList<>();
        for(Card card : cardsInHand){
            if(card.getRank() != Rank.Ace){
                points += card.getValue();
            } else {
                aces.add(card);
            }
        }
        for(Card ace : aces){
            if (points + ace.getValue() > Constants.BLACKJACK_VALUE){
                points += 1;
            } else {
                points += ace.getValue();
            }
        }
        return points;
    }

    @Override
    public boolean busted(){
        return points > Constants.BLACKJACK_VALUE;
    }

    @Override
    public boolean hasBlackjack() {
        List<Integer> ranksInHand = new ArrayList<>();
        for (Card card : cardsInHand) {
            ranksInHand.add(card.getValue());
        }
        return cardsInHand.size() == 2 && ranksInHand.contains(11) && ranksInHand.contains(10);
    }

    @Override
    public boolean surrendered() {
        return surrendered;
    }

    @Override
    public void surrender() {
        surrendered = true;
    }

    @Override
    public void finishRound(double winRatio) {
        credit += bet * winRatio;
        bet = 0;
        surrendered = false;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void takeCard(Card card) {
        cardsInHand.add(card);
        points = calculatePoints();
    }

    @Override
    public List<Card> returnCards() {
        List<Card> cardsToReturn;
        cardsToReturn = cardsInHand;
        cardsInHand = new ArrayList<>();
        return cardsToReturn;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Card> getCardsInHand() {
        return Collections.unmodifiableList(cardsInHand);
    }

    @Override
    public int getCurrentBet() {
        return bet;
    }

    @Override
    public boolean placeBet(int bet) {
        if(bet % Constants.BET_STEP != 0|| bet > credit || bet < Constants.MINIMAL_BET){
            return false;
        }
        credit -= bet;
        this.bet = bet;
        return true;
    }

    @Override
    public int getCredit() {
        return credit;
    }

    @Override
    public boolean canPlaceBet(){
        return credit > Constants.MINIMAL_BET;
    }

    @Override
    public String toString() {
        return name + " (Points: " + points + ")\n" + Card.printPicturesInLine(cardsInHand);
    }
}
