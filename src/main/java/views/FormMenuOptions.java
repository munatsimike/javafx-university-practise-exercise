package views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import model.MenuOption;

import java.util.List;

public class FormMenuOptions {

    ToggleButton addButton;
    ToggleButton editButton;
    ToggleButton deleteButton;
    ToggleGroup toggleGroup;
    HBox hBox;
    List<ToggleButton> toggleButtons;
    StringProperty selectedFormBtn;

    public FormMenuOptions() {
        addButton = new ToggleButton("Add ");
        editButton = new ToggleButton("Edit ");
        deleteButton = new ToggleButton("Delete ");
        selectedFormBtn = new SimpleStringProperty("");
        toggleButtons = List.of(addButton, editButton, deleteButton);
        hBox();
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

    private void hBox() {
        hBox = new HBox();
        hBox.setSpacing(25);
        hBox.getChildren().addAll(addButton, editButton, deleteButton);
        isVisible(false);
    }

    public HBox getFromOptions() {
        return hBox;
    }

    public void setBtnTex(MenuOption selectedBtn) {
        if (selectedBtn.equals(MenuOption.TEACHERS)) {
            for (ToggleButton toggleButton : toggleButtons)
                toggleButton.setText(toggleButton.getText().replaceAll("\\s.*", " " + MenuOption.TEACHERS));

        } else if (selectedBtn.equals(MenuOption.STUDENTS)) {
            for (ToggleButton toggleButton : toggleButtons)
                toggleButton.setText(toggleButton.getText().replaceAll("\\s.*", " " + MenuOption.STUDENTS));

        } else {
            isVisible(false);
        }

    }

    private void btnHandler() {
        for (ToggleButton toggleButton : toggleButtons) {
            toggleButton.setOnMouseClicked(mouseEvent -> {
                selectedFormBtn.set(toggleButton.textProperty().get());
                selectedFormBtn.set("");
                if (toggleButton.textProperty().get().equals("Add Students"))
                    isVisible(false);
            });
        }
    }

    public StringProperty getSelectedFormBtn() {
        return selectedFormBtn;
    }

    public void isVisible(Boolean visibility) {
        if (visibility && !hBox.isVisible()) {
            hBox.setVisible(true);
        } else if (!visibility && hBox.isVisible()) {
            hBox.setVisible(false);
        }
    }
}
