package fr.umontpellier.iut.dominionJavaFX.dominion.cards.seaside;

import fr.umontpellier.iut.dominionJavaFX.dominion.Player;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.ActionCard;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;
import fr.umontpellier.iut.dominionJavaFX.dominion.gui.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Carte aux trésors (Treasure Map)
 * <p>
 * Écartez ceci et une Carte aux trésors de votre main. Si vous avez écarté
 * deux Cartes aux trésors, recevez 4 Ors (Gold) sur votre pioche.
 */
public class TreasureMap extends ActionCard {

    public TreasureMap() {
        super("Treasure Map", 4);
    }

    @Override
    public void play(Player p) {
        List<Card> trashedCards = new ArrayList<>();
        trashedCards.add(this);
        for (Card c: p.getCardsInHand()) {
            if (c.hasName("Treasure Map")) {
                trashedCards.add(c);
                break;
            }
        }
        p.log("trashes %s".formatted(Utils.toLog(trashedCards)));
        p.moveToTrash(trashedCards);
        if (trashedCards.size() == 2) {
            List<Card> gainedGold = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                Card gold = p.getCardFromSupply("Gold");
                if (gold == null) {
                    break;
                }
                gold.moveTo(gainedGold);
            }
            if (!gainedGold.isEmpty()) {
                p.log("gains %s on deck".formatted(Utils.toLog(gainedGold)));
                for (Card c : new ArrayList<>(gainedGold)) {
                    p.gainToDraw(c);
                }
            }
        }
    }
}
