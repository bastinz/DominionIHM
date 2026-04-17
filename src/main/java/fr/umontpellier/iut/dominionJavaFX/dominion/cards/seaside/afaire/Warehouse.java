package fr.umontpellier.iut.dominionJavaFX.dominion.cards.seaside.afaire;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

/**
 * Carte Entrepôt (Warehouse)
 * <p>
 * +3 Cartes
 * +1 Action
 * Défaussez 3 cartes.
 */
public class Warehouse extends ActionCard {

    public Warehouse() {
        super("Warehouse", 3);
    }

    @Override
    public void play(Player p) {
        p.drawToHand(3);
        p.incrementActions(1);
        int nbCardsToDiscard = 3;
        while (!p.getCardsInHand().isEmpty() && nbCardsToDiscard > 0) {
            Card cardToDiscard = p.chooseCardFromHand(
                    "%s: Discard %d card(s)".formatted(this, nbCardsToDiscard),
                    false);
            p.log("discards %s".formatted(cardToDiscard.toLog()));
            p.moveToDiscard(cardToDiscard);
            nbCardsToDiscard -= 1;
        }
    }
}
