package views;

import database.Database;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Student;
import model.StudentGroup;

public class FormFields {
    TextField username;
    PasswordField passwordField;
    TextField firstNameTxtField;
    TextField lastNameTxtField;
    ToggleButton addStudentBtn;
    ToggleButton cancelBtn;
    DatePicker datePicker;
    ComboBox<String> comboBox;
    GridPane gridPane;
    HBox hBox;
    VBox vBox;
    Database database;

    public FormFields(Database database) {
        this.database = database;
        username = new TextField();
        passwordField = new PasswordField();
        firstNameTxtField = new TextField();
        lastNameTxtField = new TextField();
        datePicker = new DatePicker();
        vBox = new VBox(50);
        textPrompts();
        comboBox();
        cancelBtn();
        addStudentBtn();
        hBox();
        gridPane();
        toggleGroup();
    }

    private void toggleGroup(){
        ToggleGroup toggleGroup = new ToggleGroup();
        addStudentBtn.setToggleGroup(toggleGroup);
        cancelBtn.setToggleGroup(toggleGroup);
    }

    private void textPrompts(){
        firstNameTxtField.setPromptText("First name");
        passwordField.setPromptText("Password");
        username.setPromptText("Username");
        lastNameTxtField.setPromptText("Last name");
        datePicker.setPromptText("Date of birth");
    }

    private void comboBox(){
        comboBox = new ComboBox<>();
        comboBox.getItems().addAll(StudentGroup.INFO2A.toString(), StudentGroup.INFO2B.toString());
        comboBox.setPromptText("Select group");
        comboBox.setMinWidth(120);
    }

    private void cancelBtn() {
        cancelBtn = new ToggleButton("Cancel");
        cancelBtn.getStyleClass().add("grey-btn");
        cancelBtn.setMinWidth(100);
    }

    private void addStudentBtn(){
        addStudentBtn = new ToggleButton("Add student");
        addStudentBtn.getStyleClass().add("blue-btn");
        addStudentBtn.setMinWidth(100);
    }


    private void hBox() {
        hBox = new HBox(20);
        hBox.getChildren().addAll(addStudentBtn, cancelBtn);
    }

    private void gridPane() {
        gridPane = new GridPane();
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.add(username, 0, 0);
        gridPane.add(passwordField, 0, 1);
        gridPane.add(firstNameTxtField, 0, 2);
        gridPane.add(lastNameTxtField, 0, 3);
        gridPane.add(comboBox, 0, 4);
        gridPane.add(datePicker, 1, 0);
    }

    public VBox formFields() {
        vBox.getChildren().addAll(gridPane, hBox);
        return vBox;
    }
    
}
