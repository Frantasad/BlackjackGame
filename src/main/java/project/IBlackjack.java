package project;

/**
 *
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

    void printHelp();

    void printAllPlayers();
}
