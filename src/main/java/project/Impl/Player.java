package project.Impl;

import project.helpers.IDGenerator;
import project.model.Card;
import project.model.Rank;
import project.IPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author František Holubec
 */
public class Player implements IPlayer {
    private static final int INITIAL_CREDIT = 1000;
    public static final int MINIMAL_BET = 10;

    private int id;
    private String name;
    private List<Card> cardsInHand;
    private int points;

    private int bet;
    private int credit;

    public Player(String name) {
        this.id = IDGenerator.getNewId();
        this.name = name;
        this.cardsInHand = new ArrayList<>();
        this.credit = INITIAL_CREDIT;
        this.bet = 0;
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
            if (points + ace.getValue() > Blackjack.BLACKJACK_VALUE){
                points += 1;
            } else {
                points += ace.getValue();
            }
        }
        return points;
    }

    @Override
    public boolean busted(){
        return points > Blackjack.BLACKJACK_VALUE;
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
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        builder.append(" (Points: ").append(points).append(")\n");
        builder.append(Card.printPicturesInLine(cardsInHand));
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int getCurrentBet() {
        return bet;
    }

    @Override
    public boolean placeBet(int bet) {
        if(bet > credit || bet < MINIMAL_BET){
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
    public boolean canPlay(){
        return credit > MINIMAL_BET;
    }

    @Override
    public void addCredit(int amount) {
        credit += amount;
    }
}
