package views;

import database.Database;
import enums.ButtonText;
import enums.MenuOption;
import enums.PersonType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.*;

import java.util.Objects;

public class MainWindow {
    final static String WELCOME_NOTE = "Welcome ";
    LoginScreen loginScreen;
    String loggedInUser;
    Stage stage;
    MenuBuilder menuBuilder;
    Database database;
    Button logoutBtn;
    Label headingLabel;
    TableViewBuilder tableViewBuilder;
    VBox mainWindowContainervBox;
    VBox studentsTableViewContainer;
    VBox teachersTableViewContainer;
    Pane mainWindowTableViewContainer;
    FormMenuOptions deleteEditAddBtn;
    StudentTeacherForm studentTeacherForm;
    MyAlert myAlert;

    public MainWindow(LoginScreen screen, String loggedInUser) {
        loginScreen = screen;
        this.loggedInUser = loggedInUser;
        tableViewBuilder = new TableViewBuilder();
        menuBuilder = new MenuBuilder();
        database = new Database();
        headingLabel = new Label();
        logoutBtn = new Button("Logout");
        deleteEditAddBtn = new FormMenuOptions();
        myAlert = new MyAlert();
        studentTeacherForm = new StudentTeacherForm(database);
        pane();
        vBox();
        menuSelectedBtn();
        formMenuOptionChangeListener();
        cancelBtnChangeListener();
        alertStatusEventHandler();
        logoutBtnClickListener();

    }

    private void pane() {
        mainWindowTableViewContainer = new Pane();
        mainWindowTableViewContainer.setMinWidth(800);
    }

    private void vBox() {
        mainWindowContainervBox = new VBox(25);
        mainWindowContainervBox.setPadding(new Insets(20));
        mainWindowContainervBox.setStyle("-fx-background-color: white");
    }

    private void menuSelectedBtn() {
        menuBuilder.selectedBtn().addListener((observableValue, s, t1) -> {
            if (s != null) {
                showContent(MenuOption.valueOf(t1.toUpperCase()));
                deleteEditAddBtn.setBtnTex(MenuOption.valueOf(t1.toUpperCase()));
            }
        });
    }

