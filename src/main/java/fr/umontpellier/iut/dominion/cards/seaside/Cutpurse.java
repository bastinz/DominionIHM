package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.AttackCard;
import fr.umontpellier.iut.dominion.cards.Card;
import fr.umontpellier.iut.dominion.gui.Utils;

/**
 * Carte Coupeur de bourse (Cutpurse)
 * <p>
 * +2 Pièces
 * Tous vos adversaires défaussent un Cuivre (Copper) (ou dévoilent une main
 * sans Cuivre).
 */
public class Cutpurse extends AttackCard {
    public Cutpurse() {
        super("Cutpurse", 4);
    }

    @Override
    public void action(Player p) {
        p.incrementMoney(2);
    }

    @Override
    public void attack(Player p, Player target) {
        Card copper = target.getCardsInHand().stream().filter(c -> c.hasName("Copper")).findFirst().orElse(null);
        if (copper != null) {
            target.log("%s discards %s".formatted(target.toLog(), copper.toLog()));
            target.moveToDiscard(copper);
        } else {
            target.log("%s reveals his hand: %s".formatted(target.toLog(), Utils.toLog(target.getCardsInHand())));
        }
    }
}
