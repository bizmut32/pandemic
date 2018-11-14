package Pandemic.View.Components;

import Pandemic.Table.Field;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TableComponent extends StackPane{
    List<Field> fields;
    List<FieldComponent> fieldComponents;
    AnchorPane lines;
    double width = 28;
    double scale = 37;
    public TableComponent(Collection<Field> fields){
        this.fields = new ArrayList<>(fields);
        init();
    }

    public void init(){
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

    public void drawConnections(){
        fields.sort((a, b) ->  a.getName().compareTo(b.getName()));
        lines = new AnchorPane();
        //this.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        for(Field field: fields){
            Field[] neighbours = field.getNeighbours();
            for(Field neighbour: neighbours)
                if(neighbour.getName().compareTo(field.getName()) > 0)
                    drawConnection(field, neighbour);
        }
        this.getChildren().add(lines);
    }
    public void drawConnection(Field a, Field b){
        Line line = new Line(a.getPosition().x * scale, a.getPosition().y * scale, b.getPosition().x * scale, b.getPosition().y * scale);
        line.setStroke(Color.color(0, 0, 0, 0.3));
        line.setStrokeWidth(2);
        lines.getChildren().add(line);
    }
}