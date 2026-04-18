package fr.umontpellier.iut.dominionfx;


import fr.umontpellier.iut.dominionfx.IPlayer;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;

import java.util.List;

public interface IGame {
    void skipWasChosen();
    void supplyCardWasChosen(String supplyName);

    ObjectProperty<String> instructionProperty();
    ObjectProperty<? extends IPlayer> currentPlayerProperty();
    List<SupplyPile> getSupplyPiles();
}