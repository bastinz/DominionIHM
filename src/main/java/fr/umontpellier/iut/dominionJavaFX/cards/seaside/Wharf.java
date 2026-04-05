package fr.umontpellier.iut.dominionJavaFX.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.CardType;
import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.ActionCard;

/**
 * Carte Quai (Wharf)
 * <p>
 * Maintenant et au début de votre prochain tour : +2 Cartes et +1 Achat.
 */
public class Wharf extends ActionCard {

    public Wharf() {
        super("Wharf", 5);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.drawToHand(2);
        p.incrementBuys(1);
        setHasDurationEffect(true);
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand(2);
        p.incrementBuys(1);
        setHasDurationEffect(false);
    }
}
