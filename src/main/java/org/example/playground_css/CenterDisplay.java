package org.example.playground_css;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class CenterDisplay extends StackPane {
    private final double scale;

    public CenterDisplay(double scale) {
        super();
        this.scale = scale;
        build();
    }

    public CenterDisplay() {
        this(1.0);
    }

    public void build() {
        Button button = new Button("Button");
        getChildren().add(button);
        setAlignment(button, Pos.CENTER);

        getChildren().forEach(c -> c.setScaleX(scale));
        getChildren().forEach(c -> c.setScaleY(scale));
    }
}
