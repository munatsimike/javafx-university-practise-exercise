package model;

public class Person {
    int id;
    String userName;
    String password;
    String firstName;
    String lastName;

    public Person(int id, String userName, String password, String firstName, String lastName) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
