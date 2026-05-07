package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Cette classe présente les éléments appartenant un autre joueur que le joueur courant.
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class OtherPlayerView extends VBox {

    private ObjectProperty<? extends IPlayer> currentPlayer;

    @FXML private Label nameLabel;
    @FXML private Label handLabel;
    @FXML private Label drawLabel;
    @FXML private Label discardLabel;

    private IPlayer player;

    public OtherPlayerView(IPlayer player) {
        this.player = player;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/otherPlayer.fxml"));
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
    }

    private final ChangeListener<IPlayer> currentPlayerChangeListener = (ObservableValue<? extends IPlayer> observableValue, IPlayer oldPlayer, IPlayer newPlayer) -> {
         if (newPlayer != null) {
             nameLabel.setText(player.getName());
             handLabel.textProperty().bind(Bindings.concat("Hand : ", Bindings.size(player.getHand()).asString()));
             drawLabel.textProperty().bind(Bindings.concat("Draw : ", Bindings.size(player.getDraw()).asString()));
             discardLabel.textProperty().bind(Bindings.concat("Discard : ", Bindings.size(player.getDiscard()).asString()));
         }
     };

    protected void setCurrentPlayerChangeListener(ChangeListener<IPlayer> currentPlayerChangeListener) {
        currentPlayer.addListener(currentPlayerChangeListener);
    }


}