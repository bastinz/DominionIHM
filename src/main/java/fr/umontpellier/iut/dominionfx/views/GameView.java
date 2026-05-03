package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.IGame;
import fr.umontpellier.iut.dominionfx.mechanics.SupplyPile;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
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
 * <p>
 * Elle est initialisée avec une référence sur la partie en cours (Jeu).
 * <p>
 * On y définit les bindings sur les éléments internes qui peuvent changer
 * (le joueur courant, les 5 cartes Wagons visibles, les destinations lors de l'étape d'initialisation de la partie, ...)
 * ainsi que les listeners à exécuter lorsque ces éléments changent
 */
public class GameView extends HBox {

    private final IGame game;

    @FXML private VBox initialView;
    @FXML private Label instructionLabel;
    @FXML private FlowPane supplyPane;
    @FXML private CurrentPlayerView currentPlayerPane;
    @FXML private Button skipButton;
    @FXML private HBox temporaryCards;

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
//        game.temporaryCardsProperty().getValue().addListener(temporaryCardsListener);
//        game.temporaryCardsProperty().addListener(temporaryListListener);
        game.temporaryCardsProperty().addListener(temporaryListListener);
    }

    private Node createSupplyPile(SupplyPile pile) {
        Button pileButton = new Button(pile.getName());
        pileButton.setId(pile.getName());
        pileButton.textProperty().bind(Bindings.concat(pile.getName(), " ", pile.getCost(), " ", pile.sizeProperty().asString(),
                Bindings.when(Bindings.notEqual(0, pile.nbEmbargoTokensProperty())).then(Bindings.concat(" E", pile.nbEmbargoTokensProperty().asString())).otherwise("")));
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

    private final ListChangeListener<? super Card> temporaryListListener = change -> {
        while (change.next()) {
            if (change.wasAdded()) {
                for (Card card : change.getAddedSubList()) {
                    temporaryCards.getChildren().add(createTemporaryCardNode(card));
                }
            }
            if (change.wasRemoved()) {
                for (Card card : change.getRemoved()) {
                    temporaryCards.getChildren().removeIf(node -> node.getUserData() == card);
                }
            }
        }
    };

    private Node createTemporaryCardNode(Card card) {
        Button cardButton = new Button(card.getName());
        cardButton.setUserData(card);
        cardButton.setId(card.getName());
        cardButton.setOnMouseClicked(event -> {
            game.temporaryCardWasChosen(card.getName());}
        );
        return cardButton;
    }
}
