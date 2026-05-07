package fr.umontpellier.iut.dominionfx.views;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir les noms des joueurs de la partie.

 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class ChoosePlayersView extends Stage implements Initializable {

    private final ObservableList<String> playersNames;
    @FXML private VBox playersPane;
    @FXML private VBox playersNamesPane;
    @FXML private ComboBox<Integer> numberOfPlayers;

    public ChoosePlayersView() {
        playersNames = FXCollections.observableArrayList();
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/playersChoice.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<String> playersNamesProperty() {
        return playersNames;
    }

    public String[] getPlayersNames() {
        return playersNames.toArray(new String[0]);
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setPlayersNamesDefinedListener(ListChangeListener<String> whenPlayersNamesAreDefined) {
        playersNamesProperty().addListener(whenPlayersNamesAreDefined);
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    @FXML
    protected void setPlayersNamesList() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        for (int i = 1; i <= getNumberOfPlayers(); i++) {
            String name = getPlayerByIndex(i);
            if (name == null || name.equals("")) {
                tempNamesList.clear();
                break;
            } else
                tempNamesList.add(name);
        }
        if (!tempNamesList.isEmpty()) {
            hide();
            playersNames.clear();
            playersNames.addAll(tempNamesList);
        }
    }

    /**
     * Retourne le nombre de participants à la partie que l'utilisateur a renseigné
     */
    protected int getNumberOfPlayers() {
        return numberOfPlayers.getSelectionModel().getSelectedItem();
    }

    /**
     * Retourne le nom que l'utilisateur a renseigné pour le ième participant à la partie
     *
     * @param playerNumber : le numéro du participant
     */
    protected String getPlayerByIndex(int playerNumber) {
        return ((TextField) ((HBox) playersNamesPane.getChildren().get(playerNumber)).getChildren().get(1)).getText();
    }

    protected void setNumberOfPlayersChanged(ChangeListener<Integer> numberOfPlayersChangedListener) {
        numberOfPlayers.getSelectionModel().selectedItemProperty().addListener(numberOfPlayersChangedListener);
    }

    private final ChangeListener<Integer> numberOfPlayersChangedListener = (observableValue, oldNbPlayers, newNbPlayers) -> {
        if (oldNbPlayers < newNbPlayers)
            for (int i = oldNbPlayers + 1; i <= newNbPlayers; i++)
                ajoutZoneNomDeJoueurParNumero(i);
        else
            for (int i = 0; i < (oldNbPlayers - newNbPlayers); i++)
                enleveZoneNomDeJoueur();
    };

    protected void ajoutZoneNomDeJoueurParNumero(int playerNumber) {
        playersNamesPane.getChildren().add(new PlayerNameInput(playerNumber));
    }

    protected void enleveZoneNomDeJoueur() {
        playersNamesPane.getChildren().remove(playersNamesPane.getChildren().get(playersNamesPane.getChildren().size() - 1));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberOfPlayers.getItems().addAll(2, 3, 4);
        numberOfPlayers.getSelectionModel().select(2);
        setNumberOfPlayersChanged(numberOfPlayersChangedListener);
        setScene(new Scene(playersPane));
    }
}