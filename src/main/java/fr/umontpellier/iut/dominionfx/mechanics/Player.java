package fr.umontpellier.iut.dominionfx.mechanics;

import fr.umontpellier.iut.dominionfx.IPlayer;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.gui.Utils;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.ActionPhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Un joueur de Dominion
 */
public class Player implements IPlayer {
    /**
     * Nom du joueur
     */
    private final String name;

    /**
     * Nombre d'actions disponibles
     */
    private final IntegerProperty numberOfActions;

    /**
     * Nombre d'achats disponibles
     */
    private final IntegerProperty numberOfBuys;

    /**
     * Nombre de pièces disponibles pour acheter des cartes
     */
    private final IntegerProperty money;

    /**
     * Indique si un Argent ou un Or a été joué ce tour ou non
     * (pour la carte Corsair)
     */
    private int nbSilverOrGoldPlayed;

    /**
     * La partie en cours
     */
    private final Game game;

    /**
     * Liste des cartes dans la main du joueur
     */
    private final ObservableList<Card> hand;

    /**
     * Liste des cartes dans la défausse du joueur
     */
    private final ObservableList<Card> discard;

    /**
     * Liste des cartes dans la pioche du joueur (on considère que le dessus de
     * la pioche est à la fin de la liste)
     */
    private final ObservableList<Card> draw;

    /**
     * Listes des cartes qui ont été jouées pendant le tour courant
     */
    private final ObservableList<Card> inPlay;

    private final ObservableList<Card> cardsSetAside;
    /**
     * Liste des cartes mises de côté sur le plateau île (island mat) du joueur
     */
    private final ObservableList<Card> islandMat;

    private final ObservableList<Card> nativeVillageMat;

    private final ObservableList<Card> cardsGainedThisTurn;

    private final ObservableList<Card> cardsBoughtThisTurn;

    private int nbCardsToDrawAtCleanup = 5;

    /**
     * Nombre de jetons
     */
    private int pirateShipCounter;

    /**
     * Constructeur
     * <p>
     * Initialise les différentes piles de cartes du joueur, place 3 cartes
     * Estate et 7 cartes Copper dans la défausse du joueur puis fait piocher 5
     * cartes en main au joueur.
     * <p>
     * Indications : On peut utiliser la méthode {@code endTurn()} pour
     * préparer la main du joueur après avoir placé les cartes dans la
     * défausse.
     * Les cartes Copper et Estate doivent être instanciées
     * directement dans cette méthode.
     * 
     * @param name: le nom du joueur
     * @param game: le jeu en cours
     *
     */
    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
        money = new SimpleIntegerProperty(0);
        numberOfActions = new SimpleIntegerProperty(0);
        numberOfBuys = new SimpleIntegerProperty(0);
        // Prépare les listes de cartes
        hand = FXCollections.observableArrayList();
        discard = FXCollections.observableArrayList();
        draw = FXCollections.observableArrayList();
        inPlay = FXCollections.observableArrayList();
        cardsSetAside = FXCollections.observableArrayList();
        islandMat = FXCollections.observableArrayList();
        nativeVillageMat = FXCollections.observableArrayList();
        cardsGainedThisTurn = FXCollections.observableArrayList();
        cardsBoughtThisTurn = FXCollections.observableArrayList();

        // Ajoute 3 Estate et 7 Copper (pris dans la réserve du jeu) dans la
        // défausse du joueur
        for (int i = 0; i < 3; i++)
            moveToDiscard(getCardFromSupply("Estate"));
        for (int i = 0; i < 7; i++)
            moveToDiscard(getCardFromSupply("Copper"));

