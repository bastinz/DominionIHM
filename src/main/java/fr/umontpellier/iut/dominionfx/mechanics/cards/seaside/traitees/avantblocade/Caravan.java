package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.avantblocade;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;

/**
 * Carte Caravane (Caravan)
 * <p>
 * +1 Carte
 * +1 Action
 * Au début de votre prochain tour, +1 Carte.
 */
public class Caravan extends ActionCard {
    public Caravan() {
        super("Caravan", 4);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.drawToHand();
        p.incrementActions(1);
        setHasDurationEffect(true);
        p.getCurrentState().moveToNextPhase();
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand();
        setHasDurationEffect(false);
    }
}
