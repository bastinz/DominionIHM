package fr.umontpellier.iut.dominionJavaFX;

import fr.umontpellier.iut.dominionJavaFX.dominion.SupplyPile;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

public interface IGame {
    void skipWasChosen();
    void supplyWasChosen(String supplyName);

    ObjectProperty<String> instructionProperty();
    ObjectProperty<? extends IPlayer> currentPlayerProperty();
    ObservableList<SupplyPile> getSupplyPiles();
}