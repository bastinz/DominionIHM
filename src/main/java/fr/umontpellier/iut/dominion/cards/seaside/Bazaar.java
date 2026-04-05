package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.ActionCard;

/**
 * Carte Bazar (Bazaar)
 * <p>
 * +1 Carte
 * +2 Actions
 * +1 Pièce
 */
public class Bazaar extends ActionCard {
    public Bazaar() {
        super("Bazaar", 5);
    }

    @Override
    public void play(Player p) {
        p.drawToHand();
        p.incrementActions(2);
        p.incrementMoney(1);
    }
}
