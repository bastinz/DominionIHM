package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;

public class ActionState extends PlayerState {
    public ActionState(Player currentPlayer) {
        super(currentPlayer);
    }

    public void passer() {
        finDuTour();
    }
}
