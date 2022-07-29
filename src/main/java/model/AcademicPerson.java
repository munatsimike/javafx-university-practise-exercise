package model;

import java.util.Date;

public class AcademicPerson extends Person{
    Date birth_date;

    public AcademicPerson(int id, String userName, String password, String firstName, String lastName, Date birth_date) {
        super(id, userName, password, firstName, lastName);
        this.birth_date = birth_date;
    }
}
