package model;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AcademicPerson extends Person {
    LocalDate birth_date;

    public AcademicPerson(String username, String password) {
        super(username, password);
    }

    public LocalDate getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public AcademicPerson(int id, String userName, String password, String firstName, String lastName, LocalDate birth_date) {
        super(id, userName, password, firstName, lastName);
        this.birth_date = birth_date;
    }

    public boolean isSamePerson(AcademicPerson person1, AcademicPerson person2) {
        for (Field field : getAllFields(person1.getClass())) {
            try {
                if (!field.get(person1).equals(field.get(person2)))
                    return false;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    // get all fields of a child class and base classes
    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
