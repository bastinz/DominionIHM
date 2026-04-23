package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.avantblocade;

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
        p.setCurrentState(new HavenState(p, this));
    }

    public void endPlay(Player p, String cardName) {
        Card card = p.getCardFromHand(cardName);
        p.moveToSetAside(card);
        this.cardSetAside = card;
        setHasDurationEffect(true);
        p.getCurrentState().moveToNextPhase();
    }

    /*            Card card = currentPlayer.getCardFromHand(cardName);
            currentPlayer.moveToSetAside(card);
            haven.setCardSetAside(card);
            currentPlayer.getCurrentState().moveToNextPhase();*/

    @Override
    public void atStartOfTurn(Player p) {
        p.moveToHand(cardSetAside);
        cardSetAside = null;
        setHasDurationEffect(false);
    }
}

