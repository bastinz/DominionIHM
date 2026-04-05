package fr.umontpellier.iut.dominion.cards.common;

import fr.umontpellier.iut.dominion.cards.VictoryCard;

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