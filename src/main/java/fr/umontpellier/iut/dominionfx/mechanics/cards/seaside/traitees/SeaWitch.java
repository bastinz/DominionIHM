package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations.SeaWitchState;

import java.util.concurrent.CompletableFuture;

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
    public void action(Player p, CompletableFuture<Void> f) {
        p.drawToHand(2);
        setHasDurationEffect(true);
        f.complete(null);
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
/*        int n = 2;
        while (!p.getCardsInHand().isEmpty() && n > 0) {
            Card cardToDiscard = p.chooseCardFromHand(
                    "%s: Discard %d card(s)".formatted(this.toLog(), n),
                    false);
            p.log("%s discards %s (%s)".formatted(p.toLog(), cardToDiscard.toLog(), this.toLog()));
            p.moveToDiscard(cardToDiscard);
            n -= 1;
        }
        setHasDurationEffect(false);*/
        p.setCurrentState(new SeaWitchState(p, this));
    }
}
