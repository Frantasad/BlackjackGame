package project.model;

/**
 * Suit of the card
 * @author František Holubec
 */
public enum Suit {
    Spades("♠"),
    Hearts("♥"),
    Diamonds("♦"),
    Clubs("♣");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}