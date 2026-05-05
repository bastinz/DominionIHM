package fr.umontpellier.iut.dominionfx.mechanics.playerstate.plain;

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
            pirateShip.setDidTrashTreasure(true);
            currentPlayer.getGame().setTemporaryCards(null, null);
            complete();
        }
    }
 }