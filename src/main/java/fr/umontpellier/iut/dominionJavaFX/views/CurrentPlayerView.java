package fr.umontpellier.iut.dominionJavaFX.views;

import fr.umontpellier.iut.dominionJavaFX.DominionIHM;
import fr.umontpellier.iut.dominionJavaFX.IPlayer;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class CurrentPlayerView extends VBox {

    @FXML
    private Label nameLabel;

    @FXML
    private HBox handPane;

    @FXML
    private HBox inPlayPane;

    private ObjectProperty<? extends IPlayer> currentPlayer;

    public CurrentPlayerView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/currentPlayer.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bindCurrentPlayer() {
        currentPlayer = DominionIHM.getGame().currentPlayerProperty();
        setCurrentPlayerChangeListener(currentPlayerChangeListener);
    }

    private final ListChangeListener<? super Card> handListener = change ->
    Platform.runLater(() -> {
    while (change.next()) {
        if (change.wasAdded()) {
            for (Card card : change.getAddedSubList()) {
                handPane.getChildren().add(createCardNode(card));
            }
         }
        if (change.wasRemoved()) {
            for (Card card : change.getRemoved()) {
                handPane.getChildren().removeIf(node -> node.getUserData() == card);
            }
        }
    }
    });

    private final ListChangeListener<? super Card> inPlayListener = change ->
    Platform.runLater(() -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (Card card : change.getAddedSubList()) {
                    inPlayPane.getChildren().add(createCardNode(card));
                }
            }
            if (change.wasRemoved()) {
                for (Card card : change.getRemoved()) {
                    inPlayPane.getChildren().removeIf(node -> node.getUserData() == card);
                }
            }
        }
    });

     private final ChangeListener<IPlayer> currentPlayerChangeListener = (ObservableValue<? extends IPlayer> observableValue, IPlayer oldPlayer, IPlayer newPlayer) ->
     Platform.runLater(() -> {
     if (newPlayer != null) {
         nameLabel.setText(newPlayer.getName());
         refreshHand();
         refreshInPlay();
         newPlayer.getHand().addListener(handListener);
         newPlayer.getInPlay().addListener(inPlayListener);
     }
     });

    private void refreshHand() {
        handPane.getChildren().setAll(
                currentPlayer.getValue().getHand().stream()
                        .map(this::createCardNode)
                        .toList()
        );
    }

    private void refreshInPlay() {
        inPlayPane.getChildren().setAll(
                currentPlayer.getValue().getInPlay().stream()
                        .map(this::createCardNode)
                        .toList()
        );
    }

    private Node createCardNode(Card card) {
        Button cardButton = new Button(card.getName());
        cardButton.setUserData(card);
        return cardButton;
    }

    protected void setCurrentPlayerChangeListener(ChangeListener<IPlayer> currentPlayerChangeListener) {
        currentPlayer.addListener(currentPlayerChangeListener);
    }

    @FXML
    private void initialize() {
        bindCurrentPlayer();
    }

    @FXML
    void playTreasures() {
        currentPlayer.getValue().playTreasuresWasChosen();
    }

}