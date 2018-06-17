package project;

import project.common.cards.Card;

import java.util.List;

/**
 * Interface for player that can play blackjack
 * @author František Holubec
 */
public interface IPlayer {
    
    int getPoints();
    int getCurrentBet();
    int getCredit();
    String getName();
    List<Card> getCardsInHand();

    /**
     * Take card to the hand 
     * @param card - card to take
     */
    void takeCard(Card card);

    /**
     * Return all cards player has in hand
     * @return List of cards player had in hand
     */
    List<Card> returnCards();

    /**
     * Place bet on a game
     * @param bet - bet to place 
     * @return True if placing bet was successful, False otherwise  
     */
    boolean placeBet(int bet);

    /**
     * Check if player can place a bet
     * @return True if player has at least the minimal amount of cash to place bet
     */
    boolean canPlaceBet();

    /**
     * Check if player is busted (Value of cards in hand is more than 21)
     * @return True if player is busted, False otherwise 
     */
    boolean busted();

    /**
     * Check if player has Game (Ace and second card of value 10)
     * @return True if player has Game, False otherwise
     */
    boolean hasBlackjack();

    /**
     * Check if player surrendered this round
     * @return True if player surrendered, False otherwise
     */
    boolean surrendered();

    /** 
     * Surrender the current round
     */
    void surrender();

    /**
     * Finish one round of the game, meaning resolve prize and reset parameters for new round
     * @param winRatio - ratio of bet for prize
     */
    void finishRound(double winRatio);

}
