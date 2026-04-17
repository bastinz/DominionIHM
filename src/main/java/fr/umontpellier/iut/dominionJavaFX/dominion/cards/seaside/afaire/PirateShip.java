package fr.umontpellier.iut.dominionJavaFX.dominion.cards.seaside.afaire;

import fr.umontpellier.iut.dominionJavaFX.dominion.Button;
import fr.umontpellier.iut.dominionJavaFX.dominion.CardType;
import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.AttackCard;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;

import java.util.Arrays;
import java.util.List;

/**
 * Carte Bateau pirate (Pirate Ship)
 * <p>
 * Choisissez : +1 Pièce par jeton Pièce sur votre plateau Bateau pirate ;
 * ou tous vos adversaires dévoilent les 2 premières cartes de leur pioche,
 * écartent un Trésor (Treasure) dévoilé de votre choix et défaussent le reste,
 * et si au moins un Trésor a été écarté, placez un jeton Pièce sur votre
 * plateau Bateau pirate.
 */
public class PirateShip extends AttackCard {
    private boolean isAttacking = false;
    private boolean didTrashTreasure = false;

    public PirateShip() {
        super("Pirate Ship", 4);
    }

    @Override
    public void action(Player p) {
        didTrashTreasure = false;
        String choice = p.chooseStringFromButtons(
                "%s: Choose one".formatted(this),
                Arrays.asList(new Button("+%d coins".formatted(p.getPirateShipCounter()), "coins"), new Button("Attack", "attack")),
                false);
        if (choice.equals("coins")) {
            p.log("for +%d coins".formatted(p.getPirateShipCounter()));
            p.incrementMoney(p.getPirateShipCounter());
            isAttacking = false;
        } else {
            p.log("chooses to attack");
            isAttacking = true;
        }
    }

    @Override
    public void attack(Player p, Player target) {
        if (isAttacking) {
            List<Card> revealedCards = target.drawCards(2);
            if (revealedCards.stream().anyMatch(c -> c.hasType(CardType.TREASURE))) {
                // si au moins un trésor, choisir un trésor à écarter
                Card selectedTreasure = p.chooseCardFromButtons(
                        "%s: Choose a treasure to trash".formatted(this),
                        revealedCards.stream().filter(c -> c.hasType(CardType.TREASURE)).toList(),
                        false);
                target.log("%s trashes %s".formatted(target.toLog(), selectedTreasure));
                target.moveToTrash(selectedTreasure);
                didTrashTreasure = true;
            }
            target.moveToDiscard(revealedCards);
        }
    }

    @Override
    public void afterAttack(Player p) {
        if (didTrashTreasure) {
            p.incrementPirateShipCounter();
            p.log("Pirate Ship mat has %d tokens".formatted(this, p.getPirateShipCounter()));
        }
    }
}
