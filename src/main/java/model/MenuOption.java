package model;

public enum MenuOption {
    DASHBOARD,
    STUDENTS,
    TEACHERS;

    public String toString() {
        switch (this) {
            case DASHBOARD:
                return "Dashboard";
            case STUDENTS:
                return "Students";
            case TEACHERS:
                return "Teachers";
        }
        return null;
    }
}
