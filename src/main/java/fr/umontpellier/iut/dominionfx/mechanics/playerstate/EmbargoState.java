package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.List;

public class EmbargoState extends PlayerState {
    public EmbargoState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Choose a supply pile");
    }

    public void supplyCardWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getGame().getAllSupplyPileNames();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            currentPlayer.getGame().addEmbargoToken(cardName);
            if (currentPlayer.areActionsCompleted())
                currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        }
    }

}
