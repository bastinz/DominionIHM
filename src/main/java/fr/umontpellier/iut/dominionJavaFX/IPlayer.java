package fr.umontpellier.iut.dominionJavaFX;

import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

public interface IPlayer {

    void playTreasuresWasChosen();
    void cardInHandWasChosen(String supplyName);

    String getName();

    ObservableList<Card> getHand();
    ObservableList<Card> getInPlay();
    ObservableList<Card> getDraw(); // A REVOIR : est-ce que la size suffirait?
    ObservableList<Card> getDiscard(); // A REVOIR : est-ce que la size suffirait?
    IntegerProperty moneyProperty();
}