package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.AttackCard;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Ambassadeur (Ambassador)
 * <p>
 * Dévoilez une carte de votre main.
 * Replacez, de votre main, à la réserve, jusqu'à 2 exemplaires de cette carte.
 * Ensuite, tous vos adversaires reçoivent un exemplaire de cette carte.
 */
public class Ambassador extends AttackCard {
    private Card revealedCard;

    public Ambassador() {
        super("Ambassador", 3);
    }

    @Override
    public void action(Player p) {
        revealedCard = p.chooseCardFromHand(
                "%s: Reveal a card from your hand".formatted(this),
                false);
        if (revealedCard == null) {
            return;
        } else {
            p.log("reveals %s".formatted(revealedCard.toLog()));
        }
        int n = 2;
        while (n > 0) {
            Card returnedCard = p.chooseCardFromHand(
                    "%s: Return up to %d copies of %s".formatted(this, n, revealedCard.getName()),
                    c -> c.hasSameNameAs(revealedCard),
                    true);
            if (returnedCard == null) {
                break;
            }
            p.log("returns %s to the supply".formatted(returnedCard.toLog()));
            p.moveToSupply(returnedCard);
            n -= 1;
        }
    }

    @Override
    public void attack(Player p, Player target) {
        Card c = target.getCardFromSupply(revealedCard.getName());
        if (c != null) {
            target.log("%s gains %s".formatted(target.toLog(), revealedCard.toLog()));
            target.gainToDiscard(c);
        }
    }
}
