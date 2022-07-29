package model;

import java.util.Date;

public class Student extends AcademicPerson {
    int group;

    public Student(int id, String userName, String password, String firstName, String lastName, Date birth_date, int group) {
        super(id, userName, password, firstName, lastName, birth_date);
        this.group = group;
    }
}
