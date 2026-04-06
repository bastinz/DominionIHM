package fr.umontpellier.iut.dominionJavaFX.views;

import fr.umontpellier.iut.dominionJavaFX.IGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    }

    private void createInnerComponentsBindings() {
        // à faire une fois que la vue du jeu est totalement instanciée donc pas dans le constructeur
    }

    @FXML
    VBox initialView;

    @FXML
    Label instructionLabel;

    @FXML
    Button passButton;

    @FXML
    void pass() {
//        getJeu().passerAEteChoisi();
        System.out.println("Vous avez cliqué le bouton passer");
    }

}

//    EventHandler<? super MouseEvent> actionPasserParDefaut = (mouseEvent ->
//            Platform.runLater(() -> {
//                getJeu().passerAEteChoisi();
//            }));

