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
import model.MenuOption;

public class MenuBuilder {
    VBox menuItemsContainervBox;
    ToggleButton dashboard;
    ToggleButton students;
    ToggleButton teachers;
    StringProperty selectedBtn;

    public MenuBuilder() {
        menuItemsContainervBox = new VBox(20);
        selectedBtn = new SimpleStringProperty("");
        menuBtn();
        toggle();
        btnHandler();
    }

    private void menuBtn() {
        dashboard = new ToggleButton(MenuOption.DASHBOARD.toString());
        students = new ToggleButton(MenuOption.STUDENTS.toString());
        teachers = new ToggleButton(MenuOption.TEACHERS.toString());
        dashboard.setSelected(true);
    }

    public VBox getMenu() {
        menuItemsContainervBox.setPrefWidth(175);
        menuItemsContainervBox.getStyleClass().add("sky-blue-background");
        menuItemsContainervBox.setAlignment(Pos.BASELINE_CENTER);
        if (menuItemsContainervBox.getChildren().size() == 0)
            menuItemsContainervBox.getChildren().addAll(logoTitle(), dashboard, students, teachers);
        return menuItemsContainervBox;
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
