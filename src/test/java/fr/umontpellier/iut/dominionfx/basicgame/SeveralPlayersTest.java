package fr.umontpellier.iut.dominionfx.basicgame;

import fr.umontpellier.iut.dominionfx.BaseTestClass;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeveralPlayersTest extends BaseTestClass {

    @Override
    public void start(Stage stage) {
        game = new Game(new String[]{"PlayerTest1", "PlayerTest2", "PlayerTest3", "PlayerTest4"},
                        new String[]{"Bazaar", "Cutpurse"});
        super.start(stage);
    }

    @Test
    public void allPlayersProcessed() {
        Player currentPlayer = game.currentPlayer();
        addToSecondPlayersHand("Cutpurse");
        clickOnSkip();
        clickOnSkip();
        clickOnSkip();
        clickOnCardInHand("Cutpurse");
        assertEquals(4, currentPlayer.getGame().getPlayers().get(0).getHand().size());
        assertEquals(4, currentPlayer.getGame().getPlayers().get(1).getHand().size());
        assertEquals(4, currentPlayer.getGame().getPlayers().get(2).getHand().size());
//        pause(2);
    }
}
