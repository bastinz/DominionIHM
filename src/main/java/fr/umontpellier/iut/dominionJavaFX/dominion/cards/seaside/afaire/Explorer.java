package fr.umontpellier.iut.dominionJavaFX.dominion.cards.seaside.afaire;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

/**
 * Carte Explorateur (Explorer)
 * <p>
 * Vous pouvez dévoiler une Province de votre main. Si vous le faites, recevez
 * un Or (Gold) en main. Sinon, recevez un Argent (Silver) en main.
 */
public class Explorer extends ActionCard {

    public Explorer() {
        super("Explorer", 5);
    }

    @Override
    public void play(Player p) {
        Card province = p.chooseCardFromHand(
                "%s: You may reveal a Province".formatted(this),
                c -> c.hasName("Province"), true);
        Card gainedCard;
        if (province != null) {
            p.log("reveals %s".formatted(province.toLog()));
            gainedCard = p.getCardFromSupply("Gold");
        } else {
            gainedCard = p.getCardFromSupply("Silver");
        }
        if (gainedCard != null) {
            p.log("gains %s in hand".formatted(gainedCard.toLog()));
            p.gainToHand(gainedCard);
        }
    }
}
