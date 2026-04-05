package fr.umontpellier.iut.dominionJavaFX.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.Button;
import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.cards.Card;
import fr.umontpellier.iut.dominion.gui.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Carte Navigateur (Navigator)
 * <p>
 * +2 Pièces
 * Consultez les 5 premières cartes de votre pioche.
 * Défaussez-les toutes ou replacez-les sur votre pioche dans l'ordre de
 * votre choix.
 */
public class Navigator extends ActionCard {

    public Navigator() {
        super("Navigator", 4);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);
        List<Card> drawnCards = p.drawCards(5);
        if (drawnCards.isEmpty()) {
            return;
        }

        p.log("reveals %s".formatted(Utils.toLog(drawnCards)));
        String choice = p.chooseStringFromButtons(
                "%s: Do you want to discard %s?".formatted(this, drawnCards),
                Arrays.asList(new Button("Yes", "y"), new Button("No", "n")),
                false);
        if (choice.equals("y")) {
            p.log("discards %s".formatted(drawnCards));
            p.moveToDiscard(drawnCards);
        } else {
            p.log("puts cards back on deck");
            while (!drawnCards.isEmpty()) {
                Card c = p.chooseCardFromButtons(
                        "%s: Put cards back on deck (last on top)".formatted(this),
                        drawnCards,
                        false);
                p.moveToDraw(c);
            }
        }
    }

}
