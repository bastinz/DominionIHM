package fr.umontpellier.iut.dominionJavaFX.dominion.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.dominion.CardType;
import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.ActionCard;

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
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand();
        setHasDurationEffect(false);
    }
}
