package persistence;

import model.Week;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that creates a Week from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads week from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Week read() throws IOException {
        String jsonData = readFile(source);
        JSONObject savedJsonObject = new JSONObject(jsonData);
        return parseWeek(savedJsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses week from JSON object and returns it
    private Week parseWeek(JSONObject savedJsonObject) {
        String name = savedJsonObject.getString("name");
        Week w = new Week(name);
        addDay(w, savedJsonObject);
        addTotals(w, savedJsonObject);
        return w;
    }

    // MODIFIES: w
    // EFFECTS: reads saved values for int totals and updates the week item
    private void addTotals(Week w, JSONObject savedJsonObject) {
        int weeklyConsumption = savedJsonObject.optInt("weeklyConsumption");
        int lastWeek = savedJsonObject.optInt("lastWeek");
        int targetTotal = savedJsonObject.optInt("targetTotal");

        w.updateWeeklyConsumption(weeklyConsumption);
        w.updateLastWeek(lastWeek);
        w.setTargetTotal(targetTotal);
    }

    // MODIFIES: w
    // EFFECTS: parses days/JSONArrays from JSON object and adds them to workroom
    private void addDay(Week w, JSONObject savedJsonObject) {
        JSONArray newSunArray = savedJsonObject.getJSONArray("sunday");
        JSONArray newMonArray = savedJsonObject.getJSONArray("monday");
        JSONArray newTueArray = savedJsonObject.getJSONArray("tuesday");
        JSONArray newWedArray = savedJsonObject.getJSONArray("wednesday");
        JSONArray newThuArray = savedJsonObject.getJSONArray("thursday");
        JSONArray newFriArray = savedJsonObject.getJSONArray("friday");
        JSONArray newSatArray = savedJsonObject.getJSONArray("saturday");
        addSunday(w, newSunArray);
        addMonday(w, newMonArray);
        addTuesday(w, newTueArray);
        addWednesday(w, newWedArray);
        addThursday(w, newThuArray);
        addFriday(w, newFriArray);
        addSaturday(w, newSatArray);
    }

    // MODIFIES: w
    // EFFECTS: takes JSONObjects from given Array and constructs a Pill into Sunday of given week
    private void addSunday(Week w, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.addSunday(s);
        }
    }

    // MODIFIES: w
    // EFFECTS: takes JSONObjects from given Array and constructs a Pill into Monday of given week
    private void addMonday(Week w, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.addMonday(s);
        }
    }

    // MODIFIES: w
    // EFFECTS: takes JSONObjects from given Array and constructs a Pill into Tuesday of given week
    private void addTuesday(Week w, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.addTuesday(s);
        }
    }

    // MODIFIES: w
    // EFFECTS: takes JSONObjects from given Array and constructs a Pill into Wednesday of given week
    private void addWednesday(Week w, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.addWednesday(s);
        }
    }

    // MODIFIES: w
    // EFFECTS: takes JSONObjects from given Array and constructs a Pill into Thursday of given week
    private void addThursday(Week w, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.addThursday(s);
        }
    }

    // MODIFIES: w
    // EFFECTS: takes JSONObjects from given Array and constructs a Pill into Friday of given week
    private void addFriday(Week w, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.addFriday(s);
        }
    }

    // MODIFIES: w
    // EFFECTS: takes JSONObjects from given Array and constructs a Pill into Saturday of given week
    private void addSaturday(Week w, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.addSaturday(s);
        }
    }
}
