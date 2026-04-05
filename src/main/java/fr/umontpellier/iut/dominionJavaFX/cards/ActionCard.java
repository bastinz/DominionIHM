package fr.umontpellier.iut.dominionJavaFX.cards;

import fr.umontpellier.iut.dominionJavaFX.CardType;

/**
 * Les cartes Action
 */
public abstract class ActionCard extends Card {

    /**
     * Constructeur
     */
    public ActionCard(String name, int cost) {
        super(name, cost);
        addType(CardType.ACTION);
    }
}