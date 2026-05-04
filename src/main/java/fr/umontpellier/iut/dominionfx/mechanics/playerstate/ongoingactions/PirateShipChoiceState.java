package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.PirateShip;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;

public class PirateShipChoiceState extends PlayerState {

    private final PirateShip pirateShip;

    public PirateShipChoiceState(Player currentPlayer, PirateShip pirateShip) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Choose +%dcoins or attack".formatted(currentPlayer.getPirateShipCounter()));
        currentPlayer.setWaitForYesOrNo(true);
        this.pirateShip = pirateShip;
    }

    @Override
    public void answer(String choice) {
        currentPlayer.setWaitForYesOrNo(false);
        if (choice.equals("Yes")) {
            currentPlayer.incrementMoney(currentPlayer.getPirateShipCounter());
            pirateShip.setAttacking(false);
        }
        else
            pirateShip.setAttacking(true);
        complete();
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
/*        if (discardChoice)
            return;
        List<String> availableCards = topCards.stream().map(Card::getName).toList();
        if (!availableCards.isEmpty() && availableCards.contains(cardName)) {
            Card cardToPlay = topCards.stream().filter(c -> c.getName().equals(cardName)).findFirst().orElse(null);
            currentPlayer.moveToDraw(cardToPlay);
            topCards.remove(cardToPlay);
            if (topCards.isEmpty()) {
                getGame().setTemporaryCards(null, null);
                complete();
            }*/
        }
}
