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
import model.MenuOption;
import model.Person;
import model.PersonType;

public class MainWindow {
    final static String WELCOME_NOTE = "Welcome ";
    LoginScreen screen;
    String loggedInUser;
    Stage stage;
    MenuBuilder menuBuilder;
    Database database;
    Label label;
    TableViewBuilder tableViewBuilder;
    VBox vBox;
    TableView<Person> students;
    TableView<Person> teachers;
    Pane tableViewPane;
    FormBtnOptions formBtnOptions;
    FormFields formFields;

    public MainWindow(LoginScreen screen, String loggedInUser) {
        this.screen = screen;
        this.loggedInUser = loggedInUser;
        tableViewBuilder = new TableViewBuilder();
        menuBuilder = new MenuBuilder();
        database = new Database();
        label = new Label();
        formBtnOptions = new FormBtnOptions();
        formFields = new FormFields(database);
        pane();
        vBox();
        menuSelectedBtn();
        formSelectedButton();
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
                formBtnOptions.setBtnTex(MenuOption.valueOf(t1.toUpperCase()));
            }
        });
    }

    private void formSelectedButton() {
        formBtnOptions.getSelectedFormBtn().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Add Students")) {
                clearPane();
                formBtnOptions.getFrom().setVisible(false);
                tableViewPane.getChildren().add(formFields.formFields());
            }
        });
    }

    public Stage getMainScreen() {
        setHeaderLabelTxt(WELCOME_NOTE + loggedInUser);
        vBox.getChildren().addAll(label, tableViewPane, formBtnOptions.getFrom());
        Window window = new Window(new HBox(menuBuilder.getMenu(), vBox));
        stage = window.getWindow();
        stage.setHeight(600);
        stage.setWidth(1024);
        stage.initStyle(StageStyle.UNDECORATED);
        showLoginScreen();
        return stage;
    }

    private TableView<Person> tableView(PersonType person) {
        if (person == PersonType.STUDENT) {
            students = tableViewBuilder.getTable(person);
            students.setItems(database.getPersons(person));
        } else {
            teachers = tableViewBuilder.getTable(person);
            teachers.setItems(database.getPersons(person));
        }

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
        label.setText(txt);
        label.setFont(Font.font("Arial", FontPosture.REGULAR, 30));
        label.setPadding(new Insets(0, 0, 25, 0));
    }
}