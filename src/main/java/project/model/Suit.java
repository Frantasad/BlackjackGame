package project.model;

public enum Suit {
    Spades("Spades", "♠"),
    Hearts("Hearts", "♥"),
    Diamonds("Diamonds", "♦"),
    Clubs("Clubs", "♣");

    private final String name;
    private final String symbol;

    Suit(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return name;
    }
}