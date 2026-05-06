package fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;

public class PearlDiverState extends PlayerState {

    public PearlDiverState(Player currentPlayer, Card bottomCard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Do you want to put %s on top of your deck?".formatted(bottomCard.getName()));
    }

    @Override
    public void answer(String choice) {
        currentPlayer.setWaitForYesOrNo(false);
        if (choice.equals("Yes"))
            currentPlayer.moveToDraw(getGame().getTemporaryCards().getFirst());
        complete();
    }
 }
