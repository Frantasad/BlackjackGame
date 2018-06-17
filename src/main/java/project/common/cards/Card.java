package project.common.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One playing card
 * @author František Holubec
 */
public class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getValue() {
        return rank.getValue();
    }

    public Suit getSuit() {
        return suit;
    }

    /**
     * Generate string of card drawn in ascii art
     * @return generated string
     */
    public String toPicture(){
        return new StringBuilder()
                .append(" ___ \n")
                .append("|").append(String.format("%1$-3s", rank.getSymbol())).append("|\n")
                .append("| ").append(suit.getSymbol()).append(" |\n")
                .append("|").append(String.format("%1$3s", rank.getSymbol()).replace(" ", "_")).append("|\n")
                .toString();
    }

    /**
     * From list of cards generate line of pictures of cards
     * @param cards list of cards to generate line from
     * @return string representing line of cards in ascii art
     */
    public static String printPicturesInLine(List<Card> cards){
        if(cards.size() == 0){
            return "";
        }

        ArrayList<String[]> lines = new ArrayList<>();
        for (Card card : cards) {
            lines.add(card.toPicture().split("\\n"));
        }
        StringBuilder pictureList = new StringBuilder();
        int lineCnt = lines.get(0).length;
        for(int x = 0; x < lineCnt; x++){
            for (String[] line : lines) {
                pictureList.append(line[x]);
            }
            pictureList.append("\n");
        }

        return pictureList.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return rank == card.rank &&
                suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }

    @Override
    public String toString() {
        return rank + "-" + suit;
    }
}
