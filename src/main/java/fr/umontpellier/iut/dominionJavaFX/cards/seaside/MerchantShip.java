package fr.umontpellier.iut.dominionJavaFX.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.CardType;
import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.ActionCard;

/**
 * Carte Navire marchand (Merchant Ship)
 * <p>
 * Maintenant et au début de votre prochain tour, +Pièces.
 */
public class MerchantShip extends ActionCard {
    public MerchantShip() {
        super("Merchant Ship", 5);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);
        setHasDurationEffect(true);
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementMoney(2);
        setHasDurationEffect(false);
    }
}
