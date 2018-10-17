package Pandemic.Characters;

import Pandemic.Hand;
import Pandemic.Table.Field;

import java.io.Serializable;

public enum CharacterType implements Serializable {
    Medic("Medic", ""){
        @Override
        public Character createCharacter(Hand h, Field f) {
            return new Medic(h, f);
        }
    },
    ContingencyPlanner("ContingencyPlanner", ""){
        public Character createCharacter(Hand h, Field f) {
            return new ContingencyPlanner(h, f);
        }
    },
    Researcher("Researcher", ""){
        public Character createCharacter(Hand h, Field f) {
            return new Researcher(h, f);
        }
    },
    Scientist("Scientist", ""){
        public Character createCharacter(Hand h, Field f) {
            return new Scientist(h, f);
        }
    },
    QuarantineSpecialist("QuarantineSpecialist", ""){
        public Character createCharacter(Hand h, Field f) {
            return new QuarantineSpecialist(h, f);
        }
    },
    Dispatcher("Dispatcher", ""){
        public Character createCharacter(Hand h, Field f) {
            return new Dispatcher(h, f);
        }
    },
    OperationExpert("OpertionExpert", ""){
        public Character createCharacter(Hand h, Field f) {
            return new OperationExpert(h, f);
        }
    };

    public final String name;
    public final String color;
    CharacterType(String n, String c){
        name = n;
        color = c;
    }

    abstract public Character createCharacter(Hand h, Field f);
}