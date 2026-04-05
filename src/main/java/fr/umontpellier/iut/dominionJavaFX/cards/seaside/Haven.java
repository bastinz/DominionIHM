package fr.umontpellier.iut.dominionJavaFX.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.CardType;
import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.cards.Card;

/**
 * Carte Havre (Haven)
 * <p>
 * +1 Carte
 * +1 Action
 * Mettez de côté une carte de votre main face cachée (sous cette carte).
 * Au début de votre prochain tour, prenez-la en main.
 */
public class Haven extends ActionCard {
    private Card cardSetAside;

    public Haven() {
        super("Haven", 2);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.drawToHand();
        p.incrementActions(1);
        Card c = p.chooseCardFromHand(
                "%s: Set aside a card from your hand".formatted(this),
                false);
        if (c != null) {
            p.log("sets %s aside".formatted(c.toLog()));
            p.moveToSetAside(c);
            cardSetAside = c;
            setHasDurationEffect(true);
        }
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.moveToHand(cardSetAside);
        cardSetAside = null;
        setHasDurationEffect(false);
    }
}
