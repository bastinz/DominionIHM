package fr.umontpellier.iut.dominionJavaFX.dominion.cards.common;

import fr.umontpellier.iut.dominionJavaFX.dominion.cards.VictoryCard;

/**
 * Carte Province
 * <p>
 * 6 VP
 */
public class Province extends VictoryCard {
    public Province() {
        super("Province", 8);
    }

    @Override
    public int getVictoryValue() {
        return 6;
    }
}