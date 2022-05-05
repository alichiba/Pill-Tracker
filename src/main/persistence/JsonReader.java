package persistence;

import model.Day;
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
        JSONArray sunArray = savedJsonObject.getJSONArray("sunday");
        JSONArray monArray = savedJsonObject.getJSONArray("monday");
        JSONArray tueArray = savedJsonObject.getJSONArray("tuesday");
        JSONArray wedArray = savedJsonObject.getJSONArray("wednesday");
        JSONArray thuArray = savedJsonObject.getJSONArray("thursday");
        JSONArray friArray = savedJsonObject.getJSONArray("friday");
        JSONArray satArray = savedJsonObject.getJSONArray("saturday");
        addPills(w, w.getSunday(), sunArray);
        addPills(w, w.getMonday(), monArray);
        addPills(w, w.getTuesday(), tueArray);
        addPills(w, w.getWednesday(), wedArray);
        addPills(w, w.getThursday(), thuArray);
        addPills(w, w.getFriday(), friArray);
        addPills(w, w.getSaturday(), satArray);
    }

    // TODO add docs
    private void addPills(Week w, Day d, JSONArray newArray) {
        for (Object json : newArray) {
            JSONObject nextJson = (JSONObject) json;
            String s = nextJson.getString("name");
            w.add(d, s);
        }
    }

}
