package fr.umontpellier.iut.dominionfx.basicgame;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

public class GameOverTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2"},
                        new String[]{"Province", "Lighthouse", "Pirate", "Sailor"});
        super.start(stage);
    }

    @Override
    public void setPlayersHands() {
        addToFirstPlayersHand("Gold");
        addToFirstPlayersHand("Gold");
        SupplyPile provincePile = game.getSupplyPiles().stream().filter(p -> p.getName().equals("Province")).findFirst().get();
        while (provincePile.size() > 1) {
            provincePile.remove(0);
        }
        SupplyPile lighthousePile = game.getSupplyPiles().stream().filter(p -> p.getName().equals("Lighthouse")).findFirst().get();
        while (lighthousePile.size() > 1) {
            lighthousePile.remove(0);
        }
        game.getSupplyPiles().stream().filter(p -> p.getName().equals("Sailor")).findFirst().get().clear();
        game.getSupplyPiles().stream().filter(p -> p.getName().equals("Pirate")).findFirst().get().clear();
    }

    @Test
    public void emptyProvinceSupplyPile() {
        clickOnTreasures();
        clickOnSupplyPile("Province");
//        pause(2);
    }


    @Test
    public void emptyThreeSupplyPiles() {
        clickOnTreasures();
        clickOnSupplyPile("Lighthouse");
//        clickOnPlayAgain();
//        pause(2);
    }
}
