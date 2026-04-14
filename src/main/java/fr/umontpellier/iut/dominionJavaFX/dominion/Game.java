package fr.umontpellier.iut.dominionJavaFX.dominion;

import fr.umontpellier.iut.dominionJavaFX.IGame;
import fr.umontpellier.iut.dominionJavaFX.IPlayer;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.Card;
import fr.umontpellier.iut.dominionJavaFX.dominion.cards.FactorySupplyPile;
import fr.umontpellier.iut.dominionJavaFX.dominion.playerstate.ActionState;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * Class représentant une partie de Dominion
 */
public class Game extends Task<Void> implements Runnable, IGame {
    /**
     * Tableau contenant les joueurs de la partie
     */
    /*private*/ final ArrayList<Player> players;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Le joueur dont c'est actuellement le tour
     */
    private ObjectProperty<Player> currentTurnPlayer;

    private ObjectProperty<Player> previousTurnPlayer;

    /**
     * Instruction à afficher au joueur actif
     */
    private ObjectProperty<String> instruction;

    private boolean samePlayerShouldPlayExtraTurn = false;

    /**
     * Numéro du tour courant (commence à 1 et est incrémenté à chaque fois que
     * le tour d'un nouveau joueur commence)
     */
    private int turnNumber = 1;

    /**
     * Messages envoyés dans le log du jeu (pour affichage dans l'interface
     * graphique)
     */
    private ArrayList<String> logLines = new ArrayList<>();
    private int logIndentLevel = 0;

    /**
     * Liste des piles dans la réserve du jeu.
     * <p>
     * On suppose ici que toutes les listes contiennent des copies de la même
     * carte. Ces piles peuvent être vides en cours de partie si toutes les
     * cartes de la pile ont été achetées ou gagnées par les joueurs.
     */
    private final ObservableList<SupplyPile> supplyPiles;

    /**
     * Liste des cartes qui ont été écartées (trash)
     */
    private final List<Card> trashedCards;

    /**
     * Scanner permettant de lire les entrées au clavier
     */
    private final Scanner scanner;

    private final LinkedBlockingQueue<String> inputQueue;

    /**
     * Constructeur
     *
     * @param playerNames  liste des noms des joueurs qui participent à la
     *                     partie. Le constructeur doit créer les objets
     *                     correspondant aux joueurs
     * @param kingdomPiles nom des cartes "royaume" à utiliser pour la partie
     */
    public Game(String[] playerNames, String[] kingdomPiles) {
        instruction = new SimpleObjectProperty<>("");
        this.inputQueue = new LinkedBlockingQueue<>();
        int nbPlayers = playerNames.length;
        trashedCards = new ArrayList<>();
        scanner = new Scanner(System.in);

        // Création des piles de réserve
        supplyPiles = FXCollections.observableArrayList();
        for (String cardName : kingdomPiles) {
            supplyPiles.add(FactorySupplyPile.createSupplyPile(cardName, nbPlayers));
        }
        supplyPiles.sort(new PileComparator());
        // Ajout des piles communes à la réserve
        supplyPiles.add(FactorySupplyPile.createSupplyPile("Copper", nbPlayers));
        supplyPiles.add(FactorySupplyPile.createSupplyPile("Silver", nbPlayers));
        supplyPiles.add(FactorySupplyPile.createSupplyPile("Gold", nbPlayers));
        supplyPiles.add(FactorySupplyPile.createSupplyPile("Estate", nbPlayers));
        supplyPiles.add(FactorySupplyPile.createSupplyPile("Duchy", nbPlayers));
        supplyPiles.add(FactorySupplyPile.createSupplyPile("Province", nbPlayers));
        supplyPiles.add(FactorySupplyPile.createSupplyPile("Curse", nbPlayers));

        // Création des joueurs
        players = new ArrayList<>(nbPlayers);
        for (String playerName : playerNames)
            players.add(new Player(playerName, this));
        currentTurnPlayer = new SimpleObjectProperty<>();
        previousTurnPlayer = new SimpleObjectProperty<>();
    }

    /**
     * Renvoie l'indice du joueur passé en argument dans le tableau des
     * joueurs, ou -1 si le joueur n'est pas dans le tableau.
     */
    public int getPlayerIndex(Player p) {
        return players.indexOf(p);
    }

    public Player getCurrentTurnPlayer() {
        return currentTurnPlayer.getValue();
    }

