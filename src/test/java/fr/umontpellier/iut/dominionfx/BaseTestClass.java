package fr.umontpellier.iut.dominionfx;

import fr.umontpellier.iut.dominionfx.mechanics.Game;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class BaseTestClass extends ApplicationTest {

    protected DominionIHM dominionIHM = new DominionIHM(true);
    protected Game game;
    protected Parent handPane, inPlayPane, supplyPane, temporaryCards;

    @Override
    public void start(Stage stage) {
        dominionIHM.setGame(game);
        stage.setX(0);
        stage.setY(0);
        setPlayersHands();
        dominionIHM.start(stage);
        initPanes();
    }

    public void setPlayersHands() {
    }

    public void addToFirstPlayersHand(String cardName) {
        Player firstPlayer = game.getFirstPlayer();
        getFromSupplyToHand(firstPlayer, cardName);
    }

    public void addToSecondPlayersHand(String cardName) {
        Player secondPlayer = game.getLastPlayer();
        getFromSupplyToHand(secondPlayer, cardName);
    }

    public void addToPlayerSHand(Player player, String cardName) {
        getFromSupplyToHand(player, cardName);
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

    public void clickOnYes() {
        Node treasuresButton = lookup("#yesButton").query();
        clickOn(treasuresButton);
    }

    public void clickOnNo() {
        Node treasuresButton = lookup("#noButton").query();
        clickOn(treasuresButton);
    }


    public void clickOnFirstCardInHand() {
        Node nodeACliquer = handPane.getChildrenUnmodifiable().getFirst();
        clickOn(nodeACliquer);
    }

    public void pause(int nbSeconds) {
        WaitForAsyncUtils.sleep(nbSeconds, TimeUnit.SECONDS);
    }

    public void getFromSupplyToHand(Player player, String cardName) {
        Platform.runLater(() -> {
            player.moveToHand(player.getCardFromSupply(cardName));
        });
        WaitForAsyncUtils.waitForFxEvents();
    /*    try {
            WaitForAsyncUtils.waitFor(5, TimeUnit.SECONDS, () ->
                    listContainsCard(player.getHand(), cardName)
            );
        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }*/
    }

    public void addCardForNextDraw(String cardName) { // à faire avant le passage au prochain joueur
        Player currentPlayer = game.currentPlayer();
        Card c = currentPlayer.getCardFromSupply(cardName);;
        Platform.runLater(() -> {
            c.moveTo(currentPlayer.getDraw());
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    protected void clickOnAddToNativeVillageMat() {
        Node n = lookup("#addToNativeVillageMat").query();
        clickOn(n);
    }

    protected void clickOnTakeFromNativeVillageMat() {
        Node n = lookup("#takeFromNativeVillageMat").query();
        clickOn(n);
    }


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

    public boolean listContainsCard(List<Card> cards, String cardName) {
        return cards.stream().map(Card::getName).toList().contains(cardName);
    }
}