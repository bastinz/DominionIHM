package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.concurrent.CompletableFuture;

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
    public CompletableFuture<Void> onPlayerGainCard(Player p, Card gainedCard, Player owner) {
        if (p == owner.getOtherPlayers().getLast() && isActive) {
            Card c = owner.drawToHand();
            if (c != null) {
                // si la carte piochée est une réaction (Pirate) elle peut réagir immédiatement
                // au gain de la carte
                c.reactToPlayerGainCard(p, gainedCard, owner);
            }
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public void atStartOfTurn(Player p) {
        isActive = false;
        p.drawToHand();
        setHasDurationEffect(false);
    }
}
