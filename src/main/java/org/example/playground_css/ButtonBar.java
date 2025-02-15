package org.example.playground_css;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ButtonBar extends StackPane {

    private final VBox buttonBox = new VBox(8.0);

    public ButtonBar(double width) {
        super();
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.heightProperty().bind(heightProperty());
        getChildren().add(rectangle);

        buttonBox.setAlignment(Pos.CENTER);
        getChildren().add(buttonBox);

        StackPane.setAlignment(buttonBox, Pos.CENTER);
    }

    public void addButton(String name, EventHandler<ActionEvent> onAction) {
        Button button = new Button(name);
        button.setOnAction(onAction);
        buttonBox.getChildren().add(button);
    }

}
