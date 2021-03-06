package Pandemic.Core;

import Pandemic.Cards.*;
import Pandemic.Exceptions.EndOfGame;

import java.util.List;

public interface Events {
    void dontInfect();
    List<CityCard> forecast();
    InfectionTrash getTrash();
    void epidemic() throws EndOfGame;
    void replaceCards(List<CityCard> c);
}
