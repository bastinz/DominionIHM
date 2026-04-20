package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.List;

public class ExplorerState extends PlayerState {
    public ExplorerState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("You may reveal a Province");
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> choixPossibles = currentPlayer.getProvincesInHand();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(cardName)) {
            currentPlayer.gainTreasure("Gold");
            moveToTreasurePhase();
        }
    }

    private void moveToTreasurePhase() {
        if (currentPlayer.areActionsCompleted())
            currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
    }

    @Override
    public void skip() {
        currentPlayer.gainTreasure("Silver");
        moveToTreasurePhase();
    }
    
}
