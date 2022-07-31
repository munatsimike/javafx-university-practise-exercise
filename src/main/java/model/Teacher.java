package model;

import java.util.Date;

public class Teacher extends AcademicPerson {
    double salary;

    public double getSalary() {
        return salary;
    }

    public Teacher(int id, String userName, String password, String firstName, String lastName, Date birth_date, double salary) {
        super(id, userName, password, firstName, lastName, birth_date);
        this.salary = salary;
    }
}
