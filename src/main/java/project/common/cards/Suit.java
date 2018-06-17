package project.common.cards;

/**
 * Suit of the card
 * @author František Holubec
 */
public enum Suit {
    Spades("S"),
    Hearts("H"),
    Diamonds("D"),
    Clubs("C");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}