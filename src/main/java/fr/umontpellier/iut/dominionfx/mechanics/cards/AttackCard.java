package fr.umontpellier.iut.dominionfx.mechanics.cards;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

import java.util.concurrent.CompletableFuture;

/**
 * Les cartes Attaque
 * Rmq : les cartes Attaque sont toutes des cartes Action
 */
public abstract class AttackCard extends ActionCard {
    /**
     * Constructeur
     */
    public AttackCard(String name, int cost) {
        super(name, cost);
    }

    public abstract void action(Player p, CompletableFuture<Void> f);

    public abstract CompletableFuture<Void> attack(Player p, Player target);

    public void afterAttack(Player p) {
    }

    @Override
    public void play(Player p) {
        CompletableFuture<Void> actionFuture = new CompletableFuture<>();
        action(p, actionFuture);
        actionFuture
                .thenCompose(v -> attackAll(p))
                .thenRun(() -> afterAttack(p))
                .thenRun(() -> p.getCurrentState().moveToNextPhase());
    }

    private CompletableFuture<Void> attackAll(Player p) {
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        for (Player target : p.getOtherPlayers()) {
            if (!target.isProtectedFromAttack()) {
                future = future.thenCompose(v -> attack(p, target));
            }
        }
        return future;
    }

}