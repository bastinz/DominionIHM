package fr.umontpellier.iut.dominionJavaFX.dominion.playerstate;

import fr.umontpellier.iut.dominionJavaFX.dominion.CardType;
import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

import java.util.List;

public class PlayTreasuresState extends PlayerState {
    public PlayTreasuresState(Player currentPlayer) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Treasure phase");
    }

    public void passer() {
        finDuTour();
    }

    public void playTreasuresWasChosen() {
/*        List<String> choixPossibles = currentPlayer.getPokemonsDeBaseEnMain();
        if (!choixPossibles.isEmpty() && choixPossibles.contains(numPokemon)) {
            currentPlayer.jouerCarteEnMain(numPokemon);
            choixPossibles.setEtatCourant(new InitialisationPokemonsDeBanc(joueurActif));
        }*/
        currentPlayer.playTreasures();
        // ici il faut passer à la phase suivante
        // pour l'instant on passe au joueur suivant
//        passer();
    }


}
