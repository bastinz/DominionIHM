package fr.umontpellier.iut.dominion.cards.seaside;

import java.util.Arrays;

import fr.umontpellier.iut.dominion.Button;
import fr.umontpellier.iut.dominion.Player;
import fr.umontpellier.iut.dominion.cards.ActionCard;
import fr.umontpellier.iut.dominion.cards.Card;

/**
 * Carte Plongeur de perles (Pearl Diver)
 * <p>
 * +1 Carte
 * +1 Action
 * Consultez la carte du bas de votre pioche. Vous pouvez la placer sur le haut.
 */
public class PearlDiver extends ActionCard {

    public PearlDiver() {
        super("Pearl Diver", 2);
    }

    @Override
    public void play(Player p) {
        p.drawToHand(1);
        p.incrementActions(1);
        Card c = p.getBottomCardOfDeck();

        if (c != null) {
            String choice = p.chooseStringFromButtons(
                    "%s: Do you want to put %s on top of your deck?".formatted(this, c),
                    Arrays.asList(new Button("Yes", "y"), new Button("No", "n")),
                    false);
            if (choice.equals("y")) {
                p.log("puts bottom card on top of deck");
                p.moveToDraw(c); // retire la carte du bas de la pioche et la place sur le dessus
            }
        }
    }
}
