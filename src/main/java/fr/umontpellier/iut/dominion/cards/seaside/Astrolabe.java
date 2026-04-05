package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.TreasureCard;

/**
 * Carte Astrolabe
 * <p>
 * Maintenant et au début de votre prochain tour :
 * +1 Pièce
 * +1 Achat
 */
public class Astrolabe extends TreasureCard {
    public Astrolabe() {
        super("Astrolabe", 3);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(1);
        p.incrementBuys(1);
        setHasDurationEffect(true);
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementMoney(1);
        p.incrementBuys(1);
        setHasDurationEffect(false);
    }
}
