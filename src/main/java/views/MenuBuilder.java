package views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MenuBuilder {
    VBox vBox;
    ToggleButton dashboard;
    ToggleButton students;
    ToggleButton teachers;

    public MenuBuilder() {
        vBox = new VBox(20);
    }

    public VBox getMenu() {
        vBox.setPrefWidth(175);
        vBox.getStyleClass().add("bleu-background");
        vBox.setAlignment(Pos.BASELINE_CENTER);

        menuBtn();
        return vBox;
    }

    private void menuBtn() {
        dashboard = new ToggleButton("Dashboard");
        students = new ToggleButton("Students");
        teachers = new ToggleButton("Teachers");

        toggle();
        dashboardBtnHandler();
        studentsBtnHandler();
        teachersBtnHandler();
        dashboard.setSelected(true);

        vBox.getChildren().addAll(logoTitle(), dashboard, students, teachers);
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

    private void dashboardBtnHandler() {
        dashboard.setOnMouseClicked(e -> {
            System.out.println("dashboard");
        });
    }

    private void studentsBtnHandler() {
        students.setOnMouseClicked(e -> {
            System.out.println("students");
        });
    }

    private void teachersBtnHandler() {
        teachers.setOnMouseClicked(e -> {
            System.out.println("teachers");
        });
    }
}
