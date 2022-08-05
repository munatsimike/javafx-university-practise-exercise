package views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import model.ButtonText;
import model.MenuOption;

import java.util.List;

public class FormMenuOptions {

    ToggleButton addButton;
    ToggleButton editButton;
    ToggleButton deleteButton;
    ToggleGroup toggleGroup;
    List<ToggleButton> toggleButtons;
    StringProperty selectedFormBtn;
    HBox editDeleteAddBtnHbox;

    public FormMenuOptions() {
        addButton = new ToggleButton();
        editButton = new ToggleButton();
        deleteButton = new ToggleButton();
        selectedFormBtn = new SimpleStringProperty("");
        toggleButtons = List.of(addButton, editButton, deleteButton);
        setEditDeleteAddBtnhbox();
        toggleGroup();
        setBtnBackgroundColor();
        btnHandler();
    }

    private void setBtnBackgroundColor() {
        editButton.getStyleClass().add("grey-btn");
        addButton.getStyleClass().add("blue-btn");
        deleteButton.getStyleClass().add("red-btn");
    }

    private void toggleGroup() {
        toggleGroup = new ToggleGroup();
        for (ToggleButton toggle : toggleButtons) {
            toggle.setToggleGroup(toggleGroup);
            toggle.setUserData(toggle);
        }
    }

    // add delete, edit and add button to hBox
    private void setEditDeleteAddBtnhbox() {
        editDeleteAddBtnHbox = new HBox();
        editDeleteAddBtnHbox.setSpacing(25);
        editDeleteAddBtnHbox.getChildren().addAll(addButton, editButton, deleteButton);
        isVisible(false);
    }

    public HBox getFromOptions() {
        return editDeleteAddBtnHbox;
    }

    // set add , delete and edit button text
    public void setBtnTex(MenuOption selectedBtn) {
        if (selectedBtn.equals(MenuOption.STUDENTS)) {
            addButton.setText(ButtonText.ADD_STUDENTS.toString());
            editButton.setText(ButtonText.EDIT_STUDENTS.toString());
            deleteButton.setText(ButtonText.DELETE_STUDENTS.toString());
        } else if (selectedBtn.equals(MenuOption.TEACHERS)) {
            addButton.setText(ButtonText.ADD_TEACHERS.toString());
            editButton.setText(ButtonText.EDIT_TEACHERS.toString());
            deleteButton.setText(ButtonText.DELETE_TEACHERS.toString());
        } else {
            isVisible(false);
        }
    }

    // delete add and edit button click handler
    private void btnHandler() {
        for (ToggleButton toggleButton : toggleButtons) {
            toggleButton.setOnMouseClicked(mouseEvent -> {
                String btnText = toggleButton.textProperty().get();
                selectedFormBtn.set(btnText);
            });
        }
    }

    // return selected button: delete add and edit
    public StringProperty getSelectedFormBtn() {
        return selectedFormBtn;
    }

    // show or hide add, edit and delete buttons
    public void isVisible(Boolean visibility) {
        if (visibility && !editDeleteAddBtnHbox.isVisible()) {
            editDeleteAddBtnHbox.setVisible(true);
            selectedFormBtn.set("");
        } else if (!visibility && editDeleteAddBtnHbox.isVisible()) {
            editDeleteAddBtnHbox.setVisible(false);
        }
    }

    public void resetToggle() {

        toggleGroup.getSelectedToggle().setSelected(false);

    }
}
