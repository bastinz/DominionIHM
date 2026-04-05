package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.ActionCard;

/**
 * Carte Embargo
 * <p>
 * +2 Pièces
 * Écartez ceci pour placer un jeton Embargo sur une pile de la réserve.
 * (Pendant le reste de la partie, quand un joueur achète une carte de cette
 * pile, il reçoit une Malédiction (Curse).)
 */
public class Embargo extends ActionCard {

    public Embargo() {
        super("Embargo", 2);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);
        p.moveToTrash(this);
        List<String> options = new ArrayList<>();
        for (String name : p.getGame().getAllSupplyPileNames()) {
            options.add("SUPPLY:" + name);
        }
        String choice = p.choose(
                "%s: Choose a supply pile".formatted(this),
                options,
                new ArrayList<>(),
                false);
        String supplyName = choice.split(":")[1];
        p.log("adds an Embargo token on %s".formatted(supplyName));
        p.getGame().addEmbargoToken(supplyName);
    }
}
