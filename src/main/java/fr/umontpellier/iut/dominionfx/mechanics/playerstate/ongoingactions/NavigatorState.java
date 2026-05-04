package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class NavigatorState extends PlayerState {

    private ObservableList<Card> topCards = FXCollections.observableArrayList();
    private boolean discardChoice;

    public NavigatorState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Do you want to discard all cards?");
        topCards.addAll(currentPlayer.drawCards(5));
        currentPlayer.getGame().setTemporaryCards(topCards, currentPlayer.getDraw());
        discardChoice = true;
        currentPlayer.setWaitForYesOrNo(true);
    }

    @Override
    public void answer(String choice) {
        currentPlayer.setWaitForYesOrNo(false);
        discardChoice = false;
        if (choice.equals("Yes")) {
            getGame().setTemporaryCards(null, null);
            currentPlayer.moveToDraw(topCards);
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
            topCards.remove(cardToPlay);
            if (topCards.isEmpty()) {
                getGame().setTemporaryCards(null, null);
                complete();
            }
        }
    }
 }