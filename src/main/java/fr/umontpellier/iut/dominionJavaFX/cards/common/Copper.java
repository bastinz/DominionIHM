package fr.umontpellier.iut.dominionJavaFX.cards.common;

import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.TreasureCard;

/**
 * Carte Cuivre (Copper)
 * <p>
 * 1 Pièce
 */
public class Copper extends TreasureCard {
    public Copper() {
        super("Copper", 0);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(1);
    }
}