    public Player getPreviousTurnPlayer() {
        return previousTurnPlayer.getValue();
    }

    public void setSamePlayerShouldPlayExtraTurn(boolean b) {
        samePlayerShouldPlayExtraTurn = b;
    }

    /**
     * Renvoie la liste des adversaires du joueur passé en argument, dans
     * l'ordre dans lequel ils apparaissent à partir du joueur {@code p}.
     *
     * @param p joueur dont on veut renvoyer la liste des adversaires. On
     *          suppose que {@code p} est bien dans le tableau des joueurs.
     * @return un {@code ArrayList} contenant les autres joueurs de la partie
     *         en commençant par celui qui se trouve juste après {@code p} et
     *         en terminant par celui qui se trouve juste avant (le tableau est
     *         considéré comme cyclique, c'est-à-dire qu'après le premier
     *         élément on revient au premier).
     */
    public List<Player> otherPlayers(Player p) {
        int index = getPlayerIndex(p);
        List<Player> result = new ArrayList<>();
        for (int i = index + 1; i < players.size(); i++)
            result.add(players.get(i));

        for (int i = 0; i < index; i++)
            result.add(players.get(i));

        return result;
    }

    /**
     * @return une liste de cartes contenant la carte du dessus (la dernière de la
     *         liste) de chaque pile non-vide de la réserve (cartes royaume et
     *         cartes communes)
     */
    public List<Card> getAvailableSupplyCards() {
        List<Card> availableCards = new ArrayList<>();

        for (SupplyPile supply : supplyPiles)
            if (!supply.isEmpty())
                availableCards.add(supply.getLast());
        return availableCards;
    }

    public List<String> getAllSupplyPileNames() {
        return supplyPiles.stream().map(SupplyPile::getName).toList();
    }

    public void addEmbargoToken(String cardName) {
        for (SupplyPile pile : supplyPiles) {
            if (pile.getName().equals(cardName)) {
                pile.addEmbargoToken();
                return;
            }
        }
    }

    public int getNumberOfEmbargoTokens(String cardName) {
        for (SupplyPile pile : supplyPiles) {
            if (pile.getName().equals(cardName)) {
                return pile.getNbEmbargoTokens();
            }
        }
        return 0;
    }

    /**
     * Déplace une carte vers la pile de trash (écartée).
     * 
     * @param c la carte à écarter
     */
    void moveCardToTrash(Card c) {
        c.moveTo(trashedCards);
    }

    /**
     * Renvoie une représentation de l'état de la partie sous forme d'une chaîne
     * de caractères.
     * <p>
     * Cette représentation comporte
     * — le nom du joueur dont c'est le tour
     * — la liste des piles de la réserve en indiquant pour chacune :
     * — le nom de la carte
     * — le nombre de copies disponibles
     * — le prix de la carte entre parenthèses
     * si la pile n'est pas vide, ou "Empty pile" si la pile est vide.
     * <p>
     * On pourrait par exemple avoir l'affichage suivant :
     * <p>
     * -- Toto's Turn --
     * Ambassador x4(3) [Empty pile] Smugglers x5(3) Blockade x10(4) Navigator
     * x10(4) Sailor x8(4) Treasure Map x10(4) Outpost x10(5) Treasury x10(5) Wharf
     * x10(5) Copper x60(0) Silver x32(3) Gold x20(6) Estate x8(2) Duchy x8(5)
     * Province x2(8) Curse x4(0)
     */
    @Override
    public String toString() {
        String title = String.format("     -- %s's Turn --\n", currentTurnPlayer.getValue().getName());
        StringJoiner joiner = new StringJoiner("   ");
        for (List<Card> pile : supplyPiles)
            if (pile.isEmpty())
                joiner.add("[Empty pile]");
            else {
                Card c = pile.getLast();
                joiner.add(String.format("%s x%d(%d)", c.getName(), pile.size(), c.getCost()));
            }
        return title + joiner + "\n";
    }

    /**
     * Méthode utilitaire pour l'interface graphique.
     * À NE PAS MODIFIER.
     */
    public String toJSON() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add("\"turn_player\": " + players.indexOf(currentTurnPlayer));
        StringJoiner kingdomJoiner = new StringJoiner(", ");
        for (SupplyPile pile : supplyPiles) {
            kingdomJoiner.add(
                    "{\"card\": \"%s\", \"number\": %d, \"cost\": %d}"
                            .formatted(pile.getName(), pile.size(), pile.getCost()));
        }
        joiner.add("\"supply\": [" + kingdomJoiner + "]");

