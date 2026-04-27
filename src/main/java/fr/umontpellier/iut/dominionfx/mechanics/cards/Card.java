package fr.umontpellier.iut.dominionfx.mechanics.cards;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Représentation des cartes du jeu Dominion
 */
public abstract class Card {
    /**
     * Le nom de la carte
     */
    private final String name;

    /**
     * Le coût de la carte à l'achat
     */
    private final int cost;

    private Set<CardType> types;

    private List<Card> location;

    private BooleanProperty hasDurationEffect = new SimpleBooleanProperty(false);

    /**
     * Constructeur simple
     *
     * @param name le nom de la carte
     * @param cost le coût de la carte
     */
    public Card(String name, int cost) {
        this.name = name;
        this.cost = cost;
        this.types = new HashSet<>();
    }

    /**
     * Getters et setters
     */
    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public boolean hasSameNameAs(Card c) {
        return this.name.equals(c.getName());
    }

    public void addType(CardType type) {
        this.types.add(type);
    }

    /**
     * @return {@code true} si la carte est de type {@code type}, {@code false}
     *         sinon
     */
    public boolean hasType(CardType type) {
        return this.types.contains(type);
    }

    public void moveTo(List<Card> newLocation) {
        if (location != null) {
            location.remove(this);
        }
        location = newLocation;
        newLocation.add(this);
    }


    public boolean getHasDurationEffect() {
        return hasDurationEffect.getValue();
    }

    public void setHasDurationEffect(boolean hasDurationEffect) {
        this.hasDurationEffect.setValue(hasDurationEffect);
    }

    public BooleanProperty hasDurationEffectProperty() {
        return hasDurationEffect;
    }

    /**
     * Renvoie une représentation de la carte sous forme de chaîne de caractères
     * (ici la fonction renvoie le nom de la carte)
     */
    @Override
    public String toString() {
        return name;
    }

    public String toLog() {
        return "<span class=\"card-name\">" + name + "</span>";
    }

    /**
     * Exécute l'effet de la carte, jouée par le joueur {@code p}
     *
     * @param p joueur qui exécute l'effet de la carte
     *
     *          L'action de cette méthode dépend de la classe de la carte.
     */
    public abstract void play(Player p);

    public void onCleanup(Player p) {
        // Remarque: le fait d'appeler getHasDurationEffect() au lieu de lire
        // directement hasDurationEffect permet de redéfinir la méthode dans
        // les sous-classes si nécessaire (cf. Blockade)
        if (!getHasDurationEffect()) {
            p.moveToDiscard(this);
        }
    }

    public void atStartOfTurn(Player p) {
    }

    public void reaction(Player p) {
    }

    public boolean isProtectionFromAttack() {
        return false;
    }

    /**
     * Cette méthode est appelée sur toutes les cartes en jeu de tous les joueurs
     * quand un joueur joue une carte
     * 
     * @param p          le joueur qui joue la carte
     * @param playedCard la carte qui est jouée
     * @param owner      le joueur qui possède la carte en jeu sur laquelle la
     *                   méthode est appelée
     */
    public void onPlayerPlayCard(Player p, Card playedCard, Player owner) {
    }

    /**
     * Cette méthode est appelée sur toutes les cartes en jeu de tous les joueurs
     * quand un joueur reçoit une carte (gain).
     * 
     * @param p          le joueur qui reçoit la carte
     * @param gainedCard la carte qui est reçue
     * @param owner      le joueur qui possède la carte en jeu sur laquelle la
     *                   méthode est appelée
     */
    public void onPlayerGainCard(Player p, Card gainedCard, Player owner) {
    }

    public boolean canReactToPlayerGainCard(Player p, Card gainedCard, Player owner) {
        return false;
    }

    public void reactToPlayerGainCard(Player p, Card gainedCard, Player owner) {
    }

    /**
     * Renvoie la valeur de la carte en points de victoire (c'est cette méthode
     * qui est appelée sur toutes les cartes du deck d'un joueur pour
     * déterminer le score du joueur en fin de partie)
     * <p>
     * Toutes les cartes qui ne sont pas de type Victoire ont une valeur de
     * 0 (la méthode devra donc être redéfinie pour les cartes Victoire)
     */
    public int getVictoryValue() {
        return 0;
    }

    // pour les tests
    public void moveToTop(List<Card> newLocation) {
        if (location != null) {
            location.remove(this);
        }
        location = newLocation;
        newLocation.addLast(this);
    }
}