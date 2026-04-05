package fr.umontpellier.iut.dominionJavaFX.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.cards.Card;
import fr.umontpellier.iut.dominion.gui.Utils;

import java.util.List;

/**
 * Carte Vigie (Lookout)
 * <p>
 * +1 Action
 * Consultez les 3 premières cartes des votre pioche. Écartez-en une.
 * Défaussez-en une. Placez la carte restante sur le haut de votre pioche.
 */
public class Lookout extends ActionCard {
    public Lookout() {
        super("Lookout", 3);
    }

    @Override
    public void play(Player p) {
        p.incrementActions(1);
        List<Card> topCards = p.drawCards(3);
        // carte à écarter
        Card c = p.chooseCardFromButtons(
                "%s: Choose a card to trash".formatted(this),
                topCards,
                false);
        if (c != null) {
            p.log("trashes %s".formatted(c.toLog()));
            p.moveToTrash(c);
        }
        // carte à défausser
        c = p.chooseCardFromButtons(
                "%s: Choose a card to discard".formatted(this),
                topCards,
                false);
        if (c != null) {
            p.log("discards %s".formatted(c.toLog()));
            p.moveToDiscard(c);
        }
        // carte à remettre sur le dessus de la pioche
        p.log("places %s on top of deck".formatted(Utils.toLog(topCards)));
        p.moveToDraw(topCards);
    }

}
