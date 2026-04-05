package fr.umontpellier.iut.dominionJavaFX.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.Player;
import fr.umontpellier.iut.dominionJavaFX.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.cards.Card;

/**
 * Carte Sauveteur (Salvager)
 * <p>
 * +1 Achat
 * Écartez une carte de votre main. +1 Pièce par Pièce de son coût.
 */
public class Salvager extends ActionCard {
    public Salvager() {
        super("Salvager", 4);
    }

    @Override
    public void play(Player p) {
        p.incrementBuys(1);
        Card cardToTrash = p.chooseCardFromHand(
                "%s: Trash a card from your hand".formatted(this),
                false);
        if (cardToTrash != null) {
            p.log("trashes %s".formatted(cardToTrash.toLog()));
            p.incrementMoney(cardToTrash.getCost());
            p.moveToTrash(cardToTrash);
        }
    }
}
