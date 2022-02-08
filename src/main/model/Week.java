package model;

import java.util.HashMap;

// Represents a week with 7 days, the total weekly consumption, and last week's consumption
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

    // EFFECTS: creates new Week with no items, and 0 for totals
    public Week() {
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
    }

    //SUNDAY METHODS

    // MODIFIES: this
    // EFFECTS: Creates a new pill with given name and adds it to Sunday
    public void addSunday(String name) {
        Pill myPill = new Pill(name);
        sunday.put(name, myPill);
        weeklyConsumption++;
    }


    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Sunday
    public void removeSunday(String name) {
        sunday.remove(name);
        weeklyConsumption--;
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
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Monday
    public void removeMonday(String name) {
        monday.remove(name);
        weeklyConsumption--;
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
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Tuesday
    public void removeTuesday(String name) {
        tuesday.remove(name);
        weeklyConsumption--;
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
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Wednesday
    public void removeWednesday(String name) {
        wednesday.remove(name);
        weeklyConsumption--;
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
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Thursday
    public void removeThursday(String name) {
        thursday.remove(name);
        weeklyConsumption--;
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
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Friday
    public void removeFriday(String name) {
        friday.remove(name);
        weeklyConsumption--;
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
    }

    // REQUIRES: hashmap should not be empty when called
    // MODIFIES: this
    // EFFECTS: Removes a pill with given name from Saturday
    public void removeSaturday(String name) {
        saturday.remove(name);
        weeklyConsumption--;
    }

    public HashMap<String, Pill> getSaturday() {
        return saturday;
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
}