package fr.umontpellier.iut.dominionJavaFX.dominion.cards.common;

import fr.umontpellier.iut.dominionJavaFX.dominion.cards.VictoryCard;

/**
 * Carte Domaine (Estate)
 * <p>
 * 1 VP
 */
public class Estate extends VictoryCard {
    public Estate() {
        super("Estate", 2);
    }

    @Override
    public int getVictoryValue() {
        return 1;
    }
}