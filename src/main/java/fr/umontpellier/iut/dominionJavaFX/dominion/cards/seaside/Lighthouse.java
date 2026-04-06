package fr.umontpellier.iut.dominionJavaFX.dominion.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.dominion.CardType;
import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.ActionCard;

/**
 * Carte Phare (Lighthouse)
 * <p>
 * +1 Action
 * Maintenant et au début de votre prochain tour, +1 Pièce.
 * D'ici là, les cartes Attaque jouées par vos adversaires ne vous affectent
 * pas.
 */
public class Lighthouse extends ActionCard {
    private boolean isProtection = false;

    public Lighthouse() {
        super("Lighthouse", 2);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.incrementActions(1);
        p.incrementMoney(1);
        isProtection = true;
        setHasDurationEffect(true);
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementMoney(1);
        isProtection = false;
        setHasDurationEffect(false);
    }

    @Override
    public boolean isProtectionFromAttack() {
        return isProtection;
    }
}