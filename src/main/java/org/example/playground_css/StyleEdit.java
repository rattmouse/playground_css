package org.example.playground_css;

import javafx.scene.control.TextArea;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.StringJoiner;

public class StyleEdit extends TextArea {
    private final String resourceName;

    // TODO update to handle bindings on its own given a central display
    // TODO update to be two text areas one for full css style sheet and other for center display style property

    public StyleEdit(String resourceName) {
        super();
        this.resourceName = resourceName;
        build();
    }

    public void build() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        try {
            Files.readAllLines(Path.of(PlaygroundCssApp.class.getResource(resourceName).getFile())).forEach(stringJoiner::add);
        } catch (IOException e) {
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).forEach(stringJoiner::add);
        }
        setText(stringJoiner.toString());
    }
}
