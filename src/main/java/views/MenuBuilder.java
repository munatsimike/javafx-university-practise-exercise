package views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuBuilder {
    VBox vBox;
    ToggleButton dashboard;
    ToggleButton students;
    ToggleButton teachers;
    StringProperty selectedBtn;

    public MenuBuilder() {
        vBox = new VBox(20);
        selectedBtn = new SimpleStringProperty();
        menuBtn();
        toggle();
        btnHandler();
    }

    private void menuBtn() {
        dashboard = new ToggleButton("Dashboard");
        students = new ToggleButton("Students");
        teachers = new ToggleButton("Teachers");
    }

    public VBox getMenu() {
        vBox.setPrefWidth(175);
        vBox.getStyleClass().add("bleu-background");
        vBox.setAlignment(Pos.BASELINE_CENTER);
        vBox.getChildren().addAll(logoTitle(), dashboard, students, teachers);
        return vBox;
    }

    private void toggle() {
        ToggleGroup toggleGroup = new ToggleGroup();
        dashboard.setToggleGroup(toggleGroup);
        students.setToggleGroup(toggleGroup);
        teachers.setToggleGroup(toggleGroup);
    }

    private ImageView logo(int widthHeight, String path) {
        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(widthHeight);
        imageView.setFitWidth(widthHeight);
        return imageView;
    }

    private HBox logoTitle() {
        int spacing = 15;
        HBox hBox = new HBox(10);
        hBox.setPadding(new Insets(spacing, spacing, spacing, spacing));
        Label label = new Label("University Project");
        label.setWrapText(true);
        label.getStyleClass().add("menu-logo");
        hBox.getChildren().addAll(logo(50, "img/university.png"), label);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private void btnHandler() {
        dashboard.setOnAction(e -> selectedBtn.set(dashboard.textProperty().get()));
        teachers.setOnAction(e -> selectedBtn.set(teachers.textProperty().get()));
        students.setOnAction(e -> selectedBtn.set(students.textProperty().get()));
    }

    public StringProperty selectedBtn() {
        return selectedBtn;
    }
}
