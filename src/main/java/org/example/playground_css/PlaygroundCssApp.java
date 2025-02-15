package org.example.playground_css;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// TODO move button bar to the left and table thing to the bottom

public class PlaygroundCssApp extends Application {

    private static final StringProperty centerStyle = new SimpleStringProperty();
    private static final ObservableList<ReadOnlyProperty<?>> props = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(buildView(), 1200, 500);

        props.add(scene.heightProperty());
        props.add(scene.widthProperty());

//        scene.getStylesheets().add(PlaygroundCssApp.class.getResource("app.css").toExternalForm());
        stage.setTitle("Hello!");
//        stage.setAlwaysOnTop(true);
        stage.setX(2150);
        stage.setY(160);
        stage.setScene(scene);
        stage.show();

        props.add(stage.xProperty());
        props.add(stage.yProperty());

    }

    public Group buildGroup() {
        return new Group();
    }

    public BorderPane buildView() {
        //border panes are neat
        BorderPane borderPane = new BorderPane();
        // it has a left
        ButtonBar buttonBar = new ButtonBar(100);
        buttonBar.addButton("reload", e -> reloadCss(borderPane));
        buttonBar.addButton("clear", e -> clearCss(borderPane));
        borderPane.setLeft(buttonBar);
        // right
        StyleEdit styleEdit = new StyleEdit("app.css");
        centerStyle.bindBidirectional(styleEdit.textProperty());
        borderPane.setRight(styleEdit);
        // and center
        CenterDisplay centerDisplay = new CenterDisplay(5.0);
        centerDisplay.styleProperty().bind(centerStyle);
        borderPane.setCenter(centerDisplay);
        // plus a lil bottom
        borderPane.setBottom(buildLeft());

        // other bindings here


        return borderPane;
    }

    private void clearCss(Region parent) {
        parent.getScene().getStylesheets().clear();
    }

    private void reloadCss(Region parent) {
        parent.getScene().getStylesheets().clear();
        parent.getScene().getStylesheets().add(PlaygroundCssApp.class.getResource("app.css").toExternalForm());
    }

    private Node buildLeft() {
        TableView<ReadOnlyProperty<?>> tableView = new TableView<>();
        tableView.setItems(props);
        tableView.setPrefHeight(200);

        TableColumn<ReadOnlyProperty<?>, String> beanCol = new TableColumn<>("Bean");
        beanCol.setCellValueFactory(cell -> Bindings.createStringBinding(() -> cell.getValue().getBean().toString(), cell.getValue()));
        tableView.getColumns().add(beanCol);

        TableColumn<ReadOnlyProperty<?>, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> Bindings.createStringBinding(() -> cell.getValue().getName(), cell.getValue()));
        tableView.getColumns().add(nameCol);

        TableColumn<ReadOnlyProperty<?>, String> valCol = new TableColumn<>("Val");
        valCol.setCellValueFactory(cell -> Bindings.createStringBinding(cell.getValue().getValue()::toString, cell.getValue()));
        tableView.getColumns().add(valCol);

        TableColumn<ReadOnlyProperty<?>, String> hashCol = new TableColumn<>("Prop Hash");
        hashCol.setCellValueFactory(cell -> Bindings.createStringBinding(() -> Integer.toHexString(cell.getValue().hashCode()), cell.getValue()));
        tableView.getColumns().add(hashCol);
        hashCol.setVisible(false);

        tableView.setTableMenuButtonVisible(true);
        return tableView;
    }

    public Node buildBottom(Region parent) {
        StackPane stackPane = new StackPane();

        Rectangle rectangle = new Rectangle();
        rectangle.widthProperty().bind(parent.widthProperty());
        rectangle.setHeight(300);
        stackPane.getChildren().add(rectangle);

        Button one = new Button("reload");
        one.setOnAction(e -> reloadCss(parent));

        Button two = new Button("clear");
        two.setOnAction(e -> clearCss(parent));

        HBox buttonBox = new HBox(8.0);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(one, two);

        stackPane.getChildren().add(buttonBox);
        StackPane.setAlignment(buttonBox, Pos.CENTER);
        return stackPane;
    }

    public static void main(String[] args) {
        launch();
    }
}