package fr.umontpellier.iut.dominionfx.basicgame;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class SkipEtBuysTest extends BaseTestClass {

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
//        pause(2);
    }

    @Test
    public void buyLighthouse() {
        Player initialPlayer = game.currentPlayer();
        SupplyPile lightHousePile = game.getSupplyPiles().stream()
                .filter(pile -> "Lighthouse".equals(pile.getName()))
                .findFirst()
                .orElseThrow();
        int initialSize = lightHousePile.size();
        int initialHandSize = game.currentPlayer().getHand().size();
        clickOnCardInHand("Copper");
        clickOnCardInHand("Copper");
        assertEquals(initialHandSize - 2, game.currentPlayer().getHand().size());
        clickOnSupplyPile("Lighthouse");
        assertEquals(initialSize - 1, lightHousePile.size());
        assertNotEquals(initialPlayer, game.currentPlayer());
//        pause(2);
    }

    @Test
    public void buyWithAllTreasures() {
        Player initialPlayer = game.currentPlayer();
        SupplyPile lightHousePile = game.getSupplyPiles().stream()
            .filter(pile -> "Lighthouse".equals(pile.getName()))
            .findFirst()
            .orElseThrow();
        int initialSupplyPileSize = lightHousePile.size();
        clickOnTreasures();
        clickOnSupplyPile("Lighthouse");
        assertEquals(initialSupplyPileSize - 1, lightHousePile.size());
        assertNotEquals(initialPlayer, game.currentPlayer());
//        pause(2);
    }

}
