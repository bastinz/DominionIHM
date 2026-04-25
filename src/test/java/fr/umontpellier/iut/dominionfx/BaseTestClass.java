package fr.umontpellier.iut.dominionfx;

import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class BaseTestClass extends ApplicationTest {

    protected DominionIHM dominionIHM = new DominionIHM(true);
    protected Game game;
    protected Parent handPane, inPlayPane, supplyPane, temporaryCards;

    @Override
    public void start(Stage stage) {
        dominionIHM.setGame(game);
        setPlayersHands();
        stage.setY(0);
        dominionIHM.start(stage);
        initPanes();
    }

    public void setPlayersHands() {
    }

    public void addToFirstPlayersHand(String cardName) {
        Player firstPlayer = game.getPlayers().getFirst();
        getFromSupply(firstPlayer, cardName);
    }

    public void addToSecondPlayersHand(String cardName) {
        Player secondPlayer = game.getPlayers().getLast();
        getFromSupply(secondPlayer, cardName);
    }

    public boolean listContainsCard(List<Card> cards, String cardName) {
        return cards.stream().map(Card::getName).toList().contains(cardName);
    }

    public void initPanes() {
        handPane = lookup("#handPane").query();
        inPlayPane = lookup("#inPlayPane").query();
        supplyPane = lookup("#supplyPane").query();
        temporaryCards = lookup("#temporaryCards").query();
    }

    public void clickOnSkip() {
        Node skip = lookup("#skipButton").query();
        if (skip != null && skip.isVisible()) {
            clickOn(skip);
        } else {
            type(KeyCode.ENTER);
        }
    }

    public void clickOnCardInHand(String nomCarte) {
        Node nodeToSelect = findNodeMatchingCondition(handPane, n -> n.getId().startsWith(nomCarte));
        if (nodeToSelect == null) {
//            moveBy(200, 0);
            nodeToSelect = findNodeMatchingCondition(handPane, n -> n.getId().startsWith(nomCarte));
        }
        clickOn(nodeToSelect);
    }

    public void clickOnSupplyPile(String nomCarte) {
        Node nodeToSelect = findNodeMatchingCondition(supplyPane, n -> n.getId().startsWith(nomCarte));
        if (nodeToSelect == null) {
//            moveBy(200, 0);
            nodeToSelect = findNodeMatchingCondition(supplyPane, n -> n.getId().startsWith(nomCarte));
        }
        clickOn(nodeToSelect);
    }

    public void clickOnTemporaryCard(String nomCarte) {
        Node nodeToSelect = findNodeMatchingCondition(temporaryCards, n -> n.getId().startsWith(nomCarte));
        if (nodeToSelect == null) {
//            moveBy(200, 0);
            nodeToSelect = findNodeMatchingCondition(temporaryCards, n -> n.getId().startsWith(nomCarte));
        }
        clickOn(nodeToSelect);
    }

    public void skipUntilHandHasCard(String nomCarte) {
        Node nodeToSelect = findNodeMatchingCondition(handPane, n -> n.getId().startsWith(nomCarte));
        while (nodeToSelect == null) {
            clickOnSkip();
            nodeToSelect = findNodeMatchingCondition(handPane, n -> n.getId().startsWith(nomCarte));
        }
    }

    public void skipUntilHandHasEnoughTreasures(int nbTreasures) {
        while (handPane.getChildrenUnmodifiable().stream()
                .mapToInt(node -> switch (node.getId()) {
                    case "Copper" -> 1;
                    case "Silver" -> 2;
                    case "Gold"   -> 3;
                    default       -> 0;
                })
                .sum() < nbTreasures) {
            clickOnSkip();
        }
    }

    public void clickOnTreasures() {
        Node treasuresButton = lookup("#playTreasuresButton").query();
        clickOn(treasuresButton);
    }

   public void clickOnFirstCardInHand() {
        Node nodeACliquer = handPane.getChildrenUnmodifiable().getFirst();
        clickOn(nodeACliquer);
    }

/*     public void ajouterDeLaMainAuBanc(String nomCarte) {
        Node bancChoisi = inPlayPane.getChildrenUnmodifiable().getFirst();
        clickOnCardInHand(nomCarte);
        clickOn(bancChoisi);
    }

    public void ajouterDeLaMainAuBancAvecPosition(String nomCarte, int indice) {
        Node bancChoisi = inPlayPane.getChildrenUnmodifiable().get(indice);
        clickOnCardInHand(nomCarte);
        clickOn(bancChoisi);
    }

    public void ajouterDeLaMainAuPokemonActif(String nomCarte) {
        clickOnCardInHand(nomCarte);
        clickOnTreasures();
    }

    public void cliquerSurBancAdversaire(int indice) {
        Parent panneauBancAdversaire = trouverNodeAPartirDeLaRacine("panneauBancAdversaire");
        Node bancChoisi = panneauBancAdversaire.getChildrenUnmodifiable().stream()
                .skip(indice).findFirst()
                .get();
        clickOn(bancChoisi);
    }

    public void cliquerSurPokemonDeBancAdversaire(String nomPokemon) {
        Parent panneauBancAdversaire = lookup("#panneauBancAdversaire").query();
        Node bancChoisi = findNodeMatchingCondition(panneauBancAdversaire, n -> n.getId().startsWith(nomPokemon));
        clickOn(bancChoisi);
    }

    public void cliquerPremierPokemonDeBanc() {
        Node bancChoisi = inPlayPane.getChildrenUnmodifiable().getFirst();
        clickOn(bancChoisi);
    }

    public void cliquerPokemonDeBanc(String nomCarte) {
        Node nodeACliquer = findNodeMatchingCondition(inPlayPane, node -> node.getId().startsWith(nomCarte));
        clickOn(nodeACliquer);
    }

    public void cliquerEmplacementBanc(int indice) {
        Node bancChoisi = inPlayPane.getChildrenUnmodifiable().get(indice);
        clickOn(bancChoisi);
    }

    public void cliquerAttaque(String nomAttaque) {
        Parent panneauAttaques = lookup("#panneauAttaques").query();
        Node nodeACliquer = findNodeMatchingCondition(panneauAttaques, node -> node.getId().startsWith(nomAttaque));
        clickOn(nodeACliquer);
    }

    public void cliquerEnergieDuPokemonActif(String carteEnergie) {
        Parent cartesEnergiePokemonActif = lookup("#panneauCartesEnergie").query();
        Node nodeACliquer = findNodeMatchingCondition(cartesEnergiePokemonActif, n -> n.getId().startsWith(carteEnergie));
        clickOn(nodeACliquer);
    }

    public void cliquerEnergieDeLAdversaire() {
        Parent panneauCartesEnergieAdversaire = lookup("#panneauCartesEnergieAdversaire").query();
        Node carteEnergie = panneauCartesEnergieAdversaire.getChildrenUnmodifiable().getFirst();
        clickOn(carteEnergie);
    }

    public void cliquerCarteComplementaire(String nomCarte) {
        Parent panneauChoixComplementaires = trouverNodeAPartirDeLaRacine("panneauChoixComplementaires");
        Node nodeChoisi = panneauChoixComplementaires.getChildrenUnmodifiable().getFirst();
        clickOn(nodeChoisi);
        WaitForAsyncUtils.waitForFxEvents();
    }

    public void cliquerPremiereCarteComplementaire() {
        Parent panneauChoixComplementaires = trouverNodeAPartirDeLaRacine("panneauChoixComplementaires");
        Node nodeACliquer = panneauChoixComplementaires.getChildrenUnmodifiable().getFirst();
        clickOn(nodeACliquer);
        WaitForAsyncUtils.waitForFxEvents();
    }*/

    public Node findNodeMatchingCondition(Node root, Predicate<Node> condition) {
        if (condition.test(root)) return root;
        if (root instanceof Parent parent) {
            return parent.getChildrenUnmodifiable().stream()
                    .filter(child -> child.getId() != null)
                    .map(child -> findNodeMatchingCondition(child, condition))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

/*    private Parent trouverNodeAPartirDeLaRacine(String idNode) {
        Node root = lookup("#vueDuGame").query();
        Parent panneauDuPlayerActif = (Parent) findNodeMatchingCondition(root, node -> node.getId().startsWith(idNode));
        return panneauDuPlayerActif;
    }*/

    public void pause(int nbSeconds) {
        WaitForAsyncUtils.sleep(nbSeconds, TimeUnit.SECONDS);
    }

    public void getFromSupply(Player player, String cardName) {
        Future<?> future = WaitForAsyncUtils.asyncFx(() ->
                player.moveToHand(player.getCardFromSupply(cardName))
        );

        WaitForAsyncUtils.waitFor(future);
//        System.out.println("SOOO " + player.getName() + " " + player.getHand());
    }

    protected void clickOnAddToNativeVillageMat() {
        Node n = lookup("#addToNativeVillageMat").query();
        clickOn(n);
    }

    protected void clickOnTakeFromNativeVillageMat() {
        Node n = lookup("#takeFromNativeVillageMat").query();
        clickOn(n);
    }
}
