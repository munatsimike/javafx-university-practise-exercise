package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Person;
import model.PersonType;
import model.Student;
import model.Teacher;

import java.util.Date;
import java.util.stream.Collectors;

public class Database {
    ObservableList<Person> persons;

    public Database() {
        persons = FXCollections.observableArrayList();
        persons.add(new Student(1, "Student", "123", "Student", "Munatsi", new Date(), "INV-6"));
        persons.add(new Student(2, "Student", "123", "Student", "Munatsi", new Date(), "INV-5"));
        persons.add(new Student(3, "Student", "123", "Student", "Munatsi", new Date(), "INV-7"));
        persons.add(new Student(4, "Student", "123", "Student", "Munatsi", new Date(), "INV-3"));
        persons.add(new Student(5, "Student", "123", "Student", "Munatsi", new Date(), "INV-5"));
        persons.add(new Student(6, "Student", "123", "Student", "Munatsi", new Date(), "INV-5"));

        persons.add(new Teacher(7, "Teacher", "123", "Teacher", "Munatsi", new Date(), 5000));
        persons.add(new Teacher(8, "Teacher", "123", "Teacher", "Munatsi", new Date(), 5000));
        persons.add(new Teacher(9, "Teacher", "123", "Teacher", "Munatsi", new Date(), 5000));
        persons.add(new Teacher(10, "Teacher", "123", "Teacher", "Munatsi", new Date(), 5000));
    }

    public ObservableList<Person> getPersons(PersonType personType) {
        if (personType == PersonType.STUDENT) {
            return FXCollections.observableArrayList(persons.stream().filter(p -> p instanceof Student).collect(Collectors.toList()));
        } else {
            return FXCollections.observableArrayList(persons.stream().filter(p -> p instanceof Teacher).collect(Collectors.toList()));
        }
    }

    public int idGenerator() {
        int id = persons.size();
        return id++;
    }

}
