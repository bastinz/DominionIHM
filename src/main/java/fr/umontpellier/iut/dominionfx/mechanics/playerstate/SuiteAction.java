package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

public class SuiteAction extends ActionPhase {
    public SuiteAction(Player currentPlayer) {
        super(currentPlayer);
    }

    public boolean endOfAction() {
        return false;
    }

}
