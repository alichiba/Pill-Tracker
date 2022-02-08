package model;

// Represents a pill or object that is being tracked
public class Pill {
    private String name;

    // EFFECTS: constructs new Pill with given name
    public Pill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
