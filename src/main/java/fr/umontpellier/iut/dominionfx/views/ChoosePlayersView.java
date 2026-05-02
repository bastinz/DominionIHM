package fr.umontpellier.iut.dominionfx.views;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Cette classe correspond à une nouvelle fenêtre permettant de choisir les noms des joueurs de la partie.

 * Lorsque l'utilisateur a fini de saisir les noms de joueurs, il demandera à démarrer la partie.
 */
public class ChoosePlayersView extends Stage {

    private final ObservableList<String> nomsJoueurs;
    @FXML
    private VBox joueursBox;
    @FXML
    private TextField nomJoueur1;
    @FXML
    private TextField nomJoueur2;

    public ChoosePlayersView() {
        nomsJoueurs = FXCollections.observableArrayList();
        initStyle(StageStyle.UNDECORATED);
        initModality(Modality.APPLICATION_MODAL);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/choixJoueurs.fxml"));
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setScene(new Scene(joueursBox));
    }

    public ObservableList<String> nomsJoueursProperty() {
        return nomsJoueurs;
    }

    public String[] getNomsJoueurs() {
        return nomsJoueurs.toArray(String[]::new);
    }

    /**
     * Définit l'action à exécuter lorsque la liste des participants est correctement initialisée
     */
    public void setNomsDesJoueursDefinisListener(ListChangeListener<String> quandLesNomsDesJoueursSontDefinis) {
        nomsJoueursProperty().addListener(quandLesNomsDesJoueursSontDefinis);
    }

    /**
     * Vérifie que tous les noms des participants sont renseignés
     * et affecte la liste définitive des participants
     */
    @FXML
    protected void setListeDesNomsDeJoueurs() {
        ArrayList<String> tempNamesList = new ArrayList<>();
        if (nomJoueur1.getText() == null || nomJoueur1.getText().isEmpty()) {
            tempNamesList.clear();
        } else
            tempNamesList.add(nomJoueur1.getText());
        if (nomJoueur2.getText() == null || nomJoueur2.getText().isEmpty()) {
            tempNamesList.clear();
        } else
            tempNamesList.add(nomJoueur2.getText());
        if (tempNamesList.size() ==  2) {
            hide();
            nomsJoueurs.clear();
            nomsJoueurs.addAll(tempNamesList);
        }
    }

}
