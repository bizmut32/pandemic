package Pandemic.View.Components;

import Pandemic.Core.Virus;
import Pandemic.Players.GraphicsPlayer;
import Pandemic.Players.Player;
import Pandemic.View.Effect;
import Pandemic.View.Scenes.GameScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ControllerComponent extends BorderPane {

    private GraphicsPlayer currentPlayer;
    private Text breakOuts; //
    private Text infectionStatus; //
    private Map<Virus, Crystal> antidotes; //
    private Text message;
    private VBox actions; //
    private Map<String, ControllButton> buttons;

    public ControllerComponent(){
        this.init();
    }

    private void init(){
        AnchorPane main = new AnchorPane();
        main.setBackground(new Background(new BackgroundFill(Effect.gradient(
                Color.color(0.2, 0.2, 0.2),
                Color.color(0.3, 0.3, 0.3),
                3,
                true,
                false
        ), null, null)));
        this.setMinHeight(130);
        main.setMinHeight(130);
        main.setPadding(new Insets(0, 50, 0, 50));

        buttons = ControllButton.getButtons();
        HBox buttonsHolder = new HBox();
        Effect.grow(buttonsHolder);
        buttonsHolder.getChildren().addAll(buttons.values());
        buttonsHolder.setSpacing(20);
        buttonsHolder.setAlignment(Pos.CENTER);



        buttons.get("treat").setOnMouseClicked(e -> {
            currentPlayer.test();
        });

        actions = new VBox();
        actions.setSpacing(10);
        actions.setAlignment(Pos.CENTER);
        Effect.grow(actions, true, false, true, true);
        Color c = Color.color(0.1, 0.87, 0);
        actions.getChildren().addAll(new Crystal(15, c), new Crystal(15, c), new Crystal(15, c), new Crystal(15, c));

        main.getChildren().addAll(actions, buttonsHolder);


        this.setTop(getTopComponent());

        this.setCenter(main);
    }

    public void refresh(int actions, Set<Virus> antidotes) {
        for(int i = 0; i < 4 - actions; i++) ((Crystal)this.actions.getChildren().get(i)).disable();
        for(int i = 4 - actions; i < 4; i++)((Crystal)this.actions.getChildren().get(i)).enable();
        for(Map.Entry<Virus, Crystal> e : this.antidotes.entrySet())
            if(antidotes.contains(e.getKey())) e.getValue().enable();
            else e.getValue().disable();
    }

    public void refresh(int breakOuts, int infectionStatus){
        this.breakOuts.setText(Integer.toString(breakOuts));
        this.infectionStatus.setText(Integer.toString(infectionStatus));
    }

    public void setPlayer(GraphicsPlayer p){
        this.currentPlayer = p;
    }

    public void message(String msg){
        this.message.setText(msg);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Pane getTopComponent(){
        Font defaultFont = Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 14);
        DropShadow defaultShadow = new DropShadow(BlurType.GAUSSIAN, Color.color(0, 0, 0, 0.3), 2, 0.4, 1, 1);

        AnchorPane additionalInformaition = new AnchorPane();
        additionalInformaition.setMinHeight(30);
        additionalInformaition.setMaxHeight(30);
        additionalInformaition.setBackground(new Background(new BackgroundFill(Color.color(0.3, 0.3, 0.3), null, null)));
        additionalInformaition.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.grayRgb(1, 0.5), 10, 0.4, 0, 0));

        StackPane mainMessage = new StackPane();
        Effect.setSize(mainMessage, 600, 60);
        mainMessage.setAlignment(Pos.CENTER);
        mainMessage.setBackground(new Background(new BackgroundFill(Color.color(0.2, 0.2, 0.2), new CornerRadii(70, 70, 0, 0, false), null)));
        mainMessage.setTranslateY(10);
        mainMessage.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.grayRgb(1, 0.5), 20, 0.4, 0, 0));
        message = new Text();
        message.setEffect(defaultShadow);
        message.setFill(Color.WHITE);
        message.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        message.setTextAlignment(TextAlignment.CENTER);
        message.setText("I'm testing this shit and it's gonna look dope");
        mainMessage.getChildren().add(message);

        StackPane topPart = new StackPane(additionalInformaition, mainMessage);
        topPart.setAlignment(Pos.BOTTOM_CENTER);

        Effect.grow(additionalInformaition, false, true, true, true);


        Text breakOutsLabel = new Text("Breakouts:");
        Text antdotesLabel = new Text("Antidotes:");
        Text infectionStatusLabel = new Text("Infection status:");
        breakOutsLabel.setFill(Color.WHITE);
        breakOutsLabel.setEffect(defaultShadow);
        breakOutsLabel.setFont(defaultFont);
        antdotesLabel.setFill(Color.WHITE);
        antdotesLabel.setEffect(defaultShadow);
        antdotesLabel.setFont(defaultFont);
        infectionStatusLabel.setFill(Color.WHITE);
        infectionStatusLabel.setEffect(defaultShadow);
        infectionStatusLabel.setFont(defaultFont);

        breakOuts = new Text("3");
        breakOuts.setFill(Color.WHITE);
        breakOuts.setEffect(defaultShadow);
        breakOuts.setFont(defaultFont);

        infectionStatus = new Text("2");
        infectionStatus.setFill(Color.WHITE);
        infectionStatus.setEffect(defaultShadow);
        infectionStatus.setFont(defaultFont);

        HBox bo = new HBox(breakOutsLabel, breakOuts);
        HBox is = new HBox(infectionStatusLabel, infectionStatus);
        HBox info = new HBox(bo, is);
        info.setSpacing(15); bo.setSpacing(10); is.setSpacing(10);
        info.setAlignment(Pos.CENTER); bo.setAlignment(Pos.CENTER); is.setAlignment(Pos.CENTER);
        info.setPadding(new Insets(0, 0, 0, 20));
        Effect.grow(info, true, false, true, true);

        antidotes = new HashMap<>();
        antidotes.put(Virus.RED, new Crystal(20, GameScene.colorOfVirus(Virus.RED), false));
        antidotes.put(Virus.BLACK, new Crystal(20, GameScene.colorOfVirus(Virus.BLACK), false));
        antidotes.put(Virus.YELLOW, new Crystal(20, GameScene.colorOfVirus(Virus.YELLOW), false));
        antidotes.put(Virus.BLUE, new Crystal(20, GameScene.colorOfVirus(Virus.BLUE), false));

        HBox antidotesContainer = new HBox();
        antidotesContainer.setSpacing(18);
        antidotesContainer.setAlignment(Pos.CENTER);
        antidotesContainer.getChildren().addAll(antidotes.values());

        HBox a = new HBox();
        a.setAlignment(Pos.CENTER);
        a.setFillHeight(true);
        a.setSpacing(20);
        a.setPadding(new Insets(0, 20, 0, 0));
        Effect.grow(a, true, true, true, false);
        a.getChildren().addAll(antdotesLabel, antidotesContainer);


        additionalInformaition.getChildren().addAll(info, a);

        return topPart;
    }

    static private class ControllButton extends StackPane {
        private String text;
        private ImageView image;
        ControllButton(String text, Image img){
            this.text = text;
            image = new ImageView();
            image.setImage(img);
            image.setFitHeight(30);
            image.setFitWidth(30);
            init();
        }
        private void init(){
            VBox main = new VBox();
            this.setAlignment(Pos.CENTER);
            main.setAlignment(Pos.CENTER);
            main.setSpacing(10);
            Effect.setSize(main, 100, 100);
            main.setBackground(new Background(new BackgroundFill(Effect.gradient(
                    Color.color(1, 1, 1, 0.05), Color.color(1, 1, 1, 0.2), 2, true, false
            ), new CornerRadii(20), null)));
            main.setCursor(Cursor.HAND);

            Text label = new Text(text);
            label.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 17));
            label.setFill(Color.WHITE);
            main.getChildren().addAll(image, label);
            this.getChildren().add(main);

            main.setBorder(new Border(new BorderStroke(Color.color(1, 1, 1, 0.1), BorderStrokeStyle.SOLID, new CornerRadii(20), BorderWidths.DEFAULT)));

            main.setOnMouseEntered(e -> {
                main.setBackground(new Background(new BackgroundFill(Effect.gradient(
                        Color.color(1, 1, 1, 0.2), Color.color(1, 1, 1, 0.05), 2, true, false
                ), new CornerRadii(20), null)));

                label.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.color(1, 1, 1, 0.3), 5, 0.4, 0, 0));
                image.setEffect(new DropShadow(BlurType.GAUSSIAN, Color.color(1, 1, 1, 0.3), 5, 0.4, 0, 0));
            });
            main.setOnMouseExited(e -> {
                main.setBackground(new Background(new BackgroundFill(Effect.gradient(
                        Color.color(1, 1, 1, 0.05), Color.color(1, 1, 1, 0.2), 2, true, false
                ), new CornerRadii(20), null)));
                label.setEffect(null);
                image.setEffect(null);
            });
        }

        static Map<String, ControllButton> getButtons(){
            Map<String, ControllButton> buttons = new HashMap<>(6);
            buttons.put("treat", new ControllButton("Treat", new Image("file:res/treat.png")));
            buttons.put("share", new ControllButton("Share", new Image("file:res/share.png")));
            buttons.put("build", new ControllButton("Build", new Image("file:res/build.png")));
            buttons.put("antidote", new ControllButton("Antidote", new Image("file:res/antidote.png")));
            buttons.put("pass", new ControllButton("Pass", new Image("file:res/pass.png")));
            buttons.put("restart", new ControllButton("Restart", new Image("file:res/restart.png") ));

            return buttons;
        }
    }

    private static class Crystal extends Pane{
        private final double size;
        private final Color color;

        Crystal(double size, Color color){
            this.size = size;
            this.color = color;
            Effect.setSize(this, size, size);
            enable();
        }

        Crystal(double size, Color color, boolean isEnabled){
            this.size = size;
            this.color = color;
            Effect.setSize(this, size, size);
            if(!isEnabled) disable();
            else enable();
        }

        void enable(){
            this.setBackground(new Background(new BackgroundFill(Effect.gradient(this.color, Color.WHITE, 2, false, false), new CornerRadii(size/2), null)));
            this.setEffect(new DropShadow(BlurType.GAUSSIAN, color, 10, 0.4, 0, 0));
            this.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(size / 2), BorderWidths.DEFAULT)));
        }

        void disable(){
            this.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, null, null)));
            this.setBorder(new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(size / 2), BorderWidths.DEFAULT)));
            this.setEffect(new DropShadow(BlurType.GAUSSIAN, color, 5, 0.4, 0, 0));
        }
    }
}
