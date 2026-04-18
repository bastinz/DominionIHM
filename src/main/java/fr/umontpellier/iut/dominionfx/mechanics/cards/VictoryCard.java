package fr.umontpellier.iut.dominionfx.mechanics.cards;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;

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