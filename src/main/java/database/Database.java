package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class Database {
    ObservableList<AcademicPerson> persons;

    public Database() {
        persons = FXCollections.observableArrayList();

        persons.add(new Student(1, "Student", "12345678", "Tinashe", "Munatsi", LocalDate.now(), StudentGroup.INFO2A.toString()));
        persons.add(new Student(2, "Student", "12345678", "Student", "Student", LocalDate.now(), StudentGroup.INFO2B.toString()));
        persons.add(new Student(3, "Student", "12345678", "Tariro", "Bingura", LocalDate.now(), StudentGroup.INFO2A.toString()));
        persons.add(new Student(4, "Student", "12345678", "Student", "Munatsi", LocalDate.now(), StudentGroup.INFO2B.toString()));
        persons.add(new Student(5, "Student", "12345678", "Tatenda", "Bingura", LocalDate.now(), StudentGroup.INFO2A.toString()));
        persons.add(new Student(6, "Student", "12345678", "Student", "Munatsi", LocalDate.now(), StudentGroup.INFO2B.toString()));

        persons.add(new Teacher(7, "Teacher", "12345678", "Teacher", "Munatsi", LocalDate.now(), 5000));
        persons.add(new Teacher(8, "Teacher", "12345678", "Teacher", "Munatsi", LocalDate.now(), 5000));
        persons.add(new Teacher(9, "Teacher", "12345678", "Teacher", "Munatsi", LocalDate.now(), 5000));
        persons.add(new Teacher(10, "Teacher", "12345678", "Teacher", "Munatsi", LocalDate.now(), 5000));
    }

    public ObservableList<AcademicPerson> getPersons(PersonType personType) {
        if (personType == PersonType.STUDENT) {
            return FXCollections.observableArrayList(persons.stream().filter(p -> p instanceof Student).collect(Collectors.toList()));
        } else {
            return FXCollections.observableArrayList(persons.stream().filter(p -> p instanceof Teacher).collect(Collectors.toList()));
        }
    }

    public int getId() {
        int id = persons.size() + 1;
        return id++;
    }

    public void addPerson(AcademicPerson person) {
        persons.add(person);
    }

    public void editPerson(AcademicPerson newPerson) {
        for (AcademicPerson oldPerson : persons) {
            if (oldPerson.getId() == newPerson.getId()) {
                oldPerson.setUserName(newPerson.getUserName());
                oldPerson.setPassword(newPerson.getPassword());
                oldPerson.setFirstName(newPerson.getFirstName());
                oldPerson.setLastName(newPerson.getLastName());
                oldPerson.setBirth_date(newPerson.getBirth_date());

                if (oldPerson instanceof Student) {
                    Student newStudent = (Student) newPerson;
                    Student oldStudent = (Student) oldPerson;
                    oldStudent.setGroup(newStudent.getGroup());
                } else {
                    Teacher newTeacher = (Teacher) newPerson;
                    Teacher oldTeacher = (Teacher) oldPerson;
                    oldTeacher.setSalary(newTeacher.getSalary());
                }
            }
        }
    }

    public void deletePerson(int id) {
        persons.removeIf(person -> person.getId() == id);
    }
}
