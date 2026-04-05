package fr.umontpellier.iut.dominion.cards.common;

import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.TreasureCard;

/**
 * Carte Argent (Silver)
 * <p>
 * 2 Pièces
 */
public class Silver extends TreasureCard {
    public Silver() {
        super("Silver", 3);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);
        p.incrementNbSilverOrGoldPlayed();
    }
}
