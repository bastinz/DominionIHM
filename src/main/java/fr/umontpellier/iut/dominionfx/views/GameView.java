package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.IGame;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Cette classe correspond à la fenêtre principale de l'application.
 *
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 *
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class GameView extends HBox {

    private final IGame game;

    @FXML
    private VBox initialView;

    @FXML
    private Label instructionLabel;

    @FXML
    private FlowPane supplyPane;

    @FXML
    private CurrentPlayerView currentPlayerPane;

    @FXML
    private Button skipButton;

    public GameView(IGame game) {
        this.game = game;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/main.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createBindings() {
        setSupplyPane();
        instructionLabel.textProperty().bind(game.instructionProperty());
    }

    private Node createSupplyPile(SupplyPile pile) {
        Button pileButton = new Button(pile.getName());
        pileButton.setId(pile.getName());
        pileButton.textProperty().bind(Bindings.concat(pile.getName(), " ", pile.getCost(), " ", pile.sizeProperty().asString()));
        pileButton.setUserData(pile);
        pileButton.setOnMouseClicked(event -> {game.supplyCardWasChosen(pile.getName());});
        return pileButton;
    }

    private void setSupplyPane() {
        supplyPane.getChildren().setAll(
                game.getSupplyPiles().stream()
                        .map(this::createSupplyPile)
                        .toList()
        );
    }

    @FXML
    void skip() {
        game.skipWasChosen();
    }

    @FXML
    private void initialize() {
        createBindings();
    }

}

