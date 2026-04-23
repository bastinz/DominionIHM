package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.gui.Utils;

import java.util.concurrent.CompletableFuture;

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
    public void action(Player p, CompletableFuture<Void>f) {
        p.incrementMoney(2);
        f.complete(null);
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