        // Mélange la défausse, construit la pioche et pioche 5 cartes en main
        Collections.shuffle(discard);
        while (!discard.isEmpty()) {
            discard.getLast().moveTo(draw);
        }
        for (int i = 0; i < 5; i++) {
            draw.getLast().moveTo(hand);
        }
    }

    /**
     * Getters et setters
     */
    public String getName() {
        return name;
    }

    public int getIndex() {
        return game.getPlayerIndex(this);
    }

    public int getMoney() {
        return money.getValue();
    }

    public IntegerProperty moneyProperty() {
        return money;
    }

    public int getNumberOfActions() {
        return numberOfActions.getValue();
    }

    public int getNumberOfBuys() {
        return numberOfBuys.getValue();
    }

    public int getPirateShipCounter() {
        return pirateShipCounter;
    }

    // Uniquement pour la carte Corsair
    public int getNbSilverOrGoldPlayed() {
        return nbSilverOrGoldPlayed;
    }

    public Game getGame() {
        return game;
    }

    /**
     * Renvoie une liste des cartes que le joueur a en main.
     * <p>
     * La liste renvoyée est une copie de la liste {@code hand} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsInHand() {
        return new ArrayList<>(hand);
    }

    /**
     * Renvoie une liste des cartes que le joueur a dans sa défausse.
     * <p>
     * La liste renvoyée est une copie de la liste {@code discard} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     */
    public List<Card> getCardsInDiscard() {
        return new ArrayList<>(discard);
    }

    /**
     * Renvoie une liste des cartes que le joueur a dans sa pioche.
     * <p>
     * La liste renvoyée est une copie de la liste {@code draw} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     * 
     */
    public List<Card> getCardsInDraw() {
        return new ArrayList<>(draw);
    }

    /**
     * Renvoie une liste des cartes que le joueur a dans sa pioche.
     * <p>
     * La liste renvoyée est une copie de la liste {@code inPlay} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     * 
     */
    public List<Card> getCardsInPlay() {
        return new ArrayList<>(inPlay);
    }

    /**
     * Renvoie une liste des cartes que le joueur a dans sa pioche.
     * <p>
     * La liste renvoyée est une copie de la liste {@code cardsSetAside} du joueur.
     * Elle contient les mêmes cartes mais une modification de la liste renvoyée ne
     * modifie pas la liste originale.
     * 
     */
    public List<Card> getCardsSetAside() {
        return new ArrayList<>(cardsSetAside);
    }

    /**
     * Renvoie une liste des cartes que le joueur a dans sa pioche.
     * <p>
     * La liste renvoyée est une copie de la liste {@code nativeVillageMat} du
     * joueur. Elle contient les mêmes cartes mais une modification de la liste
     * renvoyée ne modifie pas la liste originale.
     * 
     */
    public List<Card> getCardsOnNativeVillageMat() {
        return new ArrayList<>(nativeVillageMat);
    }

    public List<Card> getCardsGainedThisTurn() {
        return new ArrayList<>(cardsGainedThisTurn);
    }

    public List<Card> getCardsBoughtThisTurn() {
        return new ArrayList<>(cardsBoughtThisTurn);
    }

    /**
     * Renvoie une liste de toutes les cartes possédées par le joueur
     */
    public List<Card> getAllOwnedCards() {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(hand);
        allCards.addAll(discard);
        allCards.addAll(draw);
        allCards.addAll(inPlay);
        allCards.addAll(cardsSetAside);
        allCards.addAll(islandMat);
        allCards.addAll(nativeVillageMat);
        return allCards;
    }

    /**
     * Renvoie le nombre total de points de victoire du joueur
     * <p>
     * Ce total est calculé en ajoutant les valeurs individuelles de toutes les
     * cartes possédées par le joueur (en utilisant la méthode
     * {@code getVictoryValue()}) des cartes
     */
    public int getVictoryPoints() {
        int points = 0;
        for (Card c : getAllOwnedCards())
            points += c.getVictoryValue();
        return points;
    }

    /**
     * Renvoie une liste des autres joueurs de la partie.
     * <p>
     * Les adversaires sont listés dans l'ordre de jeu, c'est-à-dire que le
     * premier de la liste est celui qui joue immédiatement après le joueur,
     * puis le suivant, et ainsi de suite jusqu'au joueur qui joue immédiatement
     * avant le joueur.
     * <p>
     * Rmq: Cette méthode fait appel à la méthode {@code getOtherPlayers(Player p)}
     * de la classe {@code Game}.
     */
    public List<Player> getOtherPlayers() {
        return game.otherPlayers(this);
    }

    /**
     * @return une liste de tous les joueurs de la partie, dans le sens de rotation
     *          en commençant par le joueur lui-même.
     */
    private List<Player> getPlayers() {
        List<Player> players = game.otherPlayers(this);
        players.addFirst(this);
        return players;
    }

    public boolean isProtectedFromAttack() {
        for (Card c : inPlay) {
            if (c.isProtectionFromAttack()) {
                return true;
            }
        }
        // TODO: éventuellement ajouter la possibilité de révéler Moat
        return false;
    }

    /**
     * Incrémente le nombre d'actions du joueur ({@code numberOfActions})
     *
     * @param n nombre d'actions à ajouter (ce nombre peut être négatif si
     *          l'on diminuer le nombre d'actions)
     */
    public void incrementActions(int n) {
        numberOfActions.setValue(numberOfActions.getValue() + n);
    }

    /**
     * Incrémente le nombre de pièces du joueur ({@code money})
     *
     * @param n nombre de pièces à ajouter (ce nombre peut être négatif si l'on
     *          souhaite diminuer le nombre de pièces)
     */
    public void incrementMoney(int n) {
        money.setValue(money.getValue() + n);
    }

    /**
     * Incrémente le nombre d'achats disponibles du joueur ({@code numberOfBuys})
     *
     * @param n nombre d'achats à ajouter (ce nombre peut être négatif si l'on
     *          souhaite diminuer le nombre d'achats)
     */
    public void incrementBuys(int n) {
        numberOfBuys.setValue(numberOfBuys.getValue() + n);
    }

    public void incrementPirateShipCounter() {
        pirateShipCounter += 1;
    }

    public void setNbCardsToDrawAtCleanup(int n) {
        nbCardsToDrawAtCleanup = n;
    }

    // Uniquement pour la carte Corsair
    public void incrementNbSilverOrGoldPlayed() {
        this.nbSilverOrGoldPlayed += 1;
    }

    public void moveToDiscard(Card c) {
        c.moveTo(discard);
    }

    public void moveToDiscard(List<Card> cards) {
        for (Card c : new ArrayList<>(cards)) {
            moveToDiscard(c);
        }
    }

    /**
     * Déplace une carte dans la main du joueur.
     *
     * @param c la carte à déplacer
     */
    public void moveToDraw(Card c) {
        c.moveTo(draw);
    }

    public void moveToDraw(List<Card> cards) {
        for (Card c : new ArrayList<>(cards)) {
            moveToDraw(c);
        }
    }

    public void moveToHand(Card c) {
        c.moveTo(hand);
    }

    public void moveToHand(List<Card> cards) {
        for (Card c : new ArrayList<>(cards)) {
            moveToHand(c);
        }
    }

    public void moveToInPlay(Card c) {
        c.moveTo(inPlay);
    }

    public void moveToSetAside(Card c) {
        c.moveTo(cardsSetAside);
    }

    public void moveToTrash(Card c) {
        game.moveCardToTrash(c);
    }

    public void moveToTrash(List<Card> cards) {
        for (Card c : new ArrayList<>(cards)) {
            moveToTrash(c);
        }
    }

    public void moveToIslandMat(Card c) {
        c.moveTo(islandMat);
    }

    public void moveToNativeVillageMat(Card c) {
        c.moveTo(nativeVillageMat);
    }

    private void shuffleDiscardIntoDrawIfEmpty() {
        if (draw.isEmpty()) {
            Collections.shuffle(discard);
            moveToDraw(discard);
        }
    }

    /**
     * Renvoie la carte qui se trouve au sommet de la pioche du joueur.
     * <p>
     * Si la pioche du joueur est vide, on commence par mélanger la défausse
     * et transférer toutes les cartes de la défausse dans la pioche.
     * On renvoie ensuite la première carte de la pioche si elle n'est
     * pas vide (sinon la méthode renvoie {@code null}).
     * <p>
     * Remarque : la carte n'est pas retirée de la pioche.
     *
     * @return la carte piochée, ou {@code null} si aucune carte disponible
     */
    public Card getCardFromDeck() {
        shuffleDiscardIntoDrawIfEmpty();
        if (!draw.isEmpty()) {
            return draw.getLast();
        }
        return null;
    }

    /**
     * Pioche n cartes dans la pioche du joueur. Les cartes sont déplacées de la
     * pioche vers une nouvelle liste qui est ensuite renvoyée.
     * 
     * @param n le nombre de cartes à piocher
     * @return une liste de cartes piochées (les cartes sont déplacées dans cette
     *         liste)
     */
    public List<Card> drawCards(int n) {
        List<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Card c = getCardFromDeck();
            if (c == null) {
                break;
            }
            c.moveTo(drawnCards);
        }
        return drawnCards;
    }

    // Pour Pearl Diver
    public Card getBottomCardOfDeck() {
        shuffleDiscardIntoDrawIfEmpty();
        if (!draw.isEmpty()) {
            return draw.getFirst();
        }
        return null;
    }

    /**
     * @param cardName nom de la carte à obtenir dans la réserve
     * @return la carte du sommet de la pile de réserve correspondant au nom
     *         passé en argument, ou {@code null} si la pile de réserve est vide ou
     *         si le nom ne correspond à aucune pile de la réserve.
     */
    public Card getCardFromSupply(String cardName) {
        return game.getCardFromSupply(cardName);
    }

    /**
     * Pioche une carte et la place directement dans la main du joueur.
     * <p>
     * Cette méthode fait appel à la méthode {@code drawCard()} pour piocher une
     * carte et la place dans la main du joueur.
     */
    public Card drawToHand() {
        Card c = getCardFromDeck();
        if (c != null)
            moveToHand(c);
        return c;
    }

    public List<Card> drawToHand(int n) {
        List<Card> drawnCards = drawCards(n);
        List<Card> cardsToReturn = new ArrayList<>(drawnCards);
        moveToHand(drawnCards);
        return cardsToReturn;
    }

    /**
     * Renvoie une représentation de l'état du joueur sous forme d'une chaîne
     * de caractères.
     * <p>
     * Cette représentation comporte
     * - le nom du joueur
     * - le nombre d'actions, de pièces et d'achats du joueur
     * - le nombre de cartes dans la pioche et dans la défausse du joueur
     * - la liste des cartes en jeu du joueur
     * - la liste des cartes dans la main du joueur
     * <p>
     * On pourrait par exemple avoir l'affichage suivant:
     * <p>
     * -- Toto --
     * Actions: 2 Money: 4 Buys: 1 Draw: 7 Discard: 3
     * In play: Caravan, Copper, Silver, Copper
     * Hand: Estate, Province
     */
    @Override
    public String toString() {
        String r = String.format("     -- %s --\n", name);
 /*       r += String.format("Actions: %d     Money: %d     Buys: %d     Draw: %d     Discard: %d\n",
                numberOfActions,
                money, numberOfBuys, draw.size(), discard.size());*/
        r += String.format("In play: %s\n", inPlay.toString());
        r += String.format("Hand: %s\n", hand.toString());
        return r;
    }

    public String toLog() {
        return "<span class=\"player-name\">" + name + "</span>";
    }

    /**
     * Méthode utilitaire pour l'interface graphique.
     * À NE PAS MODIFIER.
     */
    public String toJSON() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(String.format("\"name\": \"%s\"", name));
