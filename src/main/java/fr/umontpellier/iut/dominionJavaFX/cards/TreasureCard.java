package fr.umontpellier.iut.dominionJavaFX.cards;

import fr.umontpellier.iut.dominionJavaFX.CardType;

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