package project.models.game;

/**
 * Possible results of one blackjack game
 * @author František Holubec
 */
public enum GameResult {
    Win(2),
    Lose(0),
    Blackjack(2.5),
    Tie(1),
    Surrendered(0.5);

    private final double winRatio;

    GameResult(double winRatio) {
        this.winRatio = winRatio;
    }

    public double getWinRatio() {
        return winRatio;
    }
}
