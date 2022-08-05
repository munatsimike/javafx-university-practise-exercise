package views;

import database.Database;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;

import java.util.List;

public class StudentTeacherForm {
    TextField username;
    PasswordField passwordField;
    TextField firstNameTxtField;
    TextField lastNameTxtField;
    ToggleButton addEditBtn;
    FormMenuOptions formMenuOptions;
    ToggleButton cancelBtn;
    DatePicker birthdayDatePicker;
    ComboBox<String> studentGroupChoiceBox;
    GridPane gridPane;
    AcademicPerson oldPerson;
    HBox hBox;
    VBox vBox;
    Database database;
    BooleanProperty iSelected;
    List<TextField> textFields;
    MyAlert myAlert;
    Label errorLabel;

    public StudentTeacherForm(Database database, MyAlert myAlert) {
        this.database = database;
        this.myAlert = myAlert;
        assert false;
        formMenuOptions = new FormMenuOptions();
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
        addEditBtn.setToggleGroup(toggleGroup);
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
        cancelBtn = new ToggleButton(ButtonText.Cancel.toString());
        cancelBtn.getStyleClass().add("grey-btn");
        cancelBtn.setMinWidth(100);
    }

    private void addStudentBtn() {
        addEditBtn = new ToggleButton();
        addEditBtn.getStyleClass().add("blue-btn");
        addEditBtn.setMinWidth(100);
    }

    public void setAddEditBtnTxt(String txt) {
        addEditBtn.setText(txt);
    }

    private void hBox() {
        hBox = new HBox(22);
        hBox.getChildren().addAll(addEditBtn, cancelBtn);
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

    public VBox getAddEditTeacherStudentForm() {
        if (addEditBtn.textProperty().get().equals("Add Student")) {
            clearForm();
        }
        return vBox;
    }

    // fill form with person details
    public void fillForm(AcademicPerson person) {
        oldPerson = person;
        username.setText(person.getUserName());
        passwordField.setText(person.getPassword());
        firstNameTxtField.setText(person.getFirstName());
        lastNameTxtField.setText(person.getLastName());
        birthdayDatePicker.setValue(person.getBirth_date());

        if (person instanceof Student) {
            Student student = (Student) person;
            studentGroupChoiceBox.setValue(student.getGroup());
        } else {
            Teacher teacher = (Teacher) person;
        }
    }

    // listen to add edit, button clicks
    private void addStudentBtnHandler() {
        addEditBtn.setOnMouseClicked(event -> {
            Alert alert = myAlert.getAlert();
            if (errorLabel.isVisible() || !isFieldBlank()) {
                errorLabel.setText("All fields are mandatory");
                if (!errorLabel.isVisible())
                    errorLabel.setVisible(true);
                return;
            }

            Student student = new Student(database.getId(), username.getText(), passwordField.getText(), firstNameTxtField.getText(), lastNameTxtField.getText(), birthdayDatePicker.getValue(), studentGroupChoiceBox.getValue());
            // edit person
            if (oldPerson != null) {
                student.setId(oldPerson.getId());
                // check if person is the same
                if (!oldPerson.isSamePerson(oldPerson, student)) {
                    database.editPerson(student);
                    oldPerson = null;
                    alert.setContentText("Student successfully edited");
                    alert.showAndWait();

                }
                return;
            }

            // add person
            database.addPerson(student);
            alert.setContentText("Student successfully saved");
            alert.showAndWait();
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

    // set error label visibility
    private void errorLabelVisibility() {
        errorLabel.setVisible(!errorLabel.isVisible());
    }

    // clear form fields
    public void clearForm() {
        // reset text fields
        for (TextField field : textFields) {
            if (field.getText().length() > 0) {
                field.setText("");
            }
            field.getStyleClass().remove("red-border");
            field.getStyleClass().remove("green-border");
        }

        //reset date picker
        if (birthdayDatePicker.getValue() != null)
            birthdayDatePicker.setValue(null);

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
