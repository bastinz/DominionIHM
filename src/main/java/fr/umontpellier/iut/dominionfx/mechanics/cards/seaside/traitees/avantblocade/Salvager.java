package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.avantblocade;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.SalvagerState;

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
        p.setCurrentState(new SalvagerState(p));
    }
}