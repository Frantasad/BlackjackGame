package project.Impl;

import org.junit.Before;
import org.junit.Test;
import project.Constants;
import project.IPlayer;
import project.models.cards.Card;
import project.models.cards.Rank;
import project.models.cards.Suit;
import project.models.game.GameResult;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerTest {

    private IPlayer player;

    @Before
    public void setUp() {
        player = new Player("Tester");
    }

    @Test
    public void takeCardsAndHasThem() {
        assertThat(player.getCardsInHand()).isEmpty();
        Card ace = new Card(Rank.Ace, Suit.Clubs);
        Card eight = new Card(Rank.Eight, Suit.Clubs);
        player.takeCard(ace);
        assertThat(player.getCardsInHand()).containsOnly(ace);
        player.takeCard(eight);
        assertThat(player.getCardsInHand()).containsOnly(ace, eight);
    }

    @Test
    public void takeCardsToCheckBusted() {
        assertThat(player.busted()).isFalse();
        player.takeCard(new Card(Rank.Ace, Suit.Clubs));
        assertThat(player.busted()).isFalse();
        player.takeCard(new Card(Rank.Ten, Suit.Clubs));
        assertThat(player.busted()).isFalse();
    }

    @Test
    public void takeCardsToCheckBlackjack() {
        assertThat(player.hasBlackjack()).isFalse();
        Card ace = new Card(Rank.Ace, Suit.Clubs);
        Card ten = new Card(Rank.Ten, Suit.Clubs);
        player.takeCard(ace);
        assertThat(player.hasBlackjack()).isFalse();
        player.takeCard(ten);
        assertThat(player.hasBlackjack()).isTrue();
    }

    private void resolveBetsWithRatio(double ratio) {
        assertThat(player.getCredit()).isEqualTo(Constants.INITIAL_CREDIT);
        int bet = Constants.MINIMAL_BET + 2 * Constants.BET_STEP;
        player.placeBet(bet);
        int credit = player.getCredit();
        assertThat(credit).isEqualTo(Constants.INITIAL_CREDIT - bet);
        player.finishRound(ratio);
        assertThat(player.getCredit()).isEqualTo((int)(credit + bet * ratio));
    }

    @Test
    public void resolveBetsWithWinRatio() {
        resolveBetsWithRatio(GameResult.Win.getWinRatio());
    }

    @Test
    public void resolveBetsWithLooseRatio() {
        resolveBetsWithRatio(GameResult.Lose.getWinRatio());
    }

    @Test
    public void resolveBetsWithTieRatio() {
        resolveBetsWithRatio(GameResult.Tie.getWinRatio());
    }

    @Test
    public void returnCards() {
        assertThat(player.getCardsInHand()).isEmpty();
        Card ace = new Card(Rank.Ace, Suit.Clubs);
        Card eight = new Card(Rank.Eight, Suit.Clubs);
        player.takeCard(ace);
        player.takeCard(eight);
        assertThat(player.getCardsInHand()).isNotEmpty();
        player.returnCards();
        assertThat(player.getCardsInHand()).isEmpty();
    }

    @Test
    public void placeValidBet() {
        int bet = Constants.MINIMAL_BET + 2 * Constants.BET_STEP;
        assertThat(player.placeBet(bet)).isTrue();
    }

    @Test
    public void placeSmallerBetThanMinimum() {
        int bet = Constants.MINIMAL_BET - 1;
        assertThat(player.placeBet(bet)).isFalse();
    }

    @Test
    public void placeBetNotRoundedOnSteps() {
        int bet = Constants.MINIMAL_BET + 2 * Constants.BET_STEP - 1;
        assertThat(player.placeBet(bet)).isFalse();
    }

    @Test
    public void placeBetWhenHasMoney() {
        int bet = Constants.MINIMAL_BET;
        player.placeBet(bet);
        player.finishRound(1);
        assertThat(player.canPlaceBet()).isTrue();
    }

    @Test
    public void placeBetWhenDoesNotHaveMoney() {
        int bet = Constants.INITIAL_CREDIT;
        player.placeBet(bet);
        player.finishRound(0);
        assertThat(player.canPlaceBet()).isFalse();
    }

    @Test
    public void surrenderAndFinishRound() {
        int bet = Constants.MINIMAL_BET;
        player.placeBet(bet);
        player.surrender();
        assertThat(player.surrendered()).isTrue();
        player.finishRound(GameResult.Surrendered.getWinRatio());
        assertThat(player.surrendered()).isFalse();
        assertThat(player.getCurrentBet()).isEqualTo(0);
    }
}