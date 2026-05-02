package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class ExecutingEffectStateV2 extends PlayerState {

    private List<Player> playersExecutingEffects;
    private List<Card> allCardsExecutingEffect;
    private Player playerExecutingEffect = null;
    private Card cardExecutingEffect = null;
    private Card gainedCard = null;

    public ExecutingEffectStateV2(Player currentPlayer, Card gainedCard) {
        super(currentPlayer);
        this.gainedCard = gainedCard;
        playersExecutingEffects = new ArrayList<>(currentPlayer.getPlayers());
        playerExecutingEffect = getNextPlayerExecutingEffect();
        resetAllCardsExecutingEffect(playerExecutingEffect);
    }

    public void processNextEffect(Card gainedCard) {
        cardExecutingEffect = getNextCardExecutingEffect();
        if (cardExecutingEffect == null)
            moveToNextPhase();
    }

    public Card getNextCardExecutingEffect() {
        if (allCardsExecutingEffect != null && !allCardsExecutingEffect.isEmpty() ) {
            return allCardsExecutingEffect.removeFirst();
        } else {
            cardExecutingEffect = null;
            while (cardExecutingEffect == null && playerExecutingEffect != null) {
                playerExecutingEffect = getNextPlayerExecutingEffect();
                if (playerExecutingEffect != null) {
                    resetAllCardsExecutingEffect(playerExecutingEffect);
                    if (allCardsExecutingEffect != null && !allCardsExecutingEffect.isEmpty()) {
                        cardExecutingEffect = allCardsExecutingEffect.removeFirst();
                    }
                }
            }
            return cardExecutingEffect;
        }
    }

    public Player getNextPlayerExecutingEffect() {
        if (playersExecutingEffects != null && !playersExecutingEffects.isEmpty() ) {
            return playersExecutingEffects.removeFirst();
        }
        return null;
    }

    public void resetAllCardsExecutingEffect(Player playerExecutingEffect) {
        this.allCardsExecutingEffect = new ArrayList<>(playerExecutingEffect.getInPlay());;
    }

}
