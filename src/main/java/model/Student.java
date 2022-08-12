package model;

import java.time.LocalDate;

public class Student extends AcademicPerson {
    String group;

    public Student(String username, String password) {
        super(username, password);
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public Student(int id, String userName, String password, String firstName, String lastName, LocalDate birth_date, String group) {
        super(id, userName, password, firstName, lastName, birth_date);
        this.group = group;
    }
}
