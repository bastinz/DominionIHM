package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

/**
 * Carte Singe (Monkey)
 * <p>
 * Jusqu'à votre prochain tour, quand le joueur à votre droite reçoit une
 * carte, +1 Carte.
 * Au début de votre prochain tour, +1 Carte.
 */
public class Monkey extends ActionCard {
    private boolean isActive;

    public Monkey() {
        super("Monkey", 3);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        isActive = true;
        setHasDurationEffect(true);
        p.getCurrentState().moveToNextPhase();
    }

    @Override
    public void onPlayerGainCard(Player p, Card gainedCard, Player owner) {
        if (p == owner.getOtherPlayers().getLast() && isActive) {
            Card c = owner.drawToHand();
            if (c != null) {
                owner.log("%s draws %s (%s)".formatted(owner.toLog(), c.toLog(), this.toLog()));
                // si la carte piochée est une réaction (Pirate) elle peut réagir immédiatement
                // au gain de la carte
//                c.reactToPlayerGainCard(p, gainedCard, owner);

                p.getCurrentState().moveToNextExecutingEffect(gainedCard);
            }
        }
    }

    @Override
    public void atStartOfTurn(Player p) {
        isActive = false;
        p.drawToHand();
        setHasDurationEffect(false);
    }
}
