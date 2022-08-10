package views;

import database.Database;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
    LoginScreen screen;
    String loggedInUser;
    Stage stage;
    MenuBuilder menuBuilder;
    Database database;
    Label headingLabel;
    TableViewBuilder tableViewBuilder;
    VBox vBox;
    TableView<AcademicPerson> students;
    TableView<AcademicPerson> teachers;
    Pane tableViewPane;
    FormMenuOptions formMenuOptions;
    StudentTeacherForm studentTeacherForm;
    MyAlert myAlert;

    public MainWindow(LoginScreen screen, String loggedInUser) {
        this.screen = screen;
        this.loggedInUser = loggedInUser;
        tableViewBuilder = new TableViewBuilder();
        menuBuilder = new MenuBuilder();
        database = new Database();
        headingLabel = new Label();
        formMenuOptions = new FormMenuOptions();
        myAlert = new MyAlert();
        studentTeacherForm = new StudentTeacherForm(database);
        pane();
        vBox();
        menuSelectedBtn();
        formMenuOptionChangeListener();
        cancelBtnChangeListener();
        alertStatusEventHandler();
    }

    private void pane() {
        tableViewPane = new Pane();
        tableViewPane.setMinWidth(800);
    }

    private void vBox() {
        vBox = new VBox(25);
        vBox.setPadding(new Insets(20));
        vBox.setStyle("-fx-background-color: white");
    }

    private void menuSelectedBtn() {
        menuBuilder.selectedBtn().addListener((observableValue, s, t1) -> {
            if (s != null) {
                showContent(MenuOption.valueOf(t1.toUpperCase()));
                formMenuOptions.setBtnTex(MenuOption.valueOf(t1.toUpperCase()));
            }
        });
    }

    // listen to delete, edit and add button clicks
    private void formMenuOptionChangeListener() {
        formMenuOptions.getPressedBtn().addListener((observable, oldValue, newValue) -> {
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
                        tableViewPane.getChildren().add(studentTeacherForm.getAddEditTeacherStudentForm());
                        // hide formOptions edit delete and add buttons
                        formMenuOptions.isVisible(false);
                        // clear table selection
                        tableViewBuilder.clearTableSelection();

                    }
                } else {
                    // clear parent panel
                    clearPane();
                    // set heading: add students
                    headingLabel.setText(newValue);
                    // hide formOptions edit delete and add buttons
                    formMenuOptions.isVisible(false);
                    // set button text to edit or add
                    studentTeacherForm.setAddEditBtnTxt(newValue.substring(0, newValue.length() - 1));
                    // get form
                    tableViewPane.getChildren().add(studentTeacherForm.getAddEditTeacherStudentForm());
                }
            } else if ((newValue.equals(ButtonText.DELETE_STUDENTS.toString()) || newValue.equals(ButtonText.DELETE_TEACHERS.toString())) && tableViewBuilder.isRowSelected()) {
                AcademicPerson person = tableViewBuilder.getSelectedIPerson();
                // delete person
                myAlert.confirmationAlert("Are you sure you want to delete: " + person.getFirstName() + " " + person.getLastName())
                        .showAndWait()
                        .filter(response -> Objects.equals(response.getText(), "Delete"))
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
        vBox.getChildren().addAll(headingLabel, tableViewPane, formMenuOptions.getFromOptions());
        Window window = new Window(new HBox(menuBuilder.getMenu(), vBox));
        stage = window.getWindow();
        stage.setHeight(600);
        stage.setWidth(1024);
        stage.initStyle(StageStyle.UNDECORATED);
        showLoginScreen();
        return stage;
    }

    private TableView<AcademicPerson> tableView(PersonType person) {

        if (person == PersonType.STUDENT) {
            students = tableViewBuilder.getTable(person);
            students.setItems(database.getPersons(person));
        } else {
            teachers = tableViewBuilder.getTable(person);
            teachers.setItems(database.getPersons(person));
        }
        // display form menu buttons if not visible
        formMenuOptions.isVisible(true);

        return (person == PersonType.STUDENT) ? students : teachers;
    }

    private void showLoginScreen() {
        stage.setOnCloseRequest(e -> screen.stage.show());
    }

    private void showContent(MenuOption option) {
        if (option == MenuOption.DASHBOARD) {
            setHeaderLabelTxt(WELCOME_NOTE + loggedInUser);
            clearPane();
        } else if (option == MenuOption.STUDENTS) {
            setHeaderLabelTxt(MenuOption.STUDENTS.toString());
            clearPane();
            // add a child
            tableViewPane.getChildren().add(tableView(PersonType.STUDENT));
        } else {
            setHeaderLabelTxt(MenuOption.TEACHERS.toString());
            clearPane();
            // add a child
            tableViewPane.getChildren().add(tableView(PersonType.TEACHER));
        }
    }

    private void clearPane() {
        if (tableViewPane.getChildren().size() > 0) {
            tableViewPane.getChildren().clear();
        }
    }

    private void setHeaderLabelTxt(String txt) {
        headingLabel.setText(txt);
        headingLabel.setFont(Font.font("Arial", FontPosture.REGULAR, 30));
        headingLabel.setPadding(new Insets(0, 0, 25, 0));
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