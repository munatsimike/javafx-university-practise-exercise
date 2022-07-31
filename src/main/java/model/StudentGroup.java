package model;

public enum StudentGroup {
    INFO2A,
    INFO2B;

    public String toString() {
        switch (this) {
            case INFO2A:
                return ("INFO2-A");
            case INFO2B:
                return ("INFO2-B");
        }
        return null;
    }
}
