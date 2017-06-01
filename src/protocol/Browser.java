package protocol;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;

/**
 * GUI session to display network communication
 */
public class Browser extends Application {
    public static final Logger log = Logger.getLogger("Browser");
    public static Stage stage;
    public static Scene scene;
    public static VBox history = new VBox();
    public static ScrollPane conversation = new ScrollPane(history);
    public static HBox console = new HBox();
    public static VBox layout = new VBox();
    public Client client = Client.session;

    public Browser(){
        log.info("interface instantiated");
        client.browser = this;
    }

    private SVGPath drawSVG(String track){
        SVGPath path = new SVGPath();
        path.setContent(track);
        path.setStyle("-fill:#000000;-hover-fill:#d3d3d3");
        return path;
    }

    private void drawUI(){
        TextField field = new TextField();
        field.setPromptText("write here");
        Button button = new Button();
        Double radius = 20.0;
        Group svg = new Group(drawSVG("M50 -8 L-7 -3 L5 7 Z"),drawSVG("M50 -8 L33 9 L6 8 Z"));
        svg.setScaleX(Math.min(20/svg.getBoundsInParent().getWidth(), 20/svg.getBoundsInParent().getHeight()));
        button.setShape(new Circle(radius));
        button.setGraphic(svg);
        button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        button.setMaxSize(radius*2,radius*2);
        button.setMinSize(radius*2,radius*2);
        button.setOnAction(event -> {
            log.info("sending request...");
            bubble(field.getText(),Pos.CENTER_RIGHT);
            String request = field.getText();
            String response = client.contact(request);
            bubble(response,Pos.CENTER_LEFT);
            field.setText("");
        });
        bubble(client.contact(null),Pos.CENTER_LEFT);
        history.setFillWidth(false);
        conversation.setMinHeight(300.0);
        conversation.setFitToHeight(true);
        conversation.addEventFilter(ScrollEvent.SCROLL,event->{
                if (event.getDeltaX() != 0){
                    event.consume();
            }
        });
        console.setAlignment(Pos.BOTTOM_CENTER);
        console.getChildren().addAll(field, button);
        layout.getChildren().addAll(conversation, console);
    }

    @Override
    public void start(Stage browser) throws Exception {
        stage = browser;
        stage.setTitle("Protocol");
        stage.setOnCloseRequest(event -> {
            System.exit(1);
        });
        drawUI();
        scene = new Scene(layout);
        stage.setScene(scene);
        stage.show();
    }

    public void bubble(String message, Pos position){
        Client.log.info("inserting message...");
        Label label = new Label(message);
        label.setWrapText(true);
        label.setFont(new Font("Courier",10));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setAlignment(position);
        history.getChildren().add(label);
        stage.sizeToScene();
    }
}

