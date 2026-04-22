package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AmbassadorRevealState extends OnGoingActionState {

    private final CompletableFuture<Void> future;
    private final StringProperty revealedCardName;

    public AmbassadorRevealState(Player currentPlayer, CompletableFuture<Void> future) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Reveal a card from your hand");
        this.future = future;
        revealedCardName = new SimpleStringProperty();
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        List<String> availableChoices = currentPlayer.getNamesOfCardsInHand();
        if (!availableChoices.isEmpty() && availableChoices.contains(cardName)) {
            revealedCardName.setValue(cardName);
            currentPlayer.setCurrentState(new AmbassadorReturnToSupplyState(currentPlayer, cardName, future));
        }
    }

    public StringProperty revealedCardNameProperty() {
        return revealedCardName;
    }
}