package project.models.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Deck of the cards
 * @author František Holubec
 */
public class Deck {

    public static final int MAX_DECKS_IN_ONE = 8;
    private LinkedList<Card> cards;
    private List<Card> removedCards;

    public Deck() {
        this(1);
    }

    public Deck(int cnt){
        cards = new LinkedList<>();
        removedCards = new ArrayList<>();

        List<Card> newCards = new LinkedList<>();
        for(Suit suit : Suit.values()){
            for(Rank rank : Rank.values()){
                newCards.add(new Card(rank, suit));
            }
        }
        cnt = cnt > MAX_DECKS_IN_ONE ? MAX_DECKS_IN_ONE : cnt;

        for(int i = 0; i < cnt; i++){
            cards.addAll(newCards);
        }
    }

    /**
     * Get top card from the deck
     * @return top card of the deck
     */
    public Card getTopCard(){
        if (cards.isEmpty()) {
            return null;
        }
        Card card = cards.removeFirst();
        removedCards.add(card);
        return card;
    }

    /**
     * Return all cards back to the deck
     */
    public void returnAllCards(){
        cards.addAll(removedCards);
        removedCards = new ArrayList<>();
    }

    /**
     * Shuffle the deck
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

}
