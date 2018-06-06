package project;

import project.model.Card;

import java.util.List;

/**
 *
 * @author František Holubec
 */
public interface IPlayer {

    int getPoints();

    String getName();

    boolean busted();

    void takeCard(Card card);

    List<Card> returnCards();

    int getCurrentBet();

    boolean placeBet(int bet);

    int getCredit();

    boolean canPlay();

    void addCredit(int amount);
}
