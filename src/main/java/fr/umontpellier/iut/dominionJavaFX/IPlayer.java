package fr.umontpellier.iut.dominionJavaFX;

import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;
import javafx.collections.ObservableList;

public interface IPlayer {

    void playTreasuresWasChosen();
    void cardInHandWasChosen(String supplyName);

    String getName();

    ObservableList<Card> getHand();
    ObservableList<Card> getInPlay();
}