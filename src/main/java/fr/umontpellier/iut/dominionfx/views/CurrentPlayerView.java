package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.IPlayer;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.beans.binding.Bindings;
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

    private ObjectProperty<? extends IPlayer> currentPlayer;

    @FXML
    private Label nameLabel, moneyLabel, drawLabel, discardLabel, actionsLabel, buysLabel;

/*    @FXML
    private Label discardLabel;*/

    @FXML
    private HBox handPane;

    @FXML
    private HBox inPlayPane;

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

    @FXML
    private void initialize() {
        bindCurrentPlayer();
        for (Player p : DominionIHM.getGame().getPlayers()) {
            p.getHand().addListener(handListener);
            p.getInPlay().addListener(inPlayListener);
        };
    }

    @FXML
    void playTreasures() {
        currentPlayer().playTreasuresWasChosen();
    }

    private final ListChangeListener<? super Card> handListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (Card card : change.getAddedSubList()) {
                    handPane.getChildren().add(createCardNodeInHand(card));
                }
            }
            if (change.wasRemoved()) {
                for (Card card : change.getRemoved()) {
                    handPane.getChildren().removeIf(node -> node.getUserData() == card);
                }
            }
        }
    };

    private final ListChangeListener<? super Card> inPlayListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (Card card : change.getAddedSubList()) {
                    inPlayPane.getChildren().add(createCardNodeInPlay(card));
                }
            }
            if (change.wasRemoved()) {
                for (Card card : change.getRemoved()) {
                    inPlayPane.getChildren().removeIf(node -> node.getUserData() == card);
                }
            }
        }
    };

    private final ChangeListener<IPlayer> currentPlayerChangeListener = (ObservableValue<? extends IPlayer> observableValue, IPlayer oldPlayer, IPlayer newPlayer) -> {
         if (newPlayer != null) {
             nameLabel.setText(newPlayer.getName());
             refreshHand();
             refreshInPlay();
             moneyLabel.textProperty().bind(Bindings.concat("Money : ", currentPlayer().moneyProperty().asString()));
             drawLabel.textProperty().bind(Bindings.concat("Draw : ", Bindings.size(currentPlayer().getDraw()).asString()));
             discardLabel.textProperty().bind(Bindings.concat("Discard : ", Bindings.size(currentPlayer().getDiscard()).asString()));
             actionsLabel.textProperty().bind(Bindings.concat("Actions : ", currentPlayer().numberOfActionsProperty().asString()));
             buysLabel.textProperty().bind(Bindings.concat("Buys : ", currentPlayer().numberOfBuysProperty().asString()));
         }
     };

    protected void setCurrentPlayerChangeListener(ChangeListener<IPlayer> currentPlayerChangeListener) {
        currentPlayer.addListener(currentPlayerChangeListener);
    }

    private void refreshHand() {
        handPane.getChildren().setAll(
                currentPlayer().getHand().stream()
                        .map(this::createCardNodeInHand)
                        .toList()
        );
    }

    private void refreshInPlay() {
        inPlayPane.getChildren().clear();
        inPlayPane.getChildren().setAll(
                currentPlayer().getInPlay().stream()
                        .map(this::createCardNodeInPlay)
                        .toList()
        );
    }

    private Node createCardNodeInHand(Card card) {
        Button cardButton = new Button(card.getName());
        cardButton.setUserData(card);
        cardButton.setId(card.getName());
        cardButton.setOnMouseClicked(event -> {
            currentPlayer().cardInHandWasChosen(card.getName());}
        );
        return cardButton;
    }

    private Node createCardNodeInPlay(Card card) {
        Button cardButton = new Button(card.getName());
        cardButton.setDisable(true);
        cardButton.setUserData(card);
        return cardButton;
    }

    private IPlayer currentPlayer() {
        return currentPlayer.getValue();
    }

}