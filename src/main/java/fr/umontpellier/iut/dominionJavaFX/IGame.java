package fr.umontpellier.iut.dominionJavaFX;


import fr.umontpellier.iut.dominionJavaFX.dominion.SupplyPile;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import java.util.List;

public interface IGame {
    void skipWasChosen();
    void supplyWasChosen(String supplyName);

    ObjectProperty<String> instructionProperty();
    ObjectProperty<? extends IPlayer> currentPlayerProperty();
    List<SupplyPile> getSupplyPiles();
}