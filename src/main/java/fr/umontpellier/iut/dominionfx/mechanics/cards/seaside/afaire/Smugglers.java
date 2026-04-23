package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.List;

/**
 * Contrebandiers (Smugglers)
 * <p>
 * Recevez un exemplaire d'une carte coûtant jusqu'à 6 Pièces que le joueur
 * à votre droite a reçues à son dernier tour.
 */
public class Smugglers extends ActionCard {
    public Smugglers() {
        super("Smugglers", 3);
    }

    @Override
    public void play(Player p) {
        List<String> possibleCardNames = p.getOtherPlayers().getLast().getCardsGainedThisTurn().stream()
                .filter(c -> c.getCost() <= 6)
                .map(Card::getName)
                .distinct()
                .toList();
        Card supplyCard = p.chooseCardFromSupply(
                "%s: Choose a card to gain".formatted(this),
                c -> possibleCardNames.contains(c.getName()),
                false);
        if (supplyCard != null) {
            Card gainedCard = p.getCardFromSupply(supplyCard.getName());
            p.gainToDiscard(gainedCard);
        }
        p.getCurrentState().moveToNextPhase();
    }
}
