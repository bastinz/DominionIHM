package fr.umontpellier.iut.dominionfx;


import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public interface IGame {
    void skipWasChosen();
    void supplyCardWasChosen(String supplyName);
    void temporaryCardWasChosen(String cardName);

    ObjectProperty<String> instructionProperty();
    ObjectProperty<? extends IPlayer> currentPlayerProperty();
    ObservableList<Card> temporaryCardsProperty();
    BooleanProperty gameOverProperty();

    List<SupplyPile> getSupplyPiles();
    ArrayList<IPlayer> getPlayers();
    IPlayer getNextPlayer();
}