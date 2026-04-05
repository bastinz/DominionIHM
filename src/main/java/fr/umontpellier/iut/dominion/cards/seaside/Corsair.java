package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.ArrayList;
import java.util.List;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.AttackCard;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Corsaire (Corsair)
 * <p>
 * +2 Pièces
 * Au début de votre prochain tour, +1 Carte. D'ici là, chacun de vos
 * adversaires écarte le premier Argent ou Or qu'il joue à chaque tour.
 */
public class Corsair extends AttackCard {
    private final List<Player> attackedPlayers = new ArrayList<>();

    public Corsair() {
        super("Corsair", 5);
        addType(CardType.DURATION);
    }

    @Override
    public void action(Player p) {
        p.incrementMoney(2);
        attackedPlayers.clear();
        setHasDurationEffect(true);
    }

    @Override
    public void attack(Player p, Player target) {
        attackedPlayers.add(target);
    }

    @Override
    public void onPlayerPlayCard(Player p, Card playedCard, Player owner) {
        if (attackedPlayers.contains(p)
                && (playedCard.hasName("Silver") || playedCard.hasName("Gold"))
                && p.getNbSilverOrGoldPlayed() == 1) {
            p.log("trashes it (%s)".formatted(this.toLog()));
            p.moveToTrash(playedCard);
        }
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.drawToHand();
        setHasDurationEffect(false);
    }
}
