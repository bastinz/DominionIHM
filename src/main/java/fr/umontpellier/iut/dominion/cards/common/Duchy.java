package fr.umontpellier.iut.dominion.cards.common;

import fr.umontpellier.iut.dominion.cards.VictoryCard;

/**
 * Carte Duché (Duchy)
 * <p>
 * 3 VP
 */
public class Duchy extends VictoryCard {
    public Duchy() {
        super("Duchy", 5);
    }

    @Override
    public int getVictoryValue() {
        return 3;
    }
}