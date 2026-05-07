package fr.umontpellier.iut.dominionfx.mechanics;

import fr.umontpellier.iut.dominionfx.IPlayer;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PirateReactionPhase;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.StartTurnState;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.TreasurePhase;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static fr.umontpellier.iut.dominionfx.mechanics.CardType.TREASURE;

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
    private final BooleanProperty nativeVillagePlayed;
    private final BooleanProperty waitForYesOrNo;

    private final ObservableList<Card> cardsGainedThisTurn;

    private final ObservableList<Card> cardsBoughtThisTurn;

    private int nbCardsToDrawAtCleanup = 5;

    /**
     * Nombre de jetons
     */
    private IntegerProperty pirateShipCounter;

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
        nativeVillagePlayed = new SimpleBooleanProperty(false);
        waitForYesOrNo = new SimpleBooleanProperty(false);
        pirateShipCounter = new SimpleIntegerProperty(0);
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
        return pirateShipCounter.getValue();
    }

    public IntegerProperty pirateShipCounterProperty() {
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
     * Renvoie une liste des cartes que le joueur a dans son deck.
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
     * Renvoie une liste des cartes que le joueur a en jeu.
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
     * Renvoie une liste des cartes que le joueur a mises de côté.
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
     * Renvoie une liste des cartes que le joueur a sur le Native Village Mat.
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
    public List<Player> getPlayers() {
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
        pirateShipCounter.setValue(pirateShipCounter.getValue() + 1);
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
            return draw.getFirst(); // à revoir plutôt .removeFirst() pour la mise à jour de la taille de la draw
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
    public CompletableFuture<Void> playCard(Card c) {
        moveToInPlay(c);
        return c.play(this)
                .thenRun(() -> {
                    for (Player p : getPlayers()) {
                        for (Card cardInPlay : p.inPlay) {
                            cardInPlay.onPlayerPlayCard(this, c, p);
                        }
                    }
                });
    }

    /**
     * Le joueur gagne une carte.
     * <p>
     * Si la carte n'est pas {@code null}, elle est déplacée sur la défausse
     * du joueur.
     *
     * @param gainedCard carte à gagner (éventuellement {@code null})
     */
    public CompletableFuture<Void> gainTo(Card gainedCard, List<Card> location) {
        if (gainedCard == null) {
            return CompletableFuture.completedFuture(null);
        }
        gainedCard.moveTo(location);
        cardsGainedThisTurn.add(gainedCard);
        // exécuter les effets déclenchés par le gain d'une carte
        // pour chaque joueur (en commençant par le joueur qui a gagné la carte)
        // on exécute tous les effets "on gain" des cartes en jeu du joueur
        // puis on demande au joueur s'il veut utiliser une carte réaction
        return onGainedCardAllPlayers(gainedCard);
    }

    public CompletableFuture<Void> reactOnGainCard(Player owner, Card gainedCard) {
        PirateReactionPhase phase = new PirateReactionPhase(this, owner, gainedCard);
        setCurrentState(phase);
        return phase.getCompletionFuture();
    }

    private CompletableFuture<Void> onGainedCardAllPlayers(Card gainedCard) {
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        for (Player cardOwner : getPlayers()) {
            for (Card cardInPlay : new ArrayList<>(cardOwner.inPlay)) {
                future = future.thenCompose(v ->  cardInPlay.onPlayerGainCard(this, gainedCard, cardOwner));
            }
            // Réaction du joueur (après ses cartes)
            future = future.thenCompose(v ->
                    reactOnGainCard(cardOwner, gainedCard)
            );
        }
        return future;
    }

    public CompletableFuture<Void> gainToDiscard(Card c) {
        return gainTo(c, discard);
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
     * Démarre le tour du joueur
     * <p>
     * Les compteurs de nombre d'actions, de nombre d'achats et argent sont
     * initialisés
     */
    public void startTurn() {
        numberOfActions.setValue(1);
        numberOfBuys.setValue(1);
        money.setValue(0);
        nbSilverOrGoldPlayed = 0;
        cardsGainedThisTurn.clear();
        cardsBoughtThisTurn.clear();
        execDurationsSequentially().thenRun(() -> setCurrentState(new StartTurnState(this)));
    }

    private CompletableFuture<Void> execDurationsSequentially() {
        List<Card> cards = new ArrayList<>(getInPlay());
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        for (Card card : cards) {
            future = future.thenCompose(v -> card.atStartOfTurn(this));
        }
        return future;
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
    public CompletableFuture<Void> cleanup() {
        numberOfActions.setValue(0);
        money.setValue(0);
        numberOfBuys.setValue(0);
        // défausse la main
        moveToDiscard(hand);
        // cleanup
/*        for (Player p : getPlayers()) {
            for (Card c : new ArrayList<>(p.inPlay)) {
                c.onCleanup(p);
            }
        }*/

        return cleanupAllPlayers().thenRun(() -> {
            // pioche la nouvelle main (normalement 5 cartes, mais peut être 3 en cas
            // d'Outpost)
            drawToHand(nbCardsToDrawAtCleanup);
            nbCardsToDrawAtCleanup = 5;
        });
    }

    private CompletableFuture<Void> cleanupAllPlayers() {
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        for (Player p : getPlayers()) {
            // Et pour chaque carte du joueur
            future = future.thenCompose(v -> cleanupPlayerCards(p));
        }
        return future;
    }

    private CompletableFuture<Void> cleanupPlayerCards(Player p) {
        CompletableFuture<Void> future = CompletableFuture.completedFuture(null);
        for (Card c : new ArrayList<>(p.inPlay)) {
            future = future.thenCompose(v -> c.onCleanup(p));
        }
        return future;
    }

    // ==========================
    //Ajouts Sophie

    public void playTreasures() {
        List<Card> treasures = hand.stream().filter(c -> c.hasType(TREASURE)).toList();
        for (Card c : treasures)
            playCard(c);
    }

    public void answer(String answer) {
        waitForYesOrNoProperty().setValue(false);
        currentState.answer(answer);
    }

    public List<String> getNamesOfCardsInHand() {
        return hand.stream().map(Card::getName).toList();
    }

    public List<String> getNamesOfTreasuresInHand() {
        return hand.stream()
                .filter(c -> c.hasType(TREASURE))
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
    public void addToMat() {
        currentState.addToMat();
    }

    @Override
    public void takeFromMat() {
        currentState.takeFromMat();
    }

    public void switchToStateByCardType(String cardName) {
        Card cardToPlay = hand.stream()
                .filter(card -> card.getName().equals(cardName))
                .findFirst()
                .orElseThrow();
        if (cardToPlay.hasType(CardType.ACTION)) {
            incrementActions(-1);
        } else if (cardToPlay.hasType(TREASURE)) {
            setCurrentState(new TreasurePhase(this));
            numberOfActions.setValue(0);
        }
        playCard(cardToPlay).thenRun(currentState::moveToNextPhase);
    }

    public void playTreasureCard(String cardName) {
        Card cardToPlay = hand.stream()
                .filter(card -> card.getName().equals(cardName))
                .findFirst()
                .orElseThrow();
        numberOfActions.setValue(0);
        playCard(cardToPlay).thenRun(currentState::moveToNextPhase);
    }

    public CompletableFuture<Void> buy(String cardName) {
        Card c = getCardFromSupply(cardName);
        incrementBuys(-1);
        money.setValue(money.getValue() - c.getCost());
        return gainToDiscard(c).thenRun(() -> {
            cardsBoughtThisTurn.add(c);
            // gestion des token Embargo (uniquement lorsque le joueur achète une carte, pas
            // lorsqu'il en gagne une par un autre moyen)
            for (int i = 0; i < game.getNumberOfEmbargoTokens(cardName); i++) {
                Card curse = getCardFromSupply("Curse");
                if (curse != null) {
                    gainToDiscard(curse);
                }
            }
        });
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

    public BooleanProperty nativeVillagePlayedProperty() {
        return nativeVillagePlayed;
    }

    public void setNativeVillagePlayed(boolean nativeVillagePlayed) {
        this.nativeVillagePlayed.set(nativeVillagePlayed);
    }

    public boolean isWaitForYesOrNo() {
        return waitForYesOrNo.get();
    }

    public BooleanProperty waitForYesOrNoProperty() {
        return waitForYesOrNo;
    }

    public void setWaitForYesOrNo(boolean waitForYesOrNo) {
        this.waitForYesOrNo.set(waitForYesOrNo);
    }

    public void endActionPhase() {
        numberOfActions.setValue(0);
    }

    public void endTreasurePhase() {
        numberOfBuys.setValue(0);
    }
}