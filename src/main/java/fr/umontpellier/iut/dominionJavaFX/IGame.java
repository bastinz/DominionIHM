package fr.umontpellier.iut.dominionJavaFX;

import javafx.beans.property.ObjectProperty;

public interface IGame {
    void skipWasChosen();

    ObjectProperty<String> instructionProperty();
    ObjectProperty<? extends IPlayer> currentPlayerProperty();
}