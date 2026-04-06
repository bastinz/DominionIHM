package fr.umontpellier.iut.dominionJavaFX;

import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;
import javafx.collections.ObservableList;

public interface IPlayer {

    void playTreasuresWasChosen();

    String getName();

    ObservableList<Card> getHand();
}