package views;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Person;
import model.PersonType;

import java.util.Date;

public class TableViewBuilder {
    TableView<Person> tableView;

    public TableViewBuilder() {
        tableView = new TableView<>();
        tableView.setMinWidth(800);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        defaultColumns();
    }

    // default columns for students and teachers
    private void defaultColumns() {
        TableColumn<Person, Integer> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Person, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Person, Date> birthdate = new TableColumn<>("Birthdate");
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birth_date"));

        tableView.getColumns().addAll(id, firstNameColumn, lastNameColumn, birthdate);
    }


    public TableView<Person> getTable(PersonType person) {
        // add additional columns
        if (person == PersonType.STUDENT) {
            removeColumn(); // remove salary column
            TableColumn<Person, String> group = new TableColumn<>("Group");
            group.setCellValueFactory(new PropertyValueFactory<>("group"));
            // add group column for students
            tableView.getColumns().add(group);
        } else {
            removeColumn();// remove group column
            TableColumn<Person, Double> salary = new TableColumn<>("Salary");
            salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
            // add salary column for teachers
            tableView.getColumns().add(salary);
        }
        return tableView;
    }

    private void removeColumn() {
        if (tableView.getColumns().size() == 5) {
            tableView.getColumns().remove(4);
        }
    }
}
