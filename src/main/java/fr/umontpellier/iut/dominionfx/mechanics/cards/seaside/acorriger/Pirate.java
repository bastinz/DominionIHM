package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.acorriger;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.PirateReactionState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.PirateState;

import java.util.concurrent.CompletableFuture;

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
        p.getCurrentState().moveToNextPhase();
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
    public CompletableFuture<Void> reaction(Player p, Card gainedCard, Player owner) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        p.setCurrentState(new PirateReactionState(p, owner, this, future));
        return future;
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.setCurrentState(new PirateState(p,this));
    }
}

/*
Card supplyCard = p.chooseCardFromSupply(
        "%s: Gain a Treasure costing up to 6$".formatted(this),
        c -> c.hasType(CardType.TREASURE) && c.getCost() <= 6,
        false);
        if (supplyCard != null) {
Card gainedCard = p.getCardFromSupply(supplyCard.getName());
            p.log("%s gains %s (%s)".formatted(p.toLog(), gainedCard.toLog(), this.toLog()));
        p.gainToHand(gainedCard);
        }
setHasDurationEffect(false);*/
