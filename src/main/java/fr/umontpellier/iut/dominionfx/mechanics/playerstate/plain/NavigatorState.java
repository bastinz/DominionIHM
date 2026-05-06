package fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;

import java.util.List;

public class NavigatorState extends PlayerState {

    private List<Card> topCards;
    private boolean discardChoice;

    public NavigatorState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Do you want to discard all cards?");
        topCards = currentPlayer.drawCards(5);
        getGame().getTemporaryCards().addAll(topCards);
        discardChoice = true;
        currentPlayer.setWaitForYesOrNo(true);
    }

    @Override
    public void answer(String choice) {
        currentPlayer.setWaitForYesOrNo(false);
        discardChoice = false;
        if (choice.equals("Yes")) {
            currentPlayer.moveToDraw(getGame().getTemporaryCards());
            complete();
        }
        else
            getGame().instructionProperty().setValue("Put cards back on deck (last on top)");
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        if (discardChoice)
            return;
        List<String> availableCards = topCards.stream().map(Card::getName).toList();
        if (!availableCards.isEmpty() && availableCards.contains(cardName)) {
            Card cardToPlay = topCards.stream().filter(c -> c.getName().equals(cardName)).findFirst().orElse(null);
            currentPlayer.moveToDraw(cardToPlay);
            getGame().getTemporaryCards().remove(cardToPlay);
            if (getGame().getTemporaryCards().isEmpty()) {
                complete();
            }
        }
    }

    @Override
    public void skip() {
    }
 }