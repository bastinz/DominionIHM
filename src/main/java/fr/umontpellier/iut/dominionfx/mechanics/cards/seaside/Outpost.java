package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;

/**
 * Carte Avant-poste (Outpost)
 * <p>
 * Piochez seulement 3 cartes pour votre prochaine main.
 * Jouez un tour supplémentaire après celui-ci (mais pas un troisième
 * consécutif).
 */
public class Outpost extends ActionCard {
    public Outpost() {
        super("Outpost", 5);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        p.setNbCardsToDrawAtCleanup(3);
        setHasDurationEffect(true);
        Game game = p.getGame();
        if (game.getPreviousTurnPlayer() != p) {
            game.setSamePlayerShouldPlayExtraTurn(true);
        }
    }

    @Override
    public void onCleanup(Player p) {
        // si le joueur ne peut pas rejouer un tour, alors Outpost devrait être
        // défaussé à la fin du tour suivant
        super.onCleanup(p);
        setHasDurationEffect(false);
    }

    @Override
    public void atStartOfTurn(Player p) {
        setHasDurationEffect(false);
    }
}
