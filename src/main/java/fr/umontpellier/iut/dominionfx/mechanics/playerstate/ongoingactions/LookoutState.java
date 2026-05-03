package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class LookoutState extends PlayerState {

    private ObservableList<Card> topCards = FXCollections.observableArrayList();
    private boolean trashChoice;

    public LookoutState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Choose a card to trash");
        for (int i = 0; i < 3 ; i++)
            topCards.add(currentPlayer.getDraw().get(i)); // currentPlayer.drawCards(3);// à revoir
        currentPlayer.getGame().setTemporaryCards(topCards, currentPlayer.getDraw());
        trashChoice = true;
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        List<String> availableCards = topCards.stream().map(Card::getName).toList();
        if (!availableCards.isEmpty() && availableCards.contains(cardName)) {
            Card cardToPlay = topCards.stream().filter(c -> c.getName().equals(cardName)).findFirst().orElse(null);
            if (trashChoice) {
                trashChoice = false;
                currentPlayer.moveToTrash(cardToPlay);
                topCards.remove(cardToPlay);
                getGame().instructionProperty().setValue("Choose a card to discard");
            } else {
                currentPlayer.getGame().setTemporaryCards(null, null); // à revoir
                currentPlayer.moveToDiscard(cardToPlay);
                complete();
            }
        }
    }
 }