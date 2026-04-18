package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire;

import fr.umontpellier.iut.dominionfx.mechanics.Button;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.gui.Utils;

import java.util.Arrays;

/**
 * Carte Village indigène (Native Village)
 * <p>
 * +2 Actions
 * Choisissez : placez la carte du haut de votre pioche, face cachée, sur votre
 * plateau Village indigène (vous pouvez consulter ces cartes à tout moment);
 * ou prenez en main toutes les cartes du plateau.
 */
public class NativeVillage extends ActionCard {
    public NativeVillage() {
        super("Native Village", 2);
    }

    @Override
    public void play(Player p) {
        p.incrementActions(2);
        String choice = p.chooseStringFromButtons(
                "%s: Choose one".formatted(this),
                Arrays.asList(new Button("Add card to mat", "add"), new Button("Take cards from mat", "take")),
                false);
        if (choice.equals("add")) {
            Card c = p.getCardFromDeck();
            if (c != null) {
                p.log("puts %s on Native Village mat (cards on mat: %s)".formatted(c.toLog(), Utils.toLog(p.getCardsOnNativeVillageMat())));
                p.moveToNativeVillageMat(c);
            }
        } else {
            p.log("takes cards from mat");
            p.moveToHand(p.getCardsOnNativeVillageMat());
        }
    }
}
