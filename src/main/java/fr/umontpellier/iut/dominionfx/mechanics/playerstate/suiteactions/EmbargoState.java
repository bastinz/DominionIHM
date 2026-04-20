package fr.umontpellier.iut.dominionfx.mechanics.playerstate.suiteactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.List;

public class EmbargoState extends SuiteAction {
    public EmbargoState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Choose a supply pile");
    }

    @Override
    public void supplyCardWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getGame().getAllSupplyPileNames();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            currentPlayer.getGame().addEmbargoToken(cardName);
            moveToNextPhase();
        }
    }

    @Override
    public void skip() {}

}
