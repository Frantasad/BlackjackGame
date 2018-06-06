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
    private int id;
    private String name;
    private List<Card> cardsInHand;
    private int points;

    public Player(String name) {
        this.id = IDGenerator.getNewId();
        this.name = name;
        this.cardsInHand = new ArrayList<>();
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
        for(Card card : cardsInHand){
            builder.append(" ").append(card.toPicture()).append("\n");
        }
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
}
