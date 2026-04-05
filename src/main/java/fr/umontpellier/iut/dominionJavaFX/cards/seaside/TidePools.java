package fr.umontpellier.iut.dominionJavaFX.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.CardType;
import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.cards.Card;

/**
 * Carte Marée (Tide Pools)
 * <p>
 * +3 Cartes
 * +1 Action
 * Au début de votre prochain tour, défaussez 2 cartes.
 */
public class TidePools extends ActionCard {

    public TidePools() {
        super("Tide Pools", 4);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.drawToHand(3);
        p.incrementActions(1);
        setHasDurationEffect(true);
    }

    @Override
    public void atStartOfTurn(Player p) {
        int nbCardsToDiscard = 2;
        while (!p.getCardsInHand().isEmpty() && nbCardsToDiscard > 0) {
            Card cardToDiscard = p.chooseCardFromHand(
                    "%s: Discard %d card(s)".formatted(this, nbCardsToDiscard),
                    false);
            p.log("discards %s".formatted(cardToDiscard.toLog()));
            p.moveToDiscard(cardToDiscard);
            nbCardsToDiscard -= 1;
        }
        setHasDurationEffect(false);
    }

}
