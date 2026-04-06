package fr.umontpellier.iut.dominionJavaFX.views;

import fr.umontpellier.iut.dominionJavaFX.DominionIHM;
import fr.umontpellier.iut.dominionJavaFX.IPlayer;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 *
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class CurrentPlayerView extends VBox {

    @FXML
    private Label nameLabel;

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

     private final ChangeListener<IPlayer> currentPlayerChangeListener = (ObservableValue<? extends IPlayer> observableValue, IPlayer oldJoueur, IPlayer newJoueur) -> {
        Platform.runLater(() -> {
            if (newJoueur != null) {
                nameLabel.setText(newJoueur.getName());
            }
        });
    };

    protected void setCurrentPlayerChangeListener(ChangeListener<IPlayer> currentPlayerChangeListener) {
        currentPlayer.addListener(currentPlayerChangeListener);
    }

    @FXML
    private void initialize() {
        bindCurrentPlayer();
    }

}
