package fr.umontpellier.iut.dominion.cards;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;

/**
 * Les cartes Victoire
 */
public abstract class VictoryCard extends Card {

    /**
     * Constructeur
     */
    public VictoryCard(String name, int cost) {
        super(name, cost);
        addType(CardType.VICTORY);
    }

    @Override
    public void play(Player p) {
    }
}