package fr.umontpellier.iut.dominion.cards;

import fr.umontpellier.iut.dominion.CardType;
import fr.umontpellier.iut.dominion.Player;

/**
 * Les cartes Malédiction
 */
public abstract class CurseCard extends Card {

    /**
     * Constructeur
     */
    public CurseCard(String name, int cost) {
        super(name, cost);
        addType(CardType.CURSE);
    }

    @Override
    public void play(Player p) {
    }

}