package fr.umontpellier.iut.dominionfx.mechanics.cards;

import fr.umontpellier.iut.dominionfx.mechanics.Player;

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

    public abstract void action(Player p);

    public abstract void attack(Player p, Player target);

    public void afterAttack(Player p) {
    }

    @Override
    public void play(Player p) {
        action(p);
        for (Player target : p.getOtherPlayers())
            if (!target.isProtectedFromAttack())
                attack(p, target);
        afterAttack(p);
    }
}