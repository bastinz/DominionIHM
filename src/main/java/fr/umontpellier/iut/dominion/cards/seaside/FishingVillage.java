package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.ActionCard;

/**
 * Carte Village de pêcheurs (Fishing Village)
 * <p>
 * +2 Actions
 * +1 Pièce
 * Au début de votre prochain tour, +1 Action et +1 Pièce.
 */
public class FishingVillage extends ActionCard {
    public FishingVillage() {
        super("Fishing Village", 3);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.incrementActions(2);
        p.incrementMoney(1);
        setHasDurationEffect(true);
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementActions(1);
        p.incrementMoney(1);
        setHasDurationEffect(false);
    }
}