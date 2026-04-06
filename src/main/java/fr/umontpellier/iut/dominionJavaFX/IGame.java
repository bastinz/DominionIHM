package fr.umontpellier.iut.dominionJavaFX;

import javafx.beans.property.ObjectProperty;

public interface IGame {
    ObjectProperty<String> instructionProperty();
    ObjectProperty<? extends IPlayer> currentPlayerProperty();
}