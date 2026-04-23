package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.avantblocade;


import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.TreasureCard;

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
