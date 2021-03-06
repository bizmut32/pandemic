package Pandemic.Core;

import Pandemic.Cards.Card;
import Pandemic.Cards.EventCard;
import Pandemic.Exceptions.EndOfGame;
import Pandemic.Exceptions.UnnecessaryAction;
import Pandemic.Players.Player;
import Pandemic.Table.Field;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IGame extends Events {
    void drop(Card c);
    boolean isAntidoteMade(Virus v);
    void endRound() throws EndOfGame;
    void nextRound() throws EndOfGame;
    void finishRound() throws EndOfGame;
    void createAntidote(Virus color) throws UnnecessaryAction;
    void undo();

    Field getField(String cityName);
    List<EventCard> getEvents();
    void getEvent(EventCard eventCard);
    void drawEvent(EventCard eventCard);
    Map<String, Field> getFields();
    Player[] getPlayers();
    int getInfectionStatus();
    int getBreakOuts();
    Player getCurrentPlayer();
    Set<Virus> getAntidotes();
}
