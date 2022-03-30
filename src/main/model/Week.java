package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// Represents a week with 7 days, the total weekly consumption, last week's consumption, and a target
public class Week {
    private final HashMap<String, Pill> sunday;
    private final HashMap<String, Pill> monday;
    private final HashMap<String, Pill> tuesday;
    private final HashMap<String, Pill> wednesday;
    private final HashMap<String, Pill> thursday;
    private final HashMap<String, Pill> friday;
    private final HashMap<String, Pill> saturday;
    private int weeklyConsumption;
    private int lastWeek;
    private int targetTotal;
    private final String name;

    // EFFECTS: creates new Week with name, no items, and 0 for totals
    public Week(String name) {
        this.sunday = new HashMap<>();
        this.monday = new HashMap<>();
        this.tuesday = new HashMap<>();
        this.wednesday = new HashMap<>();
        this.thursday = new HashMap<>();
        this.friday = new HashMap<>();
        this.saturday = new HashMap<>();
        this.weeklyConsumption = 0;
        this.lastWeek = 0;
        this.targetTotal = 0;
        this.name = name;
    }

    //SUNDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Sunday
    public void addSunday(String name) {
        Pill myPill = new Pill(name);
        sunday.put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to Sunday: " + name));
    }


    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Sunday
    public void removeSunday(String name) {
        sunday.remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from Sunday: " + name));
    }

    public HashMap<String, Pill> getSunday() {
        return sunday;
    }

    //MONDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Monday
    public void addMonday(String name) {
        Pill myPill = new Pill(name);
        monday.put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to Monday: " + name));
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Monday
    public void removeMonday(String name) {
        monday.remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from Monday: " + name));
    }

    public HashMap<String, Pill> getMonday() {
        return monday;
    }

    //TUESDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Tuesday
    public void addTuesday(String name) {
        Pill myPill = new Pill(name);
        tuesday.put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to Tuesday: " + name));
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Tuesday
    public void removeTuesday(String name) {
        tuesday.remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from Tuesday: " + name));
    }

    public HashMap<String, Pill> getTuesday() {
        return tuesday;
    }

    //WEDNESDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Wednesday
    public void addWednesday(String name) {
        Pill myPill = new Pill(name);
        wednesday.put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to Wednesday: " + name));
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Wednesday
    public void removeWednesday(String name) {
        wednesday.remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from Wednesday: " + name));
    }

    public HashMap<String, Pill> getWednesday() {
        return wednesday;
    }

    //THURSDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Thursday
    public void addThursday(String name) {
        Pill myPill = new Pill(name);
        thursday.put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to Thursday: " + name));
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Thursday
    public void removeThursday(String name) {
        thursday.remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from Thursday: " + name));
    }

    public HashMap<String, Pill> getThursday() {
        return thursday;
    }

    //FRIDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Friday
    public void addFriday(String name) {
        Pill myPill = new Pill(name);
        friday.put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to Friday: " + name));
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Friday
    public void removeFriday(String name) {
        friday.remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from Friday: " + name));
    }

    public HashMap<String, Pill> getFriday() {
        return friday;
    }

    //SATURDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Saturday
    public void addSaturday(String name) {
        Pill myPill = new Pill(name);
        saturday.put(name, myPill);
        weeklyConsumption++;
        EventLog.getInstance().logEvent(new Event("Added to Saturday: " + name));
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Saturday
    public void removeSaturday(String name) {
        saturday.remove(name);
        weeklyConsumption--;
        EventLog.getInstance().logEvent(new Event("Removed from Saturday: " + name));
    }

    public HashMap<String, Pill> getSaturday() {
        return saturday;
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
        json.put("sunday", pillsToJson(sunday));
        json.put("monday", pillsToJson(monday));
        json.put("tuesday", pillsToJson(tuesday));
        json.put("wednesday", pillsToJson(wednesday));
        json.put("thursday", pillsToJson(thursday));
        json.put("friday", pillsToJson(friday));
        json.put("saturday", pillsToJson(saturday));
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