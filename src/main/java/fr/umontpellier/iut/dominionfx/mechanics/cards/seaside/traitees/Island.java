package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.IslandState;

/**
 * Carte Île (Island)
 * <p>
 * 2 VP
 * Placez cette carte et une carte de votre main sur votre plateau Île (Island
 * Mat).
 */
public class Island extends ActionCard {
    public Island() {
        super("Island", 4);
        addType(CardType.VICTORY);
    }

    @Override
    public void play(Player p) {
        p.setCurrentState(new IslandState(p));
    }

    @Override
    public int getVictoryValue() {
        return 2;
    }
}
