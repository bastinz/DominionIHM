package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

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
    public CompletableFuture<Void> action(Player p) {
        p.incrementMoney(2);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> attack(Player p, Player target) {
        Card copper = target.getCardsInHand().stream().filter(c -> c.hasName("Copper")).findFirst().orElse(null);
        if (copper != null) {
            target.moveToDiscard(copper);
        }
        return CompletableFuture.completedFuture(null);
    }
}
