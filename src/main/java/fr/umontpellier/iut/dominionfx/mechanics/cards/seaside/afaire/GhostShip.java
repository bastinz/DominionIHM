package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.concurrent.CompletableFuture;

/**
 * Carte Vaisseau fantôme (Ghost Ship)
 * <p>
 * +2 Cartes
 * Tous vos adversaires ayant au moins 4 cartes en main placent des cartes
 * de leur main sur leur pioche jusqu'à avoir 3 cartes en main.
 */
public class GhostShip extends AttackCard {

    public GhostShip() {
        super("Ghost Ship", 5);
    }

    @Override
    public void action(Player p, CompletableFuture<Void> f) {
        p.drawToHand(2);
        f.complete(null);
    }

    @Override
    public void attack(Player p, Player target) {
        while (target.getCardsInHand().size() > 3) {
            Card c = target.chooseCardFromHand(
                    "%s: Discard down to 3 cards".formatted(this),
                    false);
            target.log("%s puts %s onto deck".formatted(target.toLog(), c.toLog()));
            target.moveToDraw(c);
        }
    }
}
