package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Cette classe présente les éléments appartenant au joueur courant.
 * On y définit les bindings sur le joueur courant, ainsi que le listener à exécuter lorsque ce joueur change
 */
public class AllOtherPlayersView extends VBox {

    private ObjectProperty<? extends IPlayer> currentPlayer;
    private Node otherPlayerView;

    @FXML private VBox allOtherPlayersPane;

    public AllOtherPlayersView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/allOtherPlayers.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void bindCurrentPlayer() {
        currentPlayer = DominionIHM.getGame().currentPlayerProperty();
        initOtherPlayers();
        setCurrentPlayerChangeListener(currentPlayerChangeListener);
    }

    private void initOtherPlayers() {
        for (IPlayer p : DominionIHM.getGame().getPlayers()) {
            allOtherPlayersPane.getChildren().add(createPlayerNode(p));
        }
        otherPlayerView = allOtherPlayersPane.getChildren().get(allOtherPlayersPane.getChildren().size()-1);
        allOtherPlayersPane.getChildren().remove(allOtherPlayersPane.getChildren().size() - 1);
    }

    @FXML
    private void initialize() {
        bindCurrentPlayer();
    }

    protected void setCurrentPlayerChangeListener(ChangeListener<IPlayer> currentPlayerChangeListener) {
        currentPlayer.addListener(currentPlayerChangeListener);
    }

    private final ChangeListener<IPlayer> currentPlayerChangeListener = (ObservableValue<? extends IPlayer> observableValue, IPlayer oldPlayer, IPlayer newPlayer) -> {
        allOtherPlayersPane.getChildren().add(otherPlayerView);
        otherPlayerView = allOtherPlayersPane.getChildren().remove(0);
    };

    private Node createPlayerNode(IPlayer player) {
        OtherPlayerView playerPane = new OtherPlayerView(player);
        playerPane.setUserData(player);
        playerPane.setId(player.getName());
        return playerPane;
    }
}