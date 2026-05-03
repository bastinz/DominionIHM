package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;

public class PearlDiverState extends PlayerState {

    private final Card bottomCard;

    public PearlDiverState(Player currentPlayer, Card bottomCard) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Do you want to put %s on top of your deck?".formatted(bottomCard.getName()));
        this.bottomCard = bottomCard;
    }

    @Override
    public void answer(String choice) {
        currentPlayer.setWaitForYesOrNo(false);
        if (choice.equals("Yes"))
            currentPlayer.moveToDraw(bottomCard);
        currentPlayer.getGame().setTemporaryCards(null);
        complete();
    }
 }

/*
         if (c != null) {
String choice = p.chooseStringFromButtons(
        "%s: Do you want to put %s on top of your deck?".formatted(this, c),
        Arrays.asList(new Button("Yes", "y"), new Button("No", "n")),
        false);


            if (choice.equals("y")) {
        p.moveToDraw(c); // retire la carte du bas de la pioche et la place sur le dessus


            }
                    }*/
