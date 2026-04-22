package fr.umontpellier.iut.dominionfx.mechanics.cards.seaside;

import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.AttackCard;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;

import java.util.concurrent.CompletableFuture;

/**
 * Carte Sorcière de mer (Sea Hag)
 * <p>
 * Tous vos adversaires défaussent la carte du haut de leur pioche, puis 
 * reçoivent une Malédiction (Curse) sur leur pioche.
 */
public class SeaHag extends AttackCard {

    public SeaHag() {
        super("Sea Hag", 4);
    }

    @Override
    public void action(Player p, CompletableFuture<Void> f) {
        // rien à faire pour le joueur qui joue la carte
    }
    
    @Override
    public void attack(Player p, Player target) {
        Card c = target.getCardFromDeck();
        if (c != null) {
            target.log("%s discards %s".formatted(target.toLog(), c.toLog()));
            target.moveToDiscard(c);
        }
        Card curse = target.getCardFromSupply("Curse");
        if (curse != null) {
            target.log("%s gains %s on deck".formatted(target.toLog(), curse.toLog()));
            target.gainToDraw(curse);
        }
    }
}
