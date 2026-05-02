package fr.umontpellier.iut.dominionfx.mechanics.playerstate;

import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.concurrent.CompletableFuture;

public abstract class PlayerState {
    protected CompletableFuture<Void> future;
    protected final Player currentPlayer;

    public PlayerState(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
        start();
    }

    public CompletableFuture<Void> start() {
        this.future = new CompletableFuture<>();
        onEnter();
        return future;
    }

    protected void onEnter(){}

    public void complete() {
        future.complete(null);
    }

    public CompletableFuture<Void> getCompletionFuture() {
        return future;
    }

    public void moveToNextPhase() {
        if (currentPlayer.areActionsCompleted()) {
            if (currentPlayer.areBuysCompleted()) {
                endOfCurrentPlayersTurn();
            }
            else
                currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
        } else
            currentPlayer.setCurrentState(new StartTurnState(currentPlayer));
    }

    public void skip() {
    }

    public void playTreasuresWasChosen() {
        currentPlayer.endActionPhase();
        currentPlayer.setCurrentState(new TreasurePhase(currentPlayer));
//        currentPlayer.getCurrentState().playTreasuresWasChosen();
        currentPlayer.playTreasures();
    }

    public void answer(String choice) {
    }

    public void cardInHandWasChosen(String cardName) {
    }

    public void supplyCardWasChosen(String cardName) {
    }

    public void temporaryCardWasChosen(String cardName) {
    }

    public void addToMat() {
    }

    public void takeFromMat() {
    }

/*    public void moveToNextExecutingEffect(Card gainedCard) {
        Player playerExecutingEffect = currentPlayer.getNextPlayerExecutingEffect();
        if (playerExecutingEffect != null) {
            System.out.println("SOOO player suivant " + playerExecutingEffect.getName());
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
        } else {
            System.out.println("SOOO fin players");
            System.out.println("SOOO " + currentPlayer.getCurrentState());
            moveToNextPhase();
        }
    }*/

/*    public void moveToNextExecutingEffect(Card gainedCard) {
        Card cardExecutingEffect = currentPlayer.getNextCardExecutingEffect();
        if (cardExecutingEffect == null) {
            Player playerExecutingEffect = currentPlayer.getNextPlayerExecutingEffect();
            if (playerExecutingEffect == null) {
                moveToNextPhase();
            } else {
                currentPlayer.setPlayerExecutingEffect(playerExecutingEffect);
                currentPlayer.resetAllCardsExecutingEffect(playerExecutingEffect);
                moveToNextExecutingEffect(gainedCard);
            }
        } else {
            cardExecutingEffect.onPlayerGainCard(currentPlayer, gainedCard, currentPlayer.getPlayerExecutingEffect());
        }
    }*/


/*        if (playerExecutingEffect != null) {
            System.out.println("SOOO player suivant " + playerExecutingEffect.getName());
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
        } else {
            System.out.println("SOOO fin players");
            System.out.println("SOOO " + currentPlayer.getCurrentState());
            moveToNextPhase();
        }
    }*/

    public void endOfCurrentPlayersTurn() {
        // on vérifie si fin de partie ??
        getGame().moveToNextPlayerState(); // fait le cleanUp et startTurn
    }

    protected Game getGame() {
        return currentPlayer.getGame();
    }
}