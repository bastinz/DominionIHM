package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.BlockadeState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Carte Blocus (Blockade)
 * <p>
 * Recevez une carte coûtant jusqu'à 4 Pièces, en la mettant de côté.
 * Au début de votre prochain tour, prenez-la en main
 * Tant qu'elle est mise de côté, quand un autre joueur en reçoit un
 * exemplaire durant leur tour, il reçoit une Malédiction (Curse).
 */
public class Blockade extends AttackCard {
    private Card cardSetAside;
    private Player player;
    private final List<Player> attackedPlayers = new ArrayList<>();

    private CompletableFuture<Void> future;

    public Blockade() {
        super("Blockade", 4);
        addType(CardType.DURATION);
    }

    @Override
    public void action(Player p, CompletableFuture<Void> f) {
        this.future = f;
        player = p;
        attackedPlayers.clear();
        setHasDurationEffect(true);
        p.setCurrentState(new BlockadeState(p, this));
    }

    public void endAction(String cardName) {
        Card card = player.getCardFromSupply(cardName);
        player.gainToSetAside(card);
        this.cardSetAside = card;
        future.complete(null);
    }

    @Override
    public CompletableFuture<Void> attack(Player p, Player target) {
        attackedPlayers.add(target);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void onPlayerGainCard(Player p, Card gainedCard, Player owner) {
        if (attackedPlayers.contains(p)
                && cardSetAside.hasSameNameAs(gainedCard)
                && p.getGame().currentPlayer() == p) {
            Card curse = p.getCardFromSupply("Curse");
            if (curse != null) {
                p.log("gains %s (%s)".formatted(curse.toLog(), this.toLog()));
                p.gainToDiscard(curse);
            }
        }
        p.getCurrentState().moveToNextExecutingEffect(gainedCard);
    }

    @Override
    public boolean getHasDurationEffect() {
        if (!player.getCardsSetAside().contains(cardSetAside)) {
            // la carte n'est plus mise de côté
            cardSetAside = null;
        }
        setHasDurationEffect(cardSetAside != null);
        return cardSetAside != null;
    }

    @Override
    public void atStartOfTurn(Player p) {
        if (p.getCardsSetAside().contains(cardSetAside)) {
            p.moveToHand(cardSetAside);
        }
        cardSetAside = null;
        setHasDurationEffect(false);
    }
}
