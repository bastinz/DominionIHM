package fr.umontpellier.iut.dominionfx.basicgame;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DebutPartieTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                        new String[]{"Embargo", "Lighthouse", "Pirate", "Sailor"});
//        DominionIHM.getRandomKingdomCards()
        super.start(stage);
    }

    @Test
    public void skipTwiceWithTwoPlayers() {
        Player initialPlayer = game.currentPlayer();
        clickOnSkip();
        Player otherPlayer = game.currentPlayer();
        assertNotEquals(initialPlayer, otherPlayer);
        clickOnSkip();
        assertEquals(initialPlayer, game.currentPlayer());
//        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
    }

    @Test
    public void buyLighthouse() {
        Player initialPlayer = game.currentPlayer();
        SupplyPile lightHousePile = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .get();
        int initialSize = lightHousePile.size();
        ObservableList<Card> hand =  game.currentPlayer().getHand();
        int initialHandSize = hand.size();
        clickOnCardInHand("Copper");
        clickOnCardInHand("Copper");
        assertEquals(initialHandSize - 2, hand.size());
        clickOnSupplyPile("Lighthouse");
        assertEquals(initialSize - 1, lightHousePile.size());
        assertNotEquals(initialPlayer, game.currentPlayer());
//        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
    }

    @Test
    public void buyWithAllTreasures() {
        Player initialPlayer = game.currentPlayer();
        SupplyPile lightHousePile = game.getSupplyPiles().stream()
            .filter(pile -> "Lighthouse".equals(pile.getName()))
            .findFirst()
            .get();
        int initialSupplyPileSize = lightHousePile.size();
        clickOnTreasures();
        clickOnSupplyPile("Lighthouse");
        assertEquals(initialSupplyPileSize - 1, lightHousePile.size());
        assertNotEquals(initialPlayer, game.currentPlayer());
//        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
    }

    private void miseEnPlaceDebut() {
//        cliquerMain("Salamèche");
//        cliquerPasser();
//        cliquerMain("Ponyta");
//        cliquerPasser();
//        WaitForAsyncUtils.sleep(2, TimeUnit.SECONDS);
    }

}
