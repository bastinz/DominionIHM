package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

/*
public class ExecutingEffectState extends PlayerState {

    public ExecutingEffectState(Player currentPlayer) {
        super(currentPlayer);
    }

*/
/*    @Override
    public void moveToNextExecutingEffect(Card gainedCard) {
        Player playerExecutingEffect = currentPlayer.getNextPlayerExecutingEffect();
        if (playerExecutingEffect != null) {
            currentPlayer.resetAllCardsExecutingEffect(playerExecutingEffect);
            Card cardExecutingEffect = currentPlayer.getNextCardExecutingEffect();
            if (cardExecutingEffect != null) {
                cardExecutingEffect.onPlayerGainCard(currentPlayer, gainedCard, playerExecutingEffect);

//                currentPlayer.setCurrentState(new ExecutingGainedCardEffects(currentPlayer, playerExecutingEffect, gainedCard, cardExecutingEffect));
            } else {
                // révéler et activer une carte réaction
                if (gainedCard.hasType(TREASURE))
                    currentPlayer.setCurrentState(new ReactionPhase(currentPlayer, playerExecutingEffect, gainedCard));
                else
                    moveToNextExecutingEffect(gainedCard);
            }
        } else
            moveToNextPhase();
    }*//*


}*/
