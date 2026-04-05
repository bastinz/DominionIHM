package fr.umontpellier.iut.dominionJavaFX.cards;

import fr.umontpellier.iut.dominionJavaFX.CardType;
import fr.umontpellier.iut.dominionJavaFX.Player;

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