    // listen to delete, edit and add button clicks
    private void formMenuOptionChangeListener() {
        deleteEditAddBtn.getPressedBtn().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(ButtonText.ADD_STUDENTS.toString()) || newValue.equals(ButtonText.EDIT_STUDENTS.toString())) {
                if (newValue.equals(ButtonText.EDIT_STUDENTS.toString())) {
                    if (tableViewBuilder.isRowSelected()) {
                        // set heading to edit students
                        headingLabel.setText(newValue);
                        clearPane();
                        // fill form with selected row
                        studentTeacherForm.fillForm(tableViewBuilder.getSelectedIPerson());
                        // set button text to edit or add
                        studentTeacherForm.setAddEditBtnTxt(newValue.substring(0, newValue.length() - 1));
                        // get form
                        mainWindowTableViewContainer.getChildren().add(studentTeacherForm.getAddEditTeacherStudentForm());
                        // hide formOptions edit delete and add buttons
                        deleteEditAddBtn.setVisibility(false);
                        // clear table selection
                        tableViewBuilder.clearTableSelection();

                    }
                } else {
                    // clear parent panel
                    clearPane();
                    // set heading: add students
                    headingLabel.setText(newValue);
                    // hide formOptions edit delete and add buttons
                    deleteEditAddBtn.setVisibility(false);
                    // set button text to edit or add
                    studentTeacherForm.setAddEditBtnTxt(newValue.substring(0, newValue.length() - 1));
                    // get form
                    mainWindowTableViewContainer.getChildren().add(studentTeacherForm.getAddEditTeacherStudentForm());
                }
            } else if ((newValue.equals(ButtonText.DELETE_STUDENTS.toString()) || newValue.equals(ButtonText.DELETE_TEACHERS.toString())) && tableViewBuilder.isRowSelected()) {
                AcademicPerson person = tableViewBuilder.getSelectedIPerson();
                // delete person
                String firstBtnText = "Delete";
                myAlert.confirmationAlert(firstBtnText, "Are you sure you want to delete: " + person.getFirstName() + " " + person.getLastName())
                        .showAndWait()
                        .filter(response -> Objects.equals(response.getText(), firstBtnText))
                        .ifPresent(response -> database.deletePerson(person.getId()));

                if (person instanceof Student) {
                    tableView(PersonType.STUDENT);
                } else {
                    tableView(PersonType.TEACHER);
                }
            }
        });
    }

    public Stage getMainScreen() {
        setHeaderLabelTxt(WELCOME_NOTE + loggedInUser);
        if (mainWindowContainervBox.getChildren().size() == 0)
            mainWindowContainervBox.getChildren().addAll(loginBtnHbox(), headingLabel, mainWindowTableViewContainer, deleteEditAddBtn.getFromOptions());
        Window window = new Window(new HBox(menuBuilder.getMenu(), mainWindowContainervBox));
        stage = window.getWindow();
        stage.setHeight(680);
        stage.setWidth(1024);
        stage.initStyle(StageStyle.UNDECORATED);
        showLoginScreen();
        return stage;
    }

    private GridPane loginBtnHbox() {
        logoutBtn.getStyleClass().add("logout-btn");
        GridPane loginBtnContainer = new GridPane();
        loginBtnContainer.addRow(0, logoutBtn);
        loginBtnContainer.setAlignment(Pos.TOP_RIGHT);
        return loginBtnContainer;
    }

    private void logoutBtnClickListener() {
        logoutBtn.setOnMouseClicked(mouseEvent -> {
            String firstBtnText = "Logout";
            myAlert.confirmationAlert(firstBtnText, "Are you sure you want to logout")
                    .showAndWait()
                    .filter(response -> Objects.equals(response.getText(), firstBtnText))
                    .ifPresent(response -> {
                        loginScreen.stage.show();
                        this.stage.hide();
                    });
        });
    }

    private VBox tableView(PersonType person) {
        tableViewBuilder.setTableItems(database.getPersons(person));

        if (person == PersonType.STUDENT) {
            studentsTableViewContainer = tableViewBuilder.getTable(person);
        } else {
            teachersTableViewContainer = tableViewBuilder.getTable(person);
        }
        // display form menu buttons if not visible
        deleteEditAddBtn.setVisibility(true);

        return (person == PersonType.STUDENT) ? studentsTableViewContainer : teachersTableViewContainer;
    }

    private void showLoginScreen() {
        stage.setOnCloseRequest(e -> loginScreen.stage.show());
    }

    private void showContent(MenuOption option) {
        if (option == MenuOption.DASHBOARD) {
            setHeaderLabelTxt(WELCOME_NOTE + loggedInUser);
            clearPane();
        } else if (option == MenuOption.STUDENTS) {
            setHeaderLabelTxt(MenuOption.STUDENTS.toString());
            clearPane();
            // add a child
            mainWindowTableViewContainer.getChildren().add(tableView(PersonType.STUDENT));
        } else {
            setHeaderLabelTxt(MenuOption.TEACHERS.toString());
            clearPane();
            // add a child
            mainWindowTableViewContainer.getChildren().add(tableView(PersonType.TEACHER));
        }
    }

    private void clearPane() {
        if (mainWindowTableViewContainer.getChildren().size() > 0) {
            mainWindowTableViewContainer.getChildren().clear();
        }
    }

    private void setHeaderLabelTxt(String txt) {
        headingLabel.setText(txt);
        headingLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 27));
        headingLabel.setPadding(new Insets(0, 0, 18, 0));
    }

    // listen to form cancel button clicks
    private void cancelBtnChangeListener() {
        studentTeacherForm.cancelBtnIsSelected().addListener((observableValue, aBoolean, t1) -> {
            showContent(MenuOption.STUDENTS);
        });
    }

    // listen to alert showing or closing events
    private void alertStatusEventHandler() {
        myAlert.isAlertShowing().addListener((observableValue, s, t1) -> {
            // check if alert is closed
            if (t1) {
                // clear form after alert ok button has been clicked
                studentTeacherForm.clearForm();
                if (headingLabel.textProperty().get().equals(ButtonText.EDIT_STUDENTS.toString())) {
                    // show students list
                    showContent(MenuOption.STUDENTS);
                }
            }
        });
    }
}