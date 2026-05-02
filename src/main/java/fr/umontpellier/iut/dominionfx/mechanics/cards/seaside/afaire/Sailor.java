package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.afaire;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.ActionCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.durations.SailorStartOfTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions.SailorAndDurationGainedState;

import java.util.concurrent.CompletableFuture;

/**
 * Carte Navigatrice (Sailor)
 * <p>
 * +1 Action
 * Une fois durant ce tour, quand vous recevez une carte Durée (Duration),
 * vous pouvez la jouer.
 * Au début de votre prochain tour, +2 Pièces et vous pouvez écarter une carte
 * de votre main.
 */
public class Sailor extends ActionCard {
    private boolean canPlayDuration = false;

    public Sailor() {
        super("Sailor", 4);
        addType(CardType.DURATION);
    }

    @Override
    public void play(Player p) {
        canPlayDuration = true;
        p.incrementActions(1);
        setHasDurationEffect(true);
        p.getCurrentState().moveToNextPhase();
    }

    @Override
    public void onCleanup(Player p) {
        canPlayDuration = false;
        super.onCleanup(p);
    }

    @Override
    public void atStartOfTurn(Player p) {
        p.incrementMoney(2);
        p.setCurrentState(new SailorStartOfTurnState(p, this));
    }

    @Override
    public CompletableFuture<Void> onPlayerGainCard(Player p, Card gainedCard, Player owner) {
        if (canPlayDuration && gainedCard.hasType(CardType.DURATION) && p == owner) {
            p.setWaitForYesOrNo(true);
            SailorAndDurationGainedState phase = new SailorAndDurationGainedState(p, owner, gainedCard, this);
            p.setCurrentState(phase);
            return phase.getCompletionFuture();
        }
        return CompletableFuture.completedFuture(null);
    }

    public void cannotPlayDurationAnyMore() {
        canPlayDuration = false;
    }
}
/*
    String choice = p.chooseStringFromButtons(
            "%s: Do you want to play %s?".formatted(this, gainedCard),
            Arrays.asList(new Button("Yes", "y"), new Button("No", "n")),
            false);
    if (choice.equals("y")) {
        canPlayDuration = false;
        p.playCard(gainedCard);
    }
}
*/