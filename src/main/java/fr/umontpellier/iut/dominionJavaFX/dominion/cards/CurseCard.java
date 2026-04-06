package fr.umontpellier.iut.dominionJavaFX.dominion.cards;

import fr.umontpellier.iut.dominionJavaFX.dominion.CardType;
import fr.umontpellier.iut.dominionJavaFX.dominion.Player;

/**
 * Les cartes Malédiction
 */
public abstract class CurseCard extends Card {

    /**
     * Constructeur
     */
    public CurseCard(String name, int cost) {
        super(name, cost);
        addType(CardType.CURSE);
    }

    @Override
    public void play(Player p) {
    }

}