package fr.umontpellier.iut.dominionfx.mechanics.playerstate.ongoingactions;

import fr.umontpellier.iut.dominionfx.mechanics.CardType;
import fr.umontpellier.iut.dominionfx.mechanics.Player;
import fr.umontpellier.iut.dominionfx.mechanics.cards.Card;
import fr.umontpellier.iut.dominionfx.mechanics.cards.seaside.traitees.PirateShip;
import fr.umontpellier.iut.dominionfx.mechanics.playerstate.PlayerState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class PirateShipAttackState extends PlayerState {

    private ObservableList<Card> revealedCards = FXCollections.observableArrayList();
    private Player target;
    private final PirateShip pirateShip;

    public PirateShipAttackState(Player currentPlayer, Player target, List<Card> revealedCards, PirateShip pirateShip) {
        super(currentPlayer);
        getGame().instructionProperty().setValue("Choose a treasure to trash");
        this.target = target;
        this.pirateShip = pirateShip;
        this.revealedCards.addAll(revealedCards);
        currentPlayer.getGame().setTemporaryCards(this.revealedCards, target.getDraw());
    }

    @Override
    public void temporaryCardWasChosen(String cardName) {
        List<String> availableCards = revealedCards.stream().filter(c -> c.hasType(CardType.TREASURE)).map(Card::getName).toList();
        if (!availableCards.isEmpty() && availableCards.contains(cardName)) {
            Card selectedTreasure = revealedCards.stream().filter(c -> c.getName().equals(cardName)).findFirst().orElse(null);
            target.moveToTrash(selectedTreasure);
//            target.moveToDiscard(revealedCards);
            pirateShip.setDidTrashTreasure(true);
            currentPlayer.getGame().setTemporaryCards(null, null);
            complete();
        }
    }
 }


/*
List<Card> revealedCards = target.drawCards(2);
            if (revealedCards.stream().anyMatch(c -> c.hasType(CardType.TREASURE))) {
// si au moins un trésor, choisir un trésor à écarter
Card selectedTreasure = p.chooseCardFromButtons(
        "%s: Choose a treasure to trash".formatted(this),
        revealedCards.stream().filter(c -> c.hasType(CardType.TREASURE)).toList(),
        false);
                target.moveToTrash(selectedTreasure);
didTrashTreasure = true;
        }*/
