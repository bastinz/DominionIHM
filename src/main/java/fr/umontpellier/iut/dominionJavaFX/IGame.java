package fr.umontpellier.iut.dominionJavaFX;


import fr.umontpellier.iut.dominionJavaFX.dominion.SupplyPile;
import javafx.beans.property.ObjectProperty;

import java.util.List;

public interface IGame {
    void skipWasChosen();
    void supplyCardWasChosen(String supplyName);

    ObjectProperty<String> instructionProperty();
    ObjectProperty<? extends IPlayer> currentPlayerProperty();
    List<SupplyPile> getSupplyPiles();
}