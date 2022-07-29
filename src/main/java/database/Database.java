package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Person;
import model.Student;

import java.util.Date;

public class Database {
    ObservableList<Person> persons;
    int id = 4;

    public Database() {
        persons = FXCollections.observableArrayList();
        persons.add(new Student(4, "Michael", "123","Michael", "Munatsi",new Date(),5 ));
        persons.add(new Student(4, "Michael", "123","Michael", "Munatsi",new Date(),5 ));
        persons.add(new Student(4, "Michael", "123","Michael", "Munatsi",new Date(),5 ));
        persons.add(new Student(4, "Michael", "123","Michael", "Munatsi",new Date(),5 ));
        persons.add(new Student(4, "Michael", "123","Michael", "Munatsi",new Date(),5 ));
        persons.add(new Student(4, "Michael", "123","Michael", "Munatsi",new Date(),5 ));
    }

    public ObservableList<Person> getPersons() {
        return persons;
    }

    public int idGenerator() {
        return id++;
    }

}
