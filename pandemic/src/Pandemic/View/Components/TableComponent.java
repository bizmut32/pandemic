package Pandemic.View.Components;

import Pandemic.Players.GraphicsPlayer;
import Pandemic.Table.Field;
import Pandemic.View.Effect;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TableComponent extends StackPane{
    private List<Field> fields;
    private List<FieldComponent> fieldComponents;
    private AnchorPane lines;
    private double width = 28;
    private double scale = 37;

    private boolean drawn = false;

    public TableComponent(Collection<Field> fields){
        this.fields = new ArrayList<>(fields);
        init();
    }

    private void init(){
        this.setMaxWidth(width * scale);
        this.setMinHeight(width * scale / 2);
        this.setMaxHeight(width * scale / 2);
        this.setAlignment(Pos.TOP_LEFT);
        fieldComponents = new ArrayList<>();
        for(Field field: fields){
            FieldComponent component = new FieldComponent(field);
            component.setTranslateX((field.getPosition().x) * scale - component.getMinWidth() / 2);
            component.setTranslateY((field.getPosition().y) * scale - component.getMinHeight() / 2);
            fieldComponents.add(component);
        }
        drawConnections();
        this.getChildren().addAll(fieldComponents);
    }

    private void drawConnections(){
        fields.sort((a, b) ->  a.getName().compareTo(b.getName()));
        lines = new AnchorPane();
        Effect.setSize(lines, width * scale, width * scale / 2);
        //this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        for(Field field: fields){
            Field[] neighbours = field.getNeighbours();
            for(Field neighbour: neighbours)
                if(neighbour.getName().compareTo(field.getName()) > 0)
                    drawConnection(field, neighbour);
        }
        this.getChildren().add(0, lines);
    }
    private void drawConnection(Field a, Field b){
        Line line;
        if(a.getName().equals("Manila") && b.getName().equals("San Fransisco") || b.getName().equals("Manila") && a.getName().equals("San Fransisco") ||
            a.getName().equals("Sidney") && b.getName().equals("Los Angeles") || b.getName().equals("Sidney") && a.getName().equals("Los Angeles") ||
            a.getName().equals("Tokyo") && b.getName().equals("San Fransisco") || b.getName().equals("Tokyo") && a.getName().equals("San Fransisco")){
            line = new Line(a.getPosition().x * scale, a.getPosition().y * scale, (a.getPosition().x + 15 - b.getPosition().x) * scale , b.getPosition().y * scale);
            if(!drawn){
                drawn = true;
                drawConnection(b, a);
            }
            else
                drawn = false;

        }
        else
            line = new Line(a.getPosition().x * scale, a.getPosition().y * scale, b.getPosition().x * scale, b.getPosition().y * scale);
        line.setStroke(Color.color(0.9, 0.9, 0.9, 0.7));
        line.setStrokeWidth(2);
        lines.getChildren().add(line);
    }

    public void refresh(GraphicsPlayer newCurrentPlayer){
        for(FieldComponent fc: fieldComponents) fc.refresh(newCurrentPlayer);
    }
}
