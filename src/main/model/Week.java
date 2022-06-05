package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// Represents a week with 7 days, the total weekly consumption, last week's consumption, and a target
public class Week {
    private final Day sunday;
    private final Day monday;
    private final Day tuesday;
    private final Day wednesday;
    private final Day thursday;
    private final Day friday;
    private final Day saturday;
    private int weeklyConsumption;
    private int lastWeek;
    private int targetTotal;
    private final String name;

    // EFFECTS: creates new Week with name, no items, and 0 for totals
    public Week(String name) {
        this.sunday = new Day("sunday");
        this.monday = new Day("monday");
        this.tuesday = new Day("tuesday");
        this.wednesday = new Day("wednesday");
        this.thursday = new Day("thursday");
        this.friday = new Day("friday");
        this.saturday = new Day("saturday");

        this.weeklyConsumption = 0;
        this.lastWeek = 0;
        this.targetTotal = 0;
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: add a pill with given name to given day
    public void add(Day day, String name) {
        Pill myPill = new Pill(name);
        day.getMap().put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to " + day.getName() + ": " + name));
    }

    // MODIFIES: this
    // EFFECTS: removes a pill with given name to given day
    public void remove(Day day, String name) {
        day.getMap().remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from " + day.getName() + ": " + name));
    }

    // MODIFIES: this
    // EFFECTS: manually inputs total as the value for WeeklyConsumption
    public void updateWeeklyConsumption(int total) {
        weeklyConsumption = total;
    }


    // MODIFIES: this
    // EFFECTS: inputs the given integer as the value for lastWeek
    public void updateLastWeek(int total) {
        lastWeek = total;
    }

    // MODIFIES: this
    // EFFECTS: sets a value as the target total for a week
    public void setTargetTotal(int target) {
        targetTotal = target;
    }

    // EFFECTS: returns message indicating whether consumption is above, below or equal to the target
    public String targetReached() {
        String result;
        if (targetTotal == weeklyConsumption) {
            result = "The target total has been reached";
        } else if (targetTotal > weeklyConsumption) {
            result = "The weekly consumption is below the target total";
        } else {
            result = "The weekly consumption is above the target total";
        }
        return result;
    }

    public Day getSunday() {
        return sunday;
    }

    public Day getMonday() {
        return monday;
    }

    public Day getTuesday() {
        return tuesday;
    }

    public Day getWednesday() {
        return wednesday;
    }

    public Day getThursday() {
        return thursday;
    }

    public Day getFriday() {
        return friday;
    }

    public Day getSaturday() {
        return saturday;
    }

    public int getTargetTotal() {
        return targetTotal;
    }

    public int getWeeklyConsumption() {
        return weeklyConsumption;
    }

    public int getLastWeek() {
        return lastWeek;
    }

    public String getName() {
        return name;
    }

    // EFFECTS: makes a JSONObject with keys and values for all aspects of the Week
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("sunday", pillsToJson(sunday.getMap()));
        json.put("monday", pillsToJson(monday.getMap()));
        json.put("tuesday", pillsToJson(tuesday.getMap()));
        json.put("wednesday", pillsToJson(wednesday.getMap()));
        json.put("thursday", pillsToJson(thursday.getMap()));
        json.put("friday", pillsToJson(friday.getMap()));
        json.put("saturday", pillsToJson(saturday.getMap()));
        json.put("weeklyConsumption", weeklyConsumption);
        json.put("lastWeek", lastWeek);
        json.put("targetTotal", targetTotal);
        json.put("name", name);
        return json;
    }

    // EFFECTS: creates a JSONArray from the hashmap for a given day
    public JSONArray pillsToJson(HashMap<String, Pill> weekDay) {
        JSONArray jsonWeekDay = new JSONArray();
        for (Map.Entry<String, Pill> set : weekDay.entrySet()) {
            jsonWeekDay.put(set.getValue().toJson());
        }
        return jsonWeekDay;
    }
}