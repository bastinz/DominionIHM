package fr.umontpellier.iut.dominionfx;

import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

public interface IPlayer {

    void playTreasuresWasChosen();
    void cardInHandWasChosen(String supplyName);
    void addToMat();
    void takeFromMat();

    String getName();

    ObservableList<Card> getHand();
    ObservableList<Card> getInPlay();
    ObservableList<Card> getIslandMat();
    ObservableList<Card> getNativeVillageMat();
    ObservableList<Card> getDraw(); // A REVOIR : est-ce que la size suffirait?
    ObservableList<Card> getDiscard(); // A REVOIR : est-ce que la size suffirait?
    IntegerProperty numberOfActionsProperty();
    IntegerProperty numberOfBuysProperty();
    IntegerProperty moneyProperty();
    BooleanProperty nativeVillagePlayedProperty();
}