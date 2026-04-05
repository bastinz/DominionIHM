package fr.umontpellier.iut.dominion.cards;

import fr.umontpellier.iut.dominion.CardType;

/**
 * Les cartes Trésor
 */
public abstract class TreasureCard extends Card {

    /**
     * Constructeur
     */
    public TreasureCard(String name, int cost) {
        super(name, cost);
        addType(CardType.TREASURE);
    }
}