package model;

import java.time.LocalDate;

public class Teacher extends AcademicPerson{
    double salary;

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }

    public Teacher(int id, String userName, String password, String firstName, String lastName, LocalDate birth_date, double salary) {
        super(id, userName, password, firstName, lastName, birth_date);
        this.salary = salary;
    }
}
