package project.common.cards;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeckTest {

    private Deck deck;

    @Before
    public void setUp() throws Exception {
        deck = new Deck();
    }

    @Test
    public void getTopCard() {
        Card a = deck.getTopCard();
        Card b = deck.getTopCard();
        assertThat(deck.getCards()).doesNotContain(a ,b);
    }

    @Test
    public void returnAllCards() {
        List<Card> cardsBeforeRemoving = new ArrayList<>(deck.getCards());
        Card a = deck.getTopCard();
        Card b = deck.getTopCard();
        assertThat(deck.getCards()).doesNotContain(a ,b);
        deck.returnAllCards();
        assertThat(deck.getCards()).contains(a, b);
    }

    @Test
    public void shuffle() {
        List<Card> cardsBeforeShuffle = new ArrayList<>(deck.getCards());
        deck.shuffle();
        assertThat(deck.getCards()).containsOnlyElementsOf(cardsBeforeShuffle);
        assertThat(deck.getCards()).isNotEqualTo(cardsBeforeShuffle);
    }
}