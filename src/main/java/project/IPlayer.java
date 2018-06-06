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
}
