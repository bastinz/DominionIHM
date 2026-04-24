package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.GhostShipState;

import java.util.concurrent.CompletableFuture;

/**
 * Carte Vaisseau fantôme (Ghost Ship)
 * <p>
 * +2 Cartes
 * Tous vos adversaires ayant au moins 4 cartes en main placent des cartes
 * de leur main sur leur pioche jusqu'à avoir 3 cartes en main.
 */
public class GhostShip extends AttackCard {
    private Player target;

    public GhostShip() {
        super("Ghost Ship", 5);
    }

    @Override
    public void action(Player p, CompletableFuture<Void> f) {
        p.drawToHand(2);
        f.complete(null);
    }

    @Override
    public CompletableFuture<Void> attack(Player p, Player target) {
        this.target = target;
        CompletableFuture<Void> future = new CompletableFuture<>();
        if (!target.isProtectedFromAttack()) {
            int nbCardsToDiscard = target.getHand().size() - 3;
            if (nbCardsToDiscard > 0) {
                p.getGame().setTemporaryCards(target.getHand());
                p.setCurrentState(new GhostShipState(p, this, nbCardsToDiscard, future));
            } else {
                future.complete(null);
            }
        } else {
            future.complete(null);
        }
        return future;
    }

    public void discardFromTargetHand(String cardName) {
        target.getHand().stream()
                .filter(card -> card.getName().equals(cardName))
                .findFirst()
                .ifPresent(card -> target.getHand().remove(card));
    }
}
