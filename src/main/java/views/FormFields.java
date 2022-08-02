package views;

import database.Database;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Student;
import model.StudentGroup;

import java.sql.Date;
import java.util.List;

public class FormFields {
    TextField username;
    PasswordField passwordField;
    TextField firstNameTxtField;
    TextField lastNameTxtField;
    ToggleButton addStudentBtn;
    ToggleButton cancelBtn;
    DatePicker birthdayDatePicker;
    ComboBox<String> studentGroupChoiceBox;
    GridPane gridPane;
    HBox hBox;
    VBox vBox;
    Database database;
    BooleanProperty iSelected;
    List<TextField> textFields;
    MyAlert myAlert;
    Label errorLabel;

    public FormFields(Database database) {
        this.database = database;

        iSelected = new SimpleBooleanProperty(false);
        username = new TextField();
        passwordField = new PasswordField();
        firstNameTxtField = new TextField();
        lastNameTxtField = new TextField();
        birthdayDatePicker = new DatePicker();
        textPrompts();
        comboBox();
        cancelBtn();
        addStudentBtn();
        hBox();
        errorLabel();
        gridPane();
        toggleGroup();
        addStudentBtnHandler();
        cancelBtnHandler();
        vBox = new VBox(50);
        vBox.getChildren().addAll(gridPane, hBox);
        textFields = List.of(username, passwordField, firstNameTxtField, lastNameTxtField);
        textFieldFocusChangeListener();
    }

    private void errorLabel() {
        errorLabel = new Label("");
        errorLabel.setVisible(false);
        errorLabel.setStyle("-fx-text-fill: red");
    }

    private void toggleGroup() {
        ToggleGroup toggleGroup = new ToggleGroup();
        addStudentBtn.setToggleGroup(toggleGroup);
        cancelBtn.setToggleGroup(toggleGroup);
    }

    private void textPrompts() {
        firstNameTxtField.setPromptText("First name");
        passwordField.setPromptText("Password");
        username.setPromptText("Username");
        lastNameTxtField.setPromptText("Last name");
        birthdayDatePicker.setPromptText("Date of birth");
    }

    private void comboBox() {
        studentGroupChoiceBox = new ComboBox<>();
        studentGroupChoiceBox.getItems().addAll(StudentGroup.INFO2A.toString(), StudentGroup.INFO2B.toString());
        studentGroupChoiceBox.setPromptText("Select group");
        studentGroupChoiceBox.setMinWidth(120);
    }

    private void cancelBtn() {
        cancelBtn = new ToggleButton("Cancel");
        cancelBtn.getStyleClass().add("grey-btn");
        cancelBtn.setMinWidth(100);
    }

    private void addStudentBtn() {
        addStudentBtn = new ToggleButton("Add student");
        addStudentBtn.getStyleClass().add("blue-btn");
        addStudentBtn.setMinWidth(100);
    }

    private void hBox() {
        hBox = new HBox(22);
        hBox.getChildren().addAll(addStudentBtn, cancelBtn);
    }

    private void gridPane() {
        gridPane = new GridPane();
        gridPane.setHgap(21);
        gridPane.setVgap(21);
        gridPane.add(errorLabel, 0, 0, 2, 1);
        gridPane.add(username, 0, 1);
        gridPane.add(passwordField, 0, 2);
        gridPane.add(firstNameTxtField, 0, 3);
        gridPane.add(lastNameTxtField, 0, 4);
        gridPane.add(studentGroupChoiceBox, 0, 5);
        gridPane.add(birthdayDatePicker, 1, 1);
    }

    public VBox formFields() {
        clearForm();
        return vBox;
    }

    // listen to add button clicks
    private void addStudentBtnHandler() {
        addStudentBtn.setOnMouseClicked(event -> {
            if (errorLabel.isVisible() || !isFieldBlank()) {
                errorLabel.setText("All fields are mandatory");
                if (!errorLabel.isVisible())
                    errorLabel.setVisible(true);
                return;
            }

            Student student = new Student(database.getId(), username.getText(), passwordField.getText(), firstNameTxtField.getText(), lastNameTxtField.getText(), Date.valueOf(birthdayDatePicker.getValue()), studentGroupChoiceBox.getValue());
            database.addPerson(student);
            confirmationAlert();
        });
    }

    // check if fields are blank
    private boolean isFieldBlank() {
        boolean isValid = true;
        for (TextField field : textFields) {
            if (field.getText().length() == 0) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    // listen to cancel button clicks
    private void cancelBtnHandler() {
        cancelBtn.setOnMouseClicked(event -> {
            iSelected.set(true);
            iSelected.set(false);
        });
    }

    public BooleanProperty cancelBtnIsSelected() {
        return iSelected;
    }

    private void textFieldFocusChangeListener() {
        for (TextField field : textFields) {
            field.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
                if (!t1) {
                    if (field instanceof PasswordField && field.getText().length() < 8) {
                        // set invalid password field and error message and border
                        if (!errorLabel.isVisible()) {
                            errorLabel.setText("Invalid password length, length should be greater than 8");
                            field.getStyleClass().add("red-border");
                            errorLabelVisibility();
                        }
                    } else if (field.getText().length() < 4) {
                        if (!errorLabel.isVisible()) {
                            // set invalid text field border and error message
                            errorLabel.setText("Text length should be greater the 4");
                            field.getStyleClass().add("red-border");
                            errorLabelVisibility();
                        }
                    } else {
                        // set valid field border
                        field.getStyleClass().add("green-border");
                        if (errorLabel.isVisible()) {
                            errorLabel.setVisible(false);
                        }
                    }
                }
            });
        }
    }

    // create confirmation alert
    private void confirmationAlert() {
        myAlert = new MyAlert(Alert.AlertType.CONFIRMATION, "Student successfully saved");
        alertStatus();
        myAlert.showAlert();
    }

    // set error label visibility
    private void errorLabelVisibility() {
        errorLabel.setVisible(!errorLabel.isVisible());
    }

    // confirmation alert listener
    private void alertStatus() {
        myAlert.alertStatus().addListener((observableValue, s, t1) -> {
            if (t1) {
                clearForm();
            }
        });
    }

    // clear form fields
    private void clearForm() {
        // reset text fields
        for (TextField field : textFields) {
            if (field.getText().length() > 0) {
                field.setText("");
            }
            field.getStyleClass().remove("red-border");
            field.getStyleClass().remove("green-border");
        }

        //reset date picker
        if (studentGroupChoiceBox.getValue() != null)
            studentGroupChoiceBox.setValue(null);

        //clear error label
        if (errorLabel.isVisible()) {
            errorLabel.setText("");
            errorLabel.setVisible(false);
        }

        //reset date picker
        resetComboboxToPromptText(studentGroupChoiceBox);

    }

    private void resetComboboxToPromptText(ComboBox<String> comboBox) {
        if (comboBox.getValue() != null) {
            comboBox.getSelectionModel().clearSelection();
            comboBox.setButtonCell(new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(comboBox.getPromptText());
                    } else {
                        setText(item);
                    }
                }
            });
        }
    }
}