//        joiner.add(String.format("\"actions\": %d", numberOfActions));
//        joiner.add(String.format("\"money\": %d", money));
//        joiner.add(String.format("\"buys\": %d", numberOfBuys));
        joiner.add(String.format("\"draw\": %s", Utils.toJSON(draw)));
        joiner.add(String.format("\"discard\": %s", Utils.toJSON(discard)));
        joiner.add(String.format("\"in_play\": %s", Utils.toJSON(inPlay)));
        joiner.add(String.format("\"hand\": %s", Utils.toJSON(hand)));
        return "{" + joiner + "}";
    }

    /**
     * Joue une carte de la main du joueur.
     * <p>
     * Cette méthode ne vérifie pas que le joueur a le droit de jouer la
     * carte, ni même que la carte se trouve effectivement dans sa main.
     * La carte est déplacée de la main du joueur dans la liste
     * {@code inPlay} et la méthode {@code play(Player p)} de la
     * carte est exécutée.
     * 
     * @param c carte à jouer
     */
    public void playCard(Card c) {
//        System.out.println("SOOO joueur " + getName() + " joue " + c.getName());
        moveToInPlay(c);
        c.play(this);
        // exécuter les effets onPlayerPlayCard de toutes les cartes en jeu des joueurs
        for (Player p : getPlayers()) {
            for (Card cardInPlay : p.inPlay) {
                cardInPlay.onPlayerPlayCard(this, c, p);
            }
        }
    }

    /**
     * Le joueur gagne une carte.
     * <p>
     * Si la carte n'est pas {@code null}, elle est déplacée sur la défausse
     * du joueur.
     *
     * @param gainedCard carte à gagner (éventuellement {@code null})
     */
    public void gainTo(Card gainedCard, List<Card> location) {
        if (gainedCard == null) {
            return;
        }
//        indentLog();
        gainedCard.moveTo(location);
        cardsGainedThisTurn.add(gainedCard);
        // exécuter les effets déclenchés par le gain d'une carte
        // pour chaque joueur (en commençant par le joueur qui a gagné la carte)
        // on exécute tous les effets "on gain" des cartes en jeu du joueur
        // puis on demande au joueur s'il veut utiliser une carte réaction
        for (Player cardOwner : getPlayers()) {
            // exécuter les effets onGain de toutes les cartes en jeu du joueur
            for (Card cardInPlay : new ArrayList<>(cardOwner.inPlay)) {
                cardInPlay.onPlayerGainCard(this, gainedCard, cardOwner);
            }
            // révéler et activer une carte réaction
//            System.out.println("SOOO avant listOfReact " + cardOwner.getName() + " " + cardOwner.getHand());
            List<Card> listOfReactingCards = cardOwner.hand.stream()
                    .filter(c -> c.canReactToPlayerGainCard(this, gainedCard, cardOwner)).toList();
//            game.setTemporaryCards(listOfReactingCards.stream().collect(Collectors.toCollection(FXCollections::observableArrayList)));
            if (!listOfReactingCards.isEmpty()) {
//                System.out.println("SOOO " +cardOwner.getName() + " peut reagir " + listOfReactingCards);
                game.setTemporaryCards(cardOwner.getHand());
                CompletableFuture<Void> chain = new CompletableFuture<>();
                for (Card card : listOfReactingCards) {
                    chain = chain.thenCompose(v -> card.reaction(this, gainedCard, cardOwner));
                }
            }
/*            for (Card reactingCard : listOfReactingCards) {
                System.out.println("SOOO reactingCard: " + reactingCard);
                reactingCard.reaction(this, gainedCard, cardOwner);
            }*/


// Version de Victor
/*            while (cardOwner.hand.stream().anyMatch(c -> c.canReactToPlayerGainCard(this, gainedCard, cardOwner))) {
                Card choice = cardOwner.chooseCardFromHand(
                        "Reaction: you may reveal a Reaction card from your hand",
                        c -> c.canReactToPlayerGainCard(this, gainedCard, cardOwner),
                        true);
                if (choice == null) {
                    break;
                } else {
                    cardOwner.playCard(choice);
                }
            }*/
        }
//        unindentLog();
// Fin Version de Victor
    }

    public void gainToDiscard(Card c) {
        gainTo(c, discard);
    }

    public void gainToHand(Card c) {
        gainTo(c, hand);
    }

    public void gainToDraw(Card c) {
        gainTo(c, draw);
    }

    public void gainToSetAside(Card c) {
        gainTo(c, cardsSetAside);
    }

    public void moveToSupply(Card c) {
        game.moveToSupply(c);
    }

    /**
     * Attend une entrée de la part du joueur (au clavier) et renvoie le choix
     * du joueur.
     * <p>
     * La méthode lit l'entrée clavier jusqu'à ce qu'un choix valide
     * soit entré par l'utilisateur sous la forme d'une chaîne de caractères
     * {@code <TYPE>:<VALEUR>} (par exemple {@code "HAND:Caravan"})
     * correspondant à un élément de {@code choices} ou éventuellement la chaîne
     * vide si l'utilisateur est autorisé à passer. Lorsqu'un choix valide est
     * obtenu, il est renvoyé.
     * <p>
     * Exemple d'utilisation pour demander à un joueur de choisir le nom d'une
     * carte de sa main (ici il n'a pas le droit de passer s'il a au moins une carte
     * en main). Dans l'exemple la méthode renvoie une chaîne de caractères de la
     * forme {@code "HAND:<cardName>"} où {@code <cardName>} est le nom de la carte
     * choisie par le joueur parmi les cartes de sa main.
     * 
     * <pre>
     * {@code
     * List<String> choices = new ArrayList<>();
     * for (Card c : hand) {
     *     choices.add("HAND:" + c.getName());
     * }
     * String choice = p.choose("Choose a card", choices, new ArrayList<>(), false);
     * }
     * </pre>
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param choices     une liste de {@code String} correspondant aux
     *                    choix valides attendus du joueur.
     * @param buttons     une liste de boutons à afficher à l'écran. Chaque bouton
     *                    correspond à une option de choix qui sera ajoutée à la
     *                    liste des choix valides ({@code "BUTTON:<value>"}).
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la
     *                    chaîne de caractères vide {@code ""} qui signifie qu'il
     *                    désire passer. Remarque : si aucun choix valide n'est
     *                    fourni (la liste {@code choices} est vide), le joueur est
     *                    automatiquement autorisé à passer même si {@code canPass}
     *                    est faux.
     * @return l'objet {@code String} correspondant au choix effectué par
     *         l'utilisateur (un élément de {@code choices} ou une chaîne de la
     *         forme {@code "BUTTON:<value>"} correspondant à un bouton de
     *         {@code buttons} ou éventuellement {@code ""}, si l'utilisateur a
     *         choisi de passer.
     */
    public String choose(String instruction, List<String> choices, List<Button> buttons, boolean canPass) {
        // Ajout des options correspondant aux boutons
        for (Button b : buttons) {
            choices.add("BUTTON:" + b.value());
        }

        // Si aucun choix disponible, le joueur est autorisé à passer
        if (choices.isEmpty()) {
            canPass = true;
        }
        // Si le joueur peut passer, on ajoute l'option ""
        if (canPass) {
            choices.add("");
        }
        // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
        while (true) {
            game.prompt(instruction, choices, buttons, getIndex());
            String input = game.readLine();
            if (choices.contains(input)) {
                return input;
            }
        }
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * <p>
     * Dans cette méthode, la liste des choix est donnée sous la forme d'un prédicat
     * permettant de filtrer les cartes de la main du joueur. Le résultat renvoyé
     * est la carte choisie ou {@code null} si le joueur a choisi de passer.
     * <p>
     * La méthode commence par construire une liste de tous les noms des cartes
     * dans {@code hand} qui vérifient le prédicat, puis appelle la méthode
     * {@code choose} pour faire choisir un nom parmi cette liste à l'utilisateur.
     * <p>
     * Exemple d'utilisation pour faire choisir le nom d'une carte Action de sa
     * main à un joueur (dans cet exemple le joueur n'a pas le droit de passer
     * s'il a au moins une carte Action en main, mais la méthode peut quand
     * même renvoyer {@code null} s'il n'a aucune carte Action en main) :
     * 
     * <pre>
     * Card choice = p.chooseCardFromHand(
     *         "Choose an Action card",
     *         c -> c.hasType(CardType.ACTION),
     *         false);
     * </pre>
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param filter      prédicat permettant de filtrer les cartes de la main
     *                    du joueur. Seules les cartes pour lesquelles le prédicat
     *                    renvoie {@code true} seront considérées comme choix
     *                    valides.
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la carte choisie par le joueur ou {@code null} si le joueur a choisi
     *         de passer ou s'il n'avait aucune carte valide dans sa main.
     */
    public Card chooseCardFromHand(String instruction, Predicate<Card> filter, boolean canPass) {
        // ajout des options correspondant aux cartes de la liste
        List<String> choices = hand.stream().filter(filter).map(c -> "HAND:" + c.getName())
                .collect(Collectors.toList());
        String choice = choose(instruction, choices, new ArrayList<>(), canPass);
        if (choice.startsWith("HAND:")) {
            return hand.stream()
                    .filter(c -> c.hasName(choice.split(":")[1]))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    /**
     * Version de la méthode {@code chooseCardFromHand} sans prédicat. Toutes les
     * cartes de la main du joueur sont considérées comme choix valides.
     */
    public Card chooseCardFromHand(String instruction, boolean canPass) {
        return chooseCardFromHand(instruction, c -> true, canPass);
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * <p>
     * Cette méthode est similaire à {@code chooseCardFromHand} mais elle fait
     * choisir une carte parmi les cartes disponibles dans la réserve du jeu
     * (uniquement les piles de réserve contenant au moins une carte).
     * <p>
     * Exemple d'utilisation pour faire choisir une carte sur le dessus d'une pile
     * de réserve qui coûte 4 pièces ou moins (dans cet exemple le joueur est
     * autorisé à passer s'il ne veut pas choisir de carte) :
     * 
     * <pre>
     * Card choice = p.chooseCardFromSupply(
     *         "Choose a card costing up to 4",
     *         c -> c.getCost() <= 4,
     *         true);
     * </pre>
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param filter      prédicat permettant de filtrer les cartes disponibles dans
     *                    la réserve du jeu. Seules les cartes pour lesquelles le
     *                    prédicat renvoie {@code true} seront considérées comme
     *                    choix valides.
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la carte du dessus de la pile de réserve choisie par le joueur ou
     *         {@code null} si le joueur a choisi de passer ou s'il n'avait aucune
     *         carte valide dans sa main.
     */
    public Card chooseCardFromSupply(String instruction, Predicate<Card> filter, boolean canPass) {
        List<String> choices = game.getAvailableSupplyCards().stream()
                .filter(filter)
                .map(c -> "SUPPLY:" + c.getName())
                .collect(Collectors.toList());

        String choice = choose(instruction, choices, new ArrayList<>(), canPass);
        if (choice.startsWith("SUPPLY:")) {
            return getCardFromSupply(choice.split(":")[1]);
        }
        return null;
    }

    /**
     * Demande au joueur de choisir une carte parmi une liste passée en argument.
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param cards       liste des cartes parmi lesquelles le joueur doit choisir.
     *                    Pour chaque carte de la liste, un bouton portant le nom de
     *                    la carte est affiché à l'écran.
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la carte choisie par le joueur ou {@code null} si le joueur a choisi
     *         de passer ou si la liste de cartes était vide.
     */
    public Card chooseCardFromButtons(String instruction, List<Card> cards, boolean canPass) {
        // liste de noms de cartes
        List<Button> buttons = new ArrayList<>();
        // ajout des options correspondant aux cartes de la liste
        for (Card c : cards)
            buttons.add(new Button(c.getName(), c.getName()));

        String choice = choose(instruction, new ArrayList<>(), buttons, canPass);
        if (choice.startsWith("BUTTON:")) {
            for (Card c : cards) {
                if (c.hasName(choice.split(":")[1])) {
                    return c;
                }
            }
        }
        return null;
    }

    /**
     * Demande au joueur de choisir une option parmi une liste de boutons affichés à
     * l'écran.
     * 
     * @param instruction message à afficher à l'écran pour indiquer au joueur la
     *                    nature du choix qui est attendu
     * @param buttons     liste des boutons à afficher à l'écran. Chaque bouton
     *                    correspond à une option de choix qui sera ajoutée à la
     *                    liste des choix valides ({@code "BUTTON:<value>"}).
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix.
     * @return la valeur du bouton choisi par le joueur ou {@code null} si le joueur
     *         a choisi de passer ou si la liste de boutons était vide.
     */
    public String chooseStringFromButtons(String instruction, List<Button> buttons, boolean canPass) {
        String choice = choose(instruction, new ArrayList<>(), buttons, canPass);
        if (choice.startsWith("BUTTON:")) {
            return choice.split(":")[1];
        }
        return null;
    }

    /**
     * Ajoute un message dans le log du jeu qui est affiché dans l'interface
     * graphique.
     * 
     * @param message message à ajouter au log du jeu (peut contenir du HTML pour le
     *                formatage)
     */
    public void log(String message) {
        game.log(message);
    }

    public void indentLog() {
        game.indentLog();
    }

    public void unindentLog() {
        game.unindentLog();
    }

    /**
     * Démarre le tour du joueur
     * <p>
     * Les compteurs de nombre d'actions, de nombre d'achats et argent sont
     * initialisés
     */
    public void startTurn() {
        game.setUITarget(this);
        numberOfActions.setValue(1);
        money.setValue(0);
        numberOfBuys.setValue(1);
        nbSilverOrGoldPlayed = 0;
        cardsGainedThisTurn.clear();
        cardsBoughtThisTurn.clear();
        canPlayActions = true;
        canPlayTreasures = true;
        execDurationsSequentially();
    }

    private void execDurationsSequentially() {
        Iterator<Card> it = getInPlay().iterator();
        runNext(it, this);
    }

    private void runNext(Iterator<Card> it, Player player) {
        if (!it.hasNext()) {
            return;
        }
        Card card = it.next();
        runCard(card, player).thenRun(() -> runNext(it, player));
    }

    private CompletableFuture<Void> runCard(Card card, Player player) {
        CompletableFuture<Void> future = new CompletableFuture<>();
        ChangeListener<Boolean> listener = new ChangeListener<>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean oldVal, Boolean newVal) {
                if (newVal) {
                    card.hasDurationEffectProperty().removeListener(this);
                    future.complete(null);
                }
            }
        };
        card.hasDurationEffectProperty().addListener(listener);
        card.atStartOfTurn(player);
        return future;
    }

    /**
     * Exécute le tour d'un joueur
     * <p>
     * Cette méthode exécute successivement les phases du tour d'un joueur:
     * <p>
     * 1. (Préparation) initialise les compteurs d'actions, d'achats et d'argent du
     * joueur
     * <p>
     * 2. (Action, Trésor et Achat) Le joueur peut jouer des cartes Action et Trésor
     * de sa main, et acheter des cartes de la réserve. Cependant, dès qu'il joue
     * une carte Trésor, il ne peut plus jouer de carte Action pendant le reste de
     * son tour. De même, dès qu'il achète une carte, il ne peut plus jouer de carte
     * Action ni de carte Trésor pendant le reste de son tour.
     * <p>
     * Le joueur peut passer pour terminer son tour. Pour fluidifier le jeu, le tour
     * se termine également automatiquement lorsque le joueur n'a plus d'achat
     * disponible.
     */
    public void playTurn() {
        // 1. (Préparation)
        startTurn();

        // 2. Boucle principale du tour (phases Action, Trésor et Achat)
        boolean canPlayActions = true;
        boolean canPlayTreasures = true;

        turnloop: while (true) {
            // préparation des choix
            List<String> options = new ArrayList<>();
            List<Button> buttons = new ArrayList<>();
            for (Card c : hand) {
                if (canPlayActions && numberOfActions.getValue() > 0 && c.hasType(CardType.ACTION)) {
                    options.add("HAND:" + c.getName());
                }
                if (canPlayTreasures && c.hasType(CardType.TREASURE)) {
                    options.add("HAND:" + c.getName());
                }
            }
            if (numberOfBuys.getValue() > 0) {
                for (Card c : game.getAvailableSupplyCards()) {
                    if (c.getCost() <= money.getValue()) {
                        options.add("SUPPLY:" + c.getName());
                    }
                }
            }
            if (canPlayTreasures
                    && hand.stream().anyMatch(c -> c.hasType(CardType.TREASURE))) {
                buttons.add(new Button("Play treasures", "treasures"));
            }

            String instruction;
            if (canPlayActions) {
                instruction = "Action phase";
            } else if (canPlayTreasures) {
                instruction = "Treasure phase";
            } else {
                instruction = "Buy phase";
            }
            if (options.isEmpty()) {
                break;
            }
            String choice = choose(instruction, options, buttons, true);
            switch (choice) {
                case "" -> {
                    break turnloop;
                }
                case "BUTTON:treasures" -> {
                    List<Card> treasures = hand.stream().filter(c -> c.hasType(CardType.TREASURE)).toList();
                    for (Card c : treasures)
                        playCard(c);
                    canPlayActions = false;
                }
                case String s when s.startsWith("HAND:") -> {
                    Card cardToPlay = hand.stream()
                            .filter(card -> card.hasName(choice.split(":")[1]))
                            .findFirst()
                            .orElseThrow();
                    if (cardToPlay.hasType(CardType.ACTION)) {
                        numberOfActions.setValue(numberOfActions.getValue() - 1);
                        playCard(cardToPlay);
                    } else if (cardToPlay.hasType(CardType.TREASURE)) {
                        canPlayActions = false;
                        playCard(cardToPlay);
                    }
                }
                case String s when s.startsWith("SUPPLY:") -> {
                    canPlayActions = false;
                    canPlayTreasures = false;
                    String cardName = choice.split(":")[1];
                    Card c = getCardFromSupply(cardName);
                    log("%s buys %s".formatted(toLog(), c.toLog()));
                    gainToDiscard(c);
                    // gestion des token Embargo (uniquement lorsque le joueur achète une carte, pas
                    // lorsqu'il en gagne une par un autre moyen)
                    indentLog();
                    List<Card> gainedCurses = new ArrayList<>();
                    for (int i = 0; i < game.getNumberOfEmbargoTokens(cardName); i++) {
                        Card curse = getCardFromSupply("Curse");
                        if (curse != null) {
                            gainedCurses.add(curse);
                            gainToDiscard(curse);
                        }
                    }
                    if (!gainedCurses.isEmpty()) {
                        log("gains %s from Embargo tokens".formatted(Utils.toLog(gainedCurses)));
                    }
                    unindentLog();
                    numberOfBuys.setValue(numberOfBuys.getValue() - 1);
                    money.setValue(money.getValue() - c.getCost());
                    cardsBoughtThisTurn.add(c);
                }
                default -> {
                }
            }
        }
    }

    /**
     * Fin du tour du joueur
     * <p>
     * Cette méthode exécute la phase de "Clean-up" à la fin du tour d'un joueur:
     * - Les compteurs d'actions, argent et achats du joueur sont remis à 0
     * - Les cartes en main et en jeu sont défaussées (sauf les cartes Duration qui
     * ont encore un effet)
     * - Le joueur pioche les cartes de sa prochaine main (normalement 5 cartes,
     * mais parfois moins selon les effets de certaines cartes)
     */
    public void cleanup() {
        numberOfActions.setValue(0);
        money.setValue(0);
        numberOfBuys.setValue(0);
        // défausse la main
        moveToDiscard(hand);
        // cleanup
        for (Player p : getPlayers()) {
            for (Card c : new ArrayList<>(p.inPlay)) {
                c.onCleanup(p);
            }
        }
        // pioche la nouvelle main (normalement 5 cartes, mais peut être 3 en cas
        // d'Outpost)
        drawToHand(nbCardsToDrawAtCleanup);
        nbCardsToDrawAtCleanup = 5;
    }

    // ==========================
    //Ajouts Sophie

    boolean canPlayActions = true;
    boolean canPlayTreasures = true;

    public void playTreasures() {
        List<Card> treasures = hand.stream().filter(c -> c.hasType(CardType.TREASURE)).toList();
        for (Card c : treasures)
            playCard(c);
    }

    public List<String> getNamesOfCardsInHand() {
        return hand.stream().map(Card::getName).toList();
    }

    public List<String> getNamesOfTreasuresInHand() {
        return hand.stream()
                .filter(c -> c.hasType(CardType.TREASURE))
                .map(Card::getName)
                .toList();
    }

    public List<String> getAvailableSupplyCards() {
        if (numberOfBuys.getValue() > 0) {
            return game.getAvailableSupplyCards().stream()
                    .filter(c -> c.getCost() <= money.getValue())
                    .map(Card::getName)
                    .toList();
        }
        return List.of();
    }

    @Override
    public ObservableList<Card> getHand() {
        return hand;
    }

    @Override
    public ObservableList<Card> getDraw() {
        return draw;
    }

    @Override
    public ObservableList<Card> getDiscard() {
        return discard;
    }

    @Override
    public IntegerProperty numberOfActionsProperty() {
        return numberOfActions;
    }

    @Override
    public IntegerProperty numberOfBuysProperty() {
        return numberOfBuys;
    }

    @Override
    public ObservableList<Card> getInPlay() {
        return inPlay;
    }

    @Override
    public ObservableList<Card> getNativeVillageMat() {
        return nativeVillageMat;
    }

    @Override
    public ObservableList<Card> getIslandMat() {
        return islandMat;
    }

    @Override
    public void playTreasuresWasChosen() {
        currentState.playTreasuresWasChosen();
    }

    @Override
    public void cardInHandWasChosen(String cardName) {
        currentState.cardInHandWasChosen(cardName);
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        currentState.cardInHandWasChosen(cardName);
    }

    public void switchToStateByCardType(String cardName) {
        Card cardToPlay = hand.stream()
                .filter(card -> card.getName().equals(cardName))
                .findFirst()
                .orElseThrow();
        if (cardToPlay.hasType(CardType.ACTION)) {
            setCurrentState(new ActionPhase(this));
            numberOfActions.setValue(numberOfActions.getValue() - 1);
            playCard(cardToPlay);
        } else if (cardToPlay.hasType(CardType.TREASURE)) {
            setCurrentState(new TreasurePhase(this));
            canPlayActions = false;
            playCard(cardToPlay);
        }
    }

    public void playTreasureCard(String cardName) {
        Card cardToPlay = hand.stream()
                .filter(card -> card.getName().equals(cardName))
                .findFirst()
                .orElseThrow();
        canPlayActions = false;
        playCard(cardToPlay);
    }

    public void buy(String cardName) {
        canPlayActions = false;
        canPlayTreasures = false;
        Card c = getCardFromSupply(cardName);
        gainToDiscard(c);
        // gestion des token Embargo (uniquement lorsque le joueur achète une carte, pas
        // lorsqu'il en gagne une par un autre moyen)
        for (int i = 0; i < game.getNumberOfEmbargoTokens(cardName); i++) {
            Card curse = getCardFromSupply("Curse");
            if (curse != null) {
                gainToDiscard(curse);
            }
        }
        numberOfBuys.setValue(numberOfBuys.getValue() - 1);
        money.setValue(money.getValue() - c.getCost());
        cardsBoughtThisTurn.add(c);
    }

    public boolean areBuysCompleted() {
        return numberOfBuys.getValue() == 0;
    }

    public boolean areActionsCompleted() {
        return numberOfActions.getValue() == 0;
    }

    /**
     * Gestion des états du joueur courant
     */
    private PlayerState currentState;

    public void setCurrentState(PlayerState currentState) {
        this.currentState = currentState;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public List<String> getProvincesInHand() {
        return hand.stream().filter(c -> c.hasName("Province")).map(Card::getName)
                .collect(Collectors.toList());
    }

    public void gainTreasure(String cardName) {
        Card gainedCard = getCardFromSupply(cardName);
        if (gainedCard != null) {
            gainToHand(gainedCard);
        }
    }

    public void salvagerAction(String cardName) {
        Card cardToTrash = getCardsInHand().stream()
                .filter(card -> card.getName().equals(cardName))
                .findFirst()
                .orElseThrow();
        incrementMoney(cardToTrash.getCost());
        moveToTrash(cardToTrash);
    }

    public void moveFromHandToSupply(String cardName) {
        Card cardToReturnToSupply = hand.stream().filter(c -> c.getName().equals(cardName)).findFirst().orElse(null);
        moveToSupply(cardToReturnToSupply);
    }

    public Card getCardFromHand(String cardName) {
        return hand.stream().filter(c -> c.getName().equals(cardName)).findFirst().orElse(null);
    }

    public Card getCardFromInPlay(String cardName) {
        return inPlay.stream().filter(c -> c.getName().equals(cardName)).findFirst().orElse(null);
    }
}
