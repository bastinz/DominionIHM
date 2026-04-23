package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.HavenState;

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
        setHasDurationEffect(true);
        p.setCurrentState(new HavenState(p, this));
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.moveToHand(cardSetAside);
        cardSetAside = null;
        setHasDurationEffect(false);
    }


    public void setCardSetAside(Card cardSetAside) {
        this.cardSetAside = cardSetAside;
    }
}

/*Card c = p.chooseCardFromHand(
        "%s: Set aside a card from your hand".formatted(this),
        false);
        if (c != null) {
        p.log("sets %s aside".formatted(c.toLog()));
        p.moveToSetAside(c);
cardSetAside = c;
setHasDurationEffect(true);*/