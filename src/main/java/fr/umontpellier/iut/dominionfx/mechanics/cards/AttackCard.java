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

    public abstract void attack(Player p, Player target);

    public void afterAttack(Player p) {
    }

    @Override
    public void play(Player p) {
        CompletableFuture<Void> actionFuture = new CompletableFuture<>();
        action(p, actionFuture);
        actionFuture.thenRun(() -> attack(p));
    }

    private void attack(Player p) {
        for (Player target : p.getOtherPlayers())
            if (!target.isProtectedFromAttack())
                attack(p, target);
        afterAttack(p);
        p.getCurrentState().moveToNextPhase();
    }
}

