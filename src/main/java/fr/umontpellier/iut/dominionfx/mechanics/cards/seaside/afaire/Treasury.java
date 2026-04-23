package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire;

import fr.umontpellier.iut.dominionfx.mechanics.Button;
import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;

import java.util.Arrays;

/**
 * Carte Trésorerie (Treasury)
 * <p>
 * +1 Carte
 * +1 Action
 * +1 Pièce
 * À la fin de votre phase Achat, si vous n'avez pas reçu de carte Victoire
 * durant celle-ci, vous pouvez placer cette carte sur votre pioche.
 */
public class Treasury extends ActionCard {

    public Treasury() {
        super("Treasury", 5);
    }

    @Override
    public void play(Player p) {
        p.drawToHand(1);
        p.incrementActions(1);
        p.incrementMoney(1);
        p.getCurrentState().moveToNextPhase();
    }

    @Override
    public void onCleanup(Player p) {
        if (p.getCardsBoughtThisTurn().stream().noneMatch(c -> c.hasType(CardType.VICTORY))) {
            String choice = p.chooseStringFromButtons(
                    "%s: Do you want to put Treasury onto your deck?".formatted(this),
                    Arrays.asList(new Button("Yes", "y"), new Button("No", "n")),
                    false);
            if (choice.equals("y")) {
                p.log("%s puts %s onto deck".formatted(p.toLog(), this.toLog()));
                p.moveToDraw(this);
            } else {
                super.onCleanup(p);
            }
        } else {
            super.onCleanup(p);
        }
    }
}
