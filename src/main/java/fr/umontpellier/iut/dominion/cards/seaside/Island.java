package fr.umontpellier.iut.dominion.cards.seaside;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.ActionCard;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Île (Island)
 * <p>
 * 2 VP
 * Placez cette carte et une carte de votre main sur votre plateau Île (Island
 * Mat).
 */
public class Island extends ActionCard {
    public Island() {
        super("Island", 4);
        addType(CardType.VICTORY);
    }

    @Override
    public void play(Player p) {
        Card c = p.chooseCardFromHand(
                "%s: Choose a card to set aside".formatted(this),
                false);
        p.moveToIslandMat(this);
        if (c != null) {
            p.log("puts %s and %s on Island mat".formatted(c.toLog(), this.toLog()));
            p.moveToIslandMat(c);
        } else {
            p.log("puts %s on Island mat".formatted(this.toLog()));
        }
    }

    @Override
    public int getVictoryValue() {
        return 2;
    }
}
