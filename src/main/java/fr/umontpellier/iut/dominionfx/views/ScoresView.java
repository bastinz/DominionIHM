package fr.umontpellier.iut.dominionfx.views;

import fr.umontpellier.iut.dominionfx.DominionIHM;
import fr.umontpellier.iut.dominionfx.IPlayer;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class ScoresView extends VBox {

    private DominionIHM ihm;

    @FXML
    private Label winner;
    private Stage resultsStage;

    public ScoresView(DominionIHM ihm) {
        this.ihm = ihm;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/scoresView.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setFinalScores(displayWinnerDetails);
    }

    /**
     * Définit l'action à exécuter lorsque la partie est terminée
     */
    protected void setFinalScores(ChangeListener<Boolean> gameOverListener) {
        ihm.getGame().gameOverProperty().addListener(gameOverListener);
    }

    ChangeListener<Boolean> displayWinnerDetails = (observableValue, aBoolean, t1) -> {
        StringBuilder sb = new StringBuilder();
        for (IPlayer p : ihm.getGame().getPlayers()) {
            sb.append(p.getName()).append(" : ").append(p.getVictoryPoints()).append("\n");
        }
        winner.setText(sb.toString());
        ihm.getPrimaryStage().hide();

        resultsStage = new Stage();
        resultsStage.initStyle(StageStyle.UNDECORATED);
        resultsStage.initModality(Modality.APPLICATION_MODAL);
        resultsStage.setScene(new Scene(this));
        resultsStage.show();
    };

    /**
     * Retourne au début (renseignement des noms des joueurs) pour redémarrer une partie
     */
    @FXML
    protected void replay() {
        resultsStage.close();
        ihm.setPlayersAndGame();
        ihm.startGame();
    }

    /**
     * Termine la partie
     */
    @FXML
    protected void stopGame() {
        ihm.stopGame();
    }

}
