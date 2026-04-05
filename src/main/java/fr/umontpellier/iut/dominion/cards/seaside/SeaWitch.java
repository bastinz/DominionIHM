package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.AttackCard;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Sorcière marine (Sea Witch)
 * <p>
 * +2 Cartes
 * Tous vos adversaires reçoivent une Malédiction (Curse).
 * Au début de votre prochain tour, +2 Cartes, puis défaussez 2 cartes.
 */
public class SeaWitch extends AttackCard {
    public SeaWitch() {
        super("Sea Witch", 5);
        addType(CardType.DURATION);
    }

    @Override
    public void action(Player p) {
        p.drawToHand(2);
        setHasDurationEffect(true);
    }

    @Override
    public void attack(Player p, Player target) {
        Card curse = target.getCardFromSupply("Curse");
        if (curse != null) {
            target.log("%s gains %s".formatted(target.toLog(), curse.toLog()));
            target.gainToDiscard(curse);
        }
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand(2);
        int n = 2;
        while (!p.getCardsInHand().isEmpty() && n > 0) {
            Card cardToDiscard = p.chooseCardFromHand(
                    "%s: Discard %d card(s)".formatted(this.toLog(), n),
                    false);
            p.log("%s discards %s (%s)".formatted(p.toLog(), cardToDiscard.toLog(), this.toLog()));
            p.moveToDiscard(cardToDiscard);
            n -= 1;
        }
        setHasDurationEffect(false);
    }
}
