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

public class PlaygroundCssApp extends Application {

    private static final StringProperty centerStyle = new SimpleStringProperty();
    private static final ObservableList<ReadOnlyProperty<?>> props = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(buildView(), 1200, 365);

        props.add(scene.heightProperty());
        props.add(scene.widthProperty());

//        scene.getStylesheets().add(PlaygroundCssApp.class.getResource("app.css").toExternalForm());
        stage.setTitle("Hello!");
//        stage.setAlwaysOnTop(true);
        stage.setX(45);
        stage.setY(170);
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
        borderPane.setLeft(buildLeft());
        borderPane.setRight(buildRight());
        borderPane.setCenter(new CenterDisplay(5.0));
        borderPane.setBottom(buildBottom(borderPane));

        borderPane.getCenter().styleProperty().bind(centerStyle);

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

        TableColumn<ReadOnlyProperty<?>, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> Bindings.createStringBinding(() -> cell.getValue().getName(), cell.getValue()));
        tableView.getColumns().add(nameCol);

        TableColumn<ReadOnlyProperty<?>, String> valCol = new TableColumn<>("Val");
        valCol.setCellValueFactory(cell -> Bindings.createStringBinding(cell.getValue().getValue()::toString, cell.getValue()));
        tableView.getColumns().add(valCol);

        TableColumn<ReadOnlyProperty<?>, String> beanCol = new TableColumn<>("Bean");
        beanCol.setCellValueFactory(cell -> Bindings.createStringBinding(() -> cell.getValue().getBean().toString(), cell.getValue()));
        tableView.getColumns().add(beanCol);

        TableColumn<ReadOnlyProperty<?>, String> hashCol = new TableColumn<>("Prop Hash");
        hashCol.setCellValueFactory(cell -> Bindings.createStringBinding(() -> Integer.toHexString(cell.getValue().hashCode()), cell.getValue()));
        tableView.getColumns().add(hashCol);
        hashCol.setVisible(false);

        tableView.setTableMenuButtonVisible(true);
        return tableView;
    }

    private Node buildRight() {
        TextArea textArea = new TextArea();
        StringJoiner stringJoiner = new StringJoiner("\n");
        try {
            Files.readAllLines(Path.of(PlaygroundCssApp.class.getResource("app.css").getFile())).forEach(stringJoiner::add);
        } catch (IOException e) {
            Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).forEach(stringJoiner::add);
        }
        textArea.setText(stringJoiner.toString());
        centerStyle.bindBidirectional(textArea.textProperty());
        return textArea;
    }

    public Node buildBottom(Region parent) {
        StackPane stackPane = new StackPane();

        Rectangle rectangle = new Rectangle();
        rectangle.widthProperty().bind(parent.widthProperty());
        rectangle.setHeight(100);
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