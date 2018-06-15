package project;

/**
 * Interface for blackjack game
 * @author František Holubec
 */
public interface IBlackjack {

    /**
     * Start new game
     */
    void start();

    void dealInitCards();

    void initPlayers();

    void initDeck();

    void returnAllCardsToDeck();

    void printAllPlayers();

    void placeBets();

    void checkResults();

    void checkForContinue();
}
