package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.EmbargoState;

/**
 * Carte Embargo
 * <p>
 * +2 Pièces
 * Écartez ceci pour placer un jeton Embargo sur une pile de la réserve.
 * (Pendant le reste de la partie, quand un joueur achète une carte de cette
 * pile, il reçoit une Malédiction (Curse).)
 */
public class Embargo extends ActionCard {

    public Embargo() {
        super("Embargo", 2);
    }

    @Override
    public void play(Player p) {
        p.incrementMoney(2);
        p.moveToTrash(this);
        p.setCurrentState(new EmbargoState(p));
    }
}