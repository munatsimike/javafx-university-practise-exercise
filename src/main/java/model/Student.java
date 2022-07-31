package model;

import java.util.Date;

public class Student extends AcademicPerson {
    String group;

    public String getGroup() {
        return group;
    }

    public Student(int id, String userName, String password, String firstName, String lastName, Date birth_date, String group) {
        super(id, userName, password, firstName, lastName, birth_date);
        this.group = group;
    }
}
