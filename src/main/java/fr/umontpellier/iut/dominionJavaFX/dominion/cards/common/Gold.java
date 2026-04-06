package fr.umontpellier.iut.dominionJavaFX.dominion.cards.common;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.TreasureCard;

/**
 * Carte Or (Gold)
 * <p>
 * 3 Pièces
 */
public class Gold extends TreasureCard {
    public Gold() {
        super("Gold", 6);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(3);
        p.incrementNbSilverOrGoldPlayed();
    }
}
