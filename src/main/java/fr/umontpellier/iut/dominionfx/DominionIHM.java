package fr.umontpellier.iut.dominionfx;

import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.views.GameView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DominionIHM extends Application {

    public static final double pourcentageEcran = .55;/*.95*/
    /*    private ChoosePlayersView choosePlayersView;*/
    private Stage primaryStage;
    private static Game game;

    final private boolean withChoosePlayersView = false;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        if (partieTest) // on joue pour les tests
            startGame();
        else {
            if (withChoosePlayersView) {
/*            choosePlayersView = new ChoosePlayersView();
            choosePlayersView.setNomsDesJoueursDefinisListener(quandLesNomsJoueursSontDefinis);
            choosePlayersView.show();*/
            } else {
                setPlayersAndGame();
                startGame();
            }
        }
    }

    public void startGame() {
        GameView gameView = new GameView(game);
//        Scene scene = new Scene(gameView, Screen.getPrimary().getBounds().getWidth() * pourcentageEcran,  Screen.getPrimary().getBounds().getHeight() * pourcentageEcran); // la scene doit être créée avant de mettre en place les bindings
        Scene scene = new Scene(gameView, 1200,  500); // la scene doit être créée avant de mettre en place les bindings
        game.runNew();
//        startGameService(); // le service doit être démarré après que les bindings ont été mis en place

        primaryStage.setScene(scene);
        primaryStage.setTitle("Dominion-Seaside");
//        primaryStage.centerOnScreen();
        primaryStage.setOnCloseRequest(event -> {
            this.onStopGame();
            event.consume();
        });
        primaryStage.show();
    }

    private static void setPlayersAndGame() {
        String[] playerNames;
/*        if (withChoosePlayersView)
            playerNames = choosePlayersView.getNomsJoueurs();
        else {*/
        playerNames = new String[2];
        playerNames[0] = "Marco";
        playerNames[1] = "Polo";
//        }
        String[] kingdomCards = selectKingdomCards();

        game = new Game(playerNames, kingdomCards);
    }

    private boolean partieTest = false;

    public DominionIHM(boolean partieTest) { // ajouté pour les tests
        this.partieTest = partieTest;
    }

    public DominionIHM() {} // pour une partie sans tests

    public static void setGame(Game game) {
        DominionIHM.game = game;
    }

    private static String[] selectKingdomCards() {
        // Liste de toutes les cartes disponibles
        ArrayList<String> allKingdomCards = getAllKingdomCards();
        Collections.shuffle(allKingdomCards);
        // Cartes royaume à utiliser
        // Option 1. Aucune carte royaume (uniquement les cartes communes)
        // String[] kingdomCards = new String[]{};

        // Option 2. Liste explicite de cartes royaume à utiliser (le nombre de cartes peut être quelconque)
        String[] kingdomCards = new String[]{"Bazaar", "Warehouse", "Smugglers", "Sea Hag"};

        // Option 3. Choix aléatoire de 10 cartes parmi la liste complète allKingdomCards définie précédemment
//          String[] kingdomCards = allKingdomCards.subList(0, 10).toArray(new String[10]);
        return kingdomCards;
    }
    public static String[] getRandomKingdomCards() {
        return getAllKingdomCards().subList(0, 10).toArray(new String[10]);
    }
    public static ArrayList<String> getAllKingdomCards() {
        ArrayList<String> allKingdomCards = new ArrayList<>(Arrays.asList(
                "Ambassador",
                "Astrolabe",
                "Bazaar",
                "Blockade",
                "Caravan",
                "Corsair",
                "Cutpurse",
                "Embargo",
                "Explorer",
                "Fishing Village",
                "Ghost Ship",
                "Haven",
                "Island",
                "Lighthouse",
                "Lookout",
                "Merchant Ship",
                "Monkey",
                "Native Village",
                "Navigator",
                "Outpost",
                "Pearl Diver",
                "Pirate",
                "Pirate Ship",
                "Sailor",
                "Salvager",
                "Sea Chart",
                "Sea Hag",
                "Sea Witch",
                "Smugglers",
                "Tactician",
                "Tide Pools",
                "Treasure Map",
                "Treasury",
                "Warehouse",
                "Wharf"
        ));
        return allKingdomCards;
    }

/*    private final ListChangeListener<String> quandLesNomsJoueursSontDefinis = change -> {
        if (!vueChoixJoueurs.getNomsJoueurs().isEmpty())
            demarrerPartie();
    };*/

    public void onStopGame() {
/*        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setContentText("On arrête de jouer ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            serviceDuJeu.getJeu().cancel();*/
            Platform.exit();
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Game getGame() {
        return game;
    }
}