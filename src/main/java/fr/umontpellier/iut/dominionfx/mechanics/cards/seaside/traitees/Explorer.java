package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.ExplorerState;

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
        p.setCurrentState(new ExplorerState(p));
    }
}
