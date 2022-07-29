package views;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Person;

public class TableViewBuilder {
    TableView<Person> tableView;

    public TableViewBuilder() {
        tableView = new TableView<>();
        TableColumn<Person, String> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Person, String> birthdate = new TableColumn<>("Birthdate");
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birth_date"));
    }

    public TableView<Person> getTable() {
        return tableView;
    }

}
