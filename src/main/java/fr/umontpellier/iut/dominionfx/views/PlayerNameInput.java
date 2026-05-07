package fr.umontpellier.iut.dominionfx.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class PlayerNameInput extends HBox {

    public PlayerNameInput(int number) {
        Label textPlayer = new Label("Player's name " + number + " : ");
        textPlayer.setFont(Font.font(18));
        TextField playerName = new TextField("");
        playerName.setPrefWidth(140);
        setAlignment(Pos.CENTER);
        HBox.setMargin(textPlayer, new Insets(0,10,0,0));
        setLayoutX(10);
        setLayoutY(145);
        setPrefWidth(423);
        setPrefHeight(48);
        getChildren().addAll(textPlayer, playerName);
    }
}
