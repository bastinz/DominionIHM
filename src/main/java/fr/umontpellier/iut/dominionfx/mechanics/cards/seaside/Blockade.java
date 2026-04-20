package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.BlockadeState;

import java.util.ArrayList;
import java.util.List;

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

    public Blockade() {
        super("Blockade", 4);
        addType(CardType.DURATION);
    }

    @Override
    public void action(Player p) {
        player = p;
        attackedPlayers.clear();
        p.setCurrentState(new BlockadeState(p, this));
/*        Card chosenCard = p.chooseCardFromSupply(
                "%s: Gain a card costing up to 4$".formatted(this),
                c -> c.getCost() <= 4,
                false);
        if (chosenCard != null) {
            Card card = p.getCardFromSupply(chosenCard.getName());
            p.log("gains %s and sets it aside".formatted(card.toLog()));
            p.gainToSetAside(card);
            cardSetAside = card;
            // il faut mémoriser le joueur qui a joué la carte pour pouvoir plus
            // tard chercher la carte mise de côté dans la liste des cartes mises
            // de côté du joueur (à cause de l'interaction possible avec Sailor)
            player = p;
        }*/
    }

    @Override
    public void attack(Player p, Player target) {
        attackedPlayers.add(target);
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
    }

    @Override
    public boolean getHasDurationEffect() {
        if (!player.getCardsSetAside().contains(cardSetAside)) {
            // la carte n'est plus mise de côté
            cardSetAside = null;
        }
        return cardSetAside != null;
    }

    @Override
    public void atStartOfTurn(Player p) {
        if (p.getCardsSetAside().contains(cardSetAside)) {
            p.moveToHand(cardSetAside);
        }
        cardSetAside = null;
    }

    public void setCardSetAside(Card cardSetAside) {
        this.cardSetAside = cardSetAside;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}


/*
Card chosenCard = p.chooseCardFromSupply(
        "%s: Gain a card costing up to 4$".formatted(this),
        c -> c.getCost() <= 4,
        false);
        if (chosenCard != null) {
Card card = p.getCardFromSupply(chosenCard.getName());
            p.log("gains %s and sets it aside".formatted(card.toLog()));
        p.gainToSetAside(card);
cardSetAside = card;
// il faut mémoriser le joueur qui a joué la carte pour pouvoir plus
// tard chercher la carte mise de côté dans la liste des cartes mises
// de côté du joueur (à cause de l'interaction possible avec Sailor)
player = p;*/
