package fr.umontpellier.iut.dominionJavaFX.cards;

import fr.umontpellier.iut.dominionJavaFX.CardType;
import fr.umontpellier.iut.dominionJavaFX.Player;

/**
 * Les cartes Victoire
 */
public abstract class VictoryCard extends Card {

    /**
     * Constructeur
     */
    public VictoryCard(String name, int cost) {
        super(name, cost);
        addType(CardType.VICTORY);
    }

    @Override
    public void play(Player p) {
    }
}