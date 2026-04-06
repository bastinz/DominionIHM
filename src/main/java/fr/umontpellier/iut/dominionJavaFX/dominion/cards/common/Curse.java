package fr.umontpellier.iut.dominionJavaFX.dominion.cards.common;

import fr.umontpellier.iut.dominionJavaFX.dominion.cards.CurseCard;

/**
 * Carte Malédiction (Curse)
 * <p>
 * -1 VP
 */
public class Curse extends CurseCard {
    public Curse() {
        super("Curse", 0);
    }

    @Override
    public int getVictoryValue() {
        return -1;
    }
}