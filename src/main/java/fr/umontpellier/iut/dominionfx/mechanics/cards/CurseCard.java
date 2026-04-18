package fr.umontpellier.iut.dominionfx.mechanics.cards;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;

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