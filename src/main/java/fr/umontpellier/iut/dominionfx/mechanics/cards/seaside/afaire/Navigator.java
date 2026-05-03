package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire;

import fr.umontpellier.iut.dominionfx.mechanics.Button;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<Void> play(Player p) {
        p.incrementMoney(2);
        List<Card> drawnCards = p.drawCards(5);
        if (drawnCards.isEmpty()) {
            return CompletableFuture.completedFuture(null);
        }

        String choice = p.chooseStringFromButtons(
                "%s: Do you want to discard %s?".formatted(this, drawnCards),
                Arrays.asList(new Button("Yes", "y"), new Button("No", "n")),
                false);
        if (choice.equals("y")) {
            p.moveToDiscard(drawnCards);
        } else {
            while (!drawnCards.isEmpty()) {
                Card c = p.chooseCardFromButtons(
                        "%s: Put cards back on deck (last on top)".formatted(this),
                        drawnCards,
                        false);
                p.moveToDraw(c);
            }
        }
        return CompletableFuture.completedFuture(null);
    }

}
