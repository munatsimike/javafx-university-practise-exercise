package enums;

public enum ButtonText {
    ADD_STUDENTS,
    EDIT_STUDENTS,
    DELETE_STUDENTS,
    ADD_TEACHERS,
    EDIT_TEACHERS,
    DELETE_TEACHERS,
    Cancel;

    @Override
    public String toString() {
        switch (this) {
            case ADD_STUDENTS:
                return "Add Students";
            case EDIT_STUDENTS:
                return "Edit Students";
            case DELETE_STUDENTS:
                return "Delete Students";
            case ADD_TEACHERS:
                return "Add Teachers";
            case EDIT_TEACHERS:
                return "Edit Teachers";
            case DELETE_TEACHERS:
                return "Delete Teachers";
            case Cancel:
                return "Cancel";
        }
        return null;
    }
}
