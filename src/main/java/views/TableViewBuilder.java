package views;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.AcademicPerson;
import model.Person;
import enums.PersonType;

import java.util.Date;
import java.util.function.Predicate;

public class TableViewBuilder {
    TableView<AcademicPerson> tableView;
    TableView.TableViewSelectionModel<AcademicPerson> selectionModel;
    ObservableList<AcademicPerson> selectedItems;
    FilteredList<AcademicPerson> filteredData;
    MySearchForm mySearchForm;
    VBox searchTableviewContainer;

    public TableViewBuilder() {
        tableView = new TableView<>();
        mySearchForm = new MySearchForm();
        searchTableviewContainer = new VBox(20);
        // add search form to container
        searchTableviewContainer.getChildren().add(mySearchForm.getSearchFrom());
        tableView.setMinWidth(800);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        defaultColumns();
        setSelectionMode();
        selectedItems = selectionModel.getSelectedItems();
        selectedItemsChangeListener();
        searchQuerylistener();
    }

    private void setSelectionMode() {
        selectionModel = tableView.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    // default columns for students and teachers
    private void defaultColumns() {
        TableColumn<AcademicPerson, Integer> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<AcademicPerson, String> firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<AcademicPerson, String> lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<AcademicPerson, Date> birthdate = new TableColumn<>("Birthdate");
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birth_date"));

        tableView.getColumns().addAll(id, firstNameColumn, lastNameColumn, birthdate);
    }


    public VBox getTable(PersonType person) {
        // add additional columns
        if (person == PersonType.STUDENT) {
            // remove salary column
            removeColumn();
            TableColumn<AcademicPerson, String> group = new TableColumn<>("Group");
            group.setCellValueFactory(new PropertyValueFactory<>("group"));
            // add group column for students
            tableView.getColumns().add(group);
        } else {
            // remove group column
            removeColumn();
            TableColumn<AcademicPerson, Double> salary = new TableColumn<>("Salary");
            salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
            // add salary column for teachers
            tableView.getColumns().add(salary);
        }
        if (searchTableviewContainer.getChildren().size() == 2) {
            searchTableviewContainer.getChildren().remove(1);
        }

        searchTableviewContainer.getChildren().add(tableView);
        return searchTableviewContainer;
    }

    public void setTableItems(ObservableList<AcademicPerson> list) {
        filteredData = new FilteredList<>(list);
        tableView.setItems(filteredData);
    }


    // clear selected row
    public void clearTableSelection() {
        selectionModel.clearSelection();
    }

    // listen to selection changes
    private void selectedItemsChangeListener() {
        selectedItems.addListener((ListChangeListener<Person>) change -> {
        });
    }

    // remove salary or group column.
    private void removeColumn() {
        if (tableView.getColumns().size() == 5) {
            tableView.getColumns().remove(4);
        }
    }

    // return items of a selected row
    public AcademicPerson getSelectedIPerson() {
        return selectedItems.get(0);
    }

    // check if any row is selected
    public boolean isRowSelected() {
        return selectedItems.size() > 0;
    }

    private boolean searchFindPerson(AcademicPerson person, String searchText) {
        return (person.getFirstName().toLowerCase().startsWith(searchText.toLowerCase())) ||
                (person.getLastName().toLowerCase().startsWith(searchText.toLowerCase())) ||
                Integer.valueOf(person.getId()).toString().equals(searchText.toLowerCase());
    }

    private Predicate<AcademicPerson> createPredicate(String searchText) {
        return person -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindPerson(person, searchText.trim());
        };
    }

    private void searchQuerylistener() {
        mySearchForm.getSearchTextField().textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue)));
    }
}

