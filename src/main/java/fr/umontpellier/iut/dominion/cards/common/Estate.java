package fr.umontpellier.iut.dominion.cards.common;

import fr.umontpellier.iut.dominion.cards.VictoryCard;

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