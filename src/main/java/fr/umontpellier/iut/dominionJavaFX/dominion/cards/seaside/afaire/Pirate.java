package fr.umontpellier.iut.dominionJavaFX.dominion.cards.seaside.afaire;

import fr.umontpellier.iut.dominionJavaFX.dominion.CardType;
import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

/**
 * Carte Pirate
 * <p>
 * Au début de votre prochain tour, recevez en main un Trésor coûtant jusqu'à
 * 6 Pièces.
 * Quand un joueur reçoit un Trésor, vous pouvez jouer cette carte depuis votre
 * main.
 */
public class Pirate extends ActionCard {
    public Pirate() {
        super("Pirate", 5);
        addType(CardType.DURATION);
        addType(CardType.REACTION);
    }

    @Override
    public void play(Player p) {
        // Rien à faire au moment où la carte est jouée
        setHasDurationEffect(true);
    }

    @Override
    public boolean canReactToPlayerGainCard(Player p, Card gainedCard, Player owner) {
        return gainedCard.hasType(CardType.TREASURE);
    }

    @Override
    public void reactToPlayerGainCard(Player p, Card gainedCard, Player owner) {
        owner.playCard(this);
    }

    @Override
    public void atStartOfTurn(Player p) {
        Card supplyCard = p.chooseCardFromSupply(
                "%s: Gain a Treasure costing up to 6$".formatted(this),
                c -> c.hasType(CardType.TREASURE) && c.getCost() <= 6,
                false);
        if (supplyCard != null) {
            Card gainedCard = p.getCardFromSupply(supplyCard.getName());
            p.log("%s gains %s (%s)".formatted(p.toLog(), gainedCard.toLog(), this.toLog()));
            p.gainToHand(gainedCard);
        }
        setHasDurationEffect(false);
    }
}
