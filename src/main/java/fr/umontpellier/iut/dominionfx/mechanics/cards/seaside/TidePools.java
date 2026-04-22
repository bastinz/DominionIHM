package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations.TidePoolsState;

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
        p.setCurrentState(new TidePoolsState(p, this));
    }
}