        StringJoiner playersJoiner = new StringJoiner(", ");
        for (Player p : players) {
            playersJoiner.add(p.toJSON());
        }
        joiner.add("\"players\": [" + playersJoiner + "]");
        joiner.add("\"log\": ["
                + String.join(", ", logLines.stream().map(s -> "\"" + s.replace("\"", "\\\"") + "\"").toList())
                + "]");
        return "{" + joiner + "}";
    }

    /**
     * Renvoie une carte de la réserve dont le nom est passé en argument.
     *
     * @param cardName nom de la carte à trouver dans la réserve
     * @return la carte du dessus de la pile de réserve dont le nom est passé en
     *         argument ou {@code null} si aucune carte ne correspond (ou si la pile
     *         de cette carte est vide)
     */
   public Card getCardFromSupply(String cardName) {
        for (SupplyPile pile : supplyPiles)
            if (pile.getName().equals(cardName) && !pile.isEmpty()) {
                return pile.getLast();
            }
        return null;
    }

    public void moveToSupply(Card c) {
        for (SupplyPile pile : supplyPiles) {
            if (pile.getName().equals(c.getName())) {
                c.moveTo(pile);
                return;
            }
        }
        throw new RuntimeException("Card not found in supply: " + c.getName());
    }

    /**
     * Teste si la partie est terminée
     *
     * @return un booléen indiquant si la partie est terminée, c'est-à-dire si
     *         au moins l'une des deux conditions de fin suivantes est vraie
     *         - 3 piles ou plus de la réserve sont vides
     *         - la pile de Provinces de la réserve est vide
     */
    public boolean isFinished() {
        int nbEmptyPiles = 0; // nombre de piles vides
        for (SupplyPile pile : supplyPiles) {
            if (pile.getName().equals("Province") && pile.isEmpty()) {
                // si la pile de Provinces est vide, la partie est terminée
                return true;
            }
            if (pile.isEmpty()) {
                nbEmptyPiles += 1;
            }
        }
        return (nbEmptyPiles >= 3);
    }

    /**
     * Passe au joueur suivant et incrémente le numéro du tour si nécessaire.
     * <p>
     * Cette méthode doit mettre à jour l'attribut {@code currentTurnPlayer} pour
     * qu'il référence le joueur dont c'est le tour après l'appel de la
     * méthode.
     */
    public void moveToNextPlayer() {
        currentTurnPlayer.getValue().cleanup();
        previousTurnPlayer.setValue(currentTurnPlayer.getValue());
        if (!samePlayerShouldPlayExtraTurn) {
            // passe au joueur suivant
            int nextPlayerIndex = (players.indexOf(currentTurnPlayer.getValue()) + 1) % players.size();
            currentTurnPlayer.setValue(players.get(nextPlayerIndex));
            turnNumber += 1;
        }
        samePlayerShouldPlayExtraTurn = false;
    }

    /**
     * Boucle d'exécution d'une partie.
     * <p>
     * Cette méthode exécute les tours des joueurs jusqu'à ce que la partie soit
     * terminée. Lorsque la partie se termine, la méthode affiche le score
     * final et les cartes possédées par chacun des joueurs.
     */
    public void runNew() {
        currentTurnPlayer.setValue(players.getFirst());
        getCurrentTurnPlayer().setCurrentState(new ActionState(getCurrentTurnPlayer())) ;
//        currentTurnPlayer.getValue().playTurn();
    }

    /**
     * Boucle d'exécution d'une partie.
     * <p>
     * Cette méthode exécute les tours des joueurs jusqu'à ce que la partie soit
     * terminée. Lorsque la partie se termine, la méthode affiche le score
     * final et les cartes possédées par chacun des joueurs.
     */
    public void run() {
        currentTurnPlayer.setValue(players.get(0));
        while (!isFinished()) {
            // joue le tour du joueur courant
            if (currentTurnPlayer.getValue() != previousTurnPlayer.getValue()) {
                log("<div class=\"turn-title\">%s (turn %d)</div>".formatted(currentTurnPlayer.getValue().toLog(), turnNumber));
            } else {
                log("<div class=\"turn-title\">%s (extra turn)</div>".formatted(currentTurnPlayer.getValue().toLog()));
            }
            currentTurnPlayer.getValue().playTurn();
            moveToNextPlayer();
        }
        // Affiche le score et les cartes de chaque joueur
        log("<div class=\"turn-title\">Game over</div>");
        for (Player p : players) {
            for (Card c : p.getAllOwnedCards()) {
                p.moveToHand(c);
            }
            log("%s: %d Points".formatted(
                    p.toLog(),
                    p.getVictoryPoints()));
            log(Utils.toLog(p.getAllOwnedCards()));
        }
        prompt("Game over", List.of(""), new ArrayList<>(), 0);
    }

    /**
     * Envoie une chaîne de caractères à l'interface graphique
     * <p>
     * Cette méthode ne fait rien mais elle est utilisée par une sous-classe de
     * Game ({@code GameGUI}) qui communique avec l'interface graphique. Vous
     * ne devez pas l'utiliser ni la modifier.
     *
     * @param message chaîne de caractères à envoyer
     */
    public void sendToUI(String message) {
    }

    /**
     * Lit une ligne de l'entrée standard
     * <p>
     * C'est cette méthode qui doit être appelée à chaque fois qu'on veut lire
     * l'entrée clavier de l'utilisateur (par exemple dans Player.choose), ce
     * qui permet de n'avoir qu'un seul Scanner pour tout le programme.
     *
     * @return une chaîne de caractères correspondant à la ligne suivante de
     *         l'entrée standard (sans le retour à la ligne finale)
     */
    public String readLine() {
//        return scanner.nextLine();
        try {
            return inputQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Envoie l'état de la partie pour affichage aux joueurs et à l'UI avant de
     * faire un choix
     *
     * @param instruction l'instruction qui est donnée au joueur
     * @param choices     la liste des choix possibles à afficher à l'utilisateur
     * @param buttons     la liste des boutons à afficher à l'utilisateur
     */
    public void prompt(String instruction, List<String> choices, List<Button> buttons, int activePlayerIndex) {
        // Prépare la version affichée à l'utilisateur
        System.out.println("");
        System.out.println(toString());
        System.out.println(currentTurnPlayer.getValue().toString());
        String ligneInstruction = ">>> " + instruction + "<<<";
        Platform.runLater(() -> {
            this.instruction.set(instruction);
        });
        System.out.println(ligneInstruction);

        // Prépare la représentation envoyée à l'UI
        StringJoiner joiner = new StringJoiner(", ", "{", "}");
        joiner.add("\"game\": " + toJSON());
        joiner.add("\"active_player\": " + activePlayerIndex);
        joiner.add("\"instruction\": \"" + instruction + "\"");
        joiner.add("\"choices\": "
                + choices.stream().map(c -> "\"" + c + "\"").collect(Collectors.joining(", ", "[", "]")));
        joiner.add("\"buttons\": " + buttons.stream()
                .map(b -> String.format("{\"label\": \"%s\", \"value\": \"%s\"}", b.label(), b.value()))
                .toList());
        // Envoie la version pour l'UI
        sendToUI(joiner.toString());
    }

    /**
     * Ajoute un message dans le log du jeu qui est affiché dans l'interface
     * graphique. Le message peut contenir du HTML pour le formatage.
     * 
     * @param message
     */
    public void log(String message) {
        if (logIndentLevel > 0) {
            logLines.add("    ".repeat(logIndentLevel - 1) + "... " + message);
        } else {
            logLines.add(message);
        }
    }

    public void indentLog() {
        logIndentLevel += 1;
    }

    public void unindentLog() {
        logIndentLevel -= 1;
    }

    @Override
    protected Void call() throws Exception {
        run();
        return null;
    }

/*    @Override
    public void skipWasChosen() {
        addInput("");
    }*/
    @Override
    public void skipWasChosen() {
        moveToNextPlayer();
    }

    @Override
    public void supplyWasChosen(String supplyName) {
        addInput("SUPPLY:" + supplyName);
    }

    @Override
    public ObjectProperty<String> instructionProperty() {
        return instruction;
    }

    @Override
    public ObjectProperty<? extends IPlayer> currentPlayerProperty() {
        return currentTurnPlayer;
    }

    public void addInput(String message) {
        inputQueue.add(message);
    }

    @Override
    public ObservableList<SupplyPile> getSupplyPiles() {
        return supplyPiles;
    }

    public void moveToNextPlayerState() {
        moveToNextPlayer();
        getCurrentTurnPlayer().setCurrentState(new ActionState(getCurrentTurnPlayer())) ;
    }

}