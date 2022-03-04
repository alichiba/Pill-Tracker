package model;

import org.json.JSONObject;

// Represents a pill or object that is being tracked
public class Pill {
    private final String name;

    // EFFECTS: constructs new Pill with given name
    public Pill(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJson() {
        JSONObject jsonPill = new JSONObject();
        jsonPill.put("name", name);
        return jsonPill;
    }
}
