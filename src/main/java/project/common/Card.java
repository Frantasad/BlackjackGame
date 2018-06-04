package project.common;

public class Card {
    private static final int MIN_RANK = 1;
    private static final int MAX_RANK = 13;

    private int rank;
    private Suit suit;

    public Card(int rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public int getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }
}
