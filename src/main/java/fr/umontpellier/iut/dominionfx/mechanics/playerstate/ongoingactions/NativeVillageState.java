package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

public class NativeVillageState extends OnGoingActionPhase {

    public NativeVillageState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Choose one");
    }

    @Override
    public void addToMat() {
        if (!currentPlayer.getDraw().isEmpty()) {
            Card c = currentPlayer.getCardFromDeck();
            currentPlayer.moveToNativeVillageMat(c);
            currentPlayer.setNativeVillagePlayed(false);
            complete();
        }
    }

    @Override
    public void takeFromMat() {
        currentPlayer.moveToHand(currentPlayer.getCardsOnNativeVillageMat());
        currentPlayer.setNativeVillagePlayed(false);
        complete();
    }

    @Override
    public void skip() {
    }
}
