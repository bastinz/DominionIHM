package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.List;

public class TreasurePhase extends PlayerState {
    public TreasurePhase(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Treasure phase");
    }

    @Override
    public void skip() {
        endOfCurrentPlayersTurn();
//        moveToNextPhase();
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfTreasuresInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            currentPlayer.playTreasureCard(cardName);
        }
    }

/*    @Override
    public void playTreasuresWasChosen() {
        currentPlayer.endActionPhase();
        currentPlayer.playTreasures();
    }*/

    @Override
    public void supplyCardWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getAvailableSupplyCards();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            currentPlayer.buy(cardName);
        }
    }
}
