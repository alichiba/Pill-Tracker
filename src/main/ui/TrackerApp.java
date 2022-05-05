package ui;

import model.Day;
import model.Pill;
import model.Week;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

// Pill Tracker Application console user interface
public class TrackerApp {
    private static final String JSON_STORE = "./data/yourWeek.json";
    private Week thisWeek;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the tracker application
    public TrackerApp() {
        input = new Scanner(System.in);
        thisWeek = new Week("Your Weekly Tracker");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes input for the main menu
    public void runTracker() {
        boolean keepGoing = true;
        String command;
        startUp();
        while (keepGoing) {
            displayMenu();
            command = input.next().toLowerCase();
            if (command.equals("e")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nExited successfully. See you soon!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the week
    public void startUp() {
        thisWeek = new Week("Your Weekly Tracker");
        input = new Scanner(System.in);
    }

    // EFFECTS: displays the main menu options
    private void displayMenu() {
        System.out.println("\nWelcome to your tracker! Select from:");
        System.out.println("\tv -> view week");
        System.out.println("\ta -> add item");
        System.out.println("\tr -> remove item");
        System.out.println("\tt -> set a target amount");
        System.out.println("\tn -> start a new week");
        System.out.println("\ts -> save week to file");
        System.out.println("\tl -> load week from file");
        System.out.println("\te -> exit");
    }

    // MODIFIES: this
    // EFFECTS: processes the input choice for the main menu
    private void processCommand(String command) {
        switch (command) {
            case "v":
                viewWeek();
                break;
            case "a":
                addItem();
                break;
            case "r":
                removeItem();
                break;
            case "t":
                setTotal();
                break;
            case "n":
                nextWeek();
                break;
            case "s":
                saveWeek();
                break;
            case "l":
                loadWeek();
                break;
            default:
                System.out.println("Invalid Selection...");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: produces the items in the days of the week and the totals
    private void viewWeek() {
        view(thisWeek.getSunday());
        view(thisWeek.getMonday());
        view(thisWeek.getTuesday());
        view(thisWeek.getWednesday());
        view(thisWeek.getThursday());
        view(thisWeek.getFriday());
        view(thisWeek.getSaturday());
        System.out.println("\nWeekly Total: " + thisWeek.getWeeklyConsumption());
        System.out.println("Target Total: " + thisWeek.getTargetTotal());
        System.out.println(thisWeek.targetReached());
        System.out.println("Last Weeks Total: " + thisWeek.getLastWeek());
    }

    // MODIFIES: this
    // EFFECTS: runs the add menu and adds the desired item to the desired day
    private void addItem() {
        System.out.println("Enter name of item:");
        String name = input.next().toLowerCase();
        System.out.println("\nAdd to:");
        String day = pickDay();
        // TODO!!! add exception (catch exception here, throw in Day?) for same name same day
        // message: Cannot add because name already exists in specified day.
        //          Consider adding a number after the name ie. "pill2"
        addTo(day, name);
        returnAddStatement(day, name);
    }

    // MODIFIES: this
    // EFFECTS: processes the input for picking the day
    private String pickDay() {
        String choice;
        System.out.println("\tsun for Sunday");
        System.out.println("\tmon for Monday");
        System.out.println("\ttue for Tuesday");
        System.out.println("\twed for Wednesday");
        System.out.println("\tthu for Thursday");
        System.out.println("\tfri for Friday");
        System.out.println("\tsat for Saturday");
        choice = input.next();
        choice = choice.toLowerCase();
        return choice;
    }

    // MODIFIES: this
    // EFFECTS: adds an item to a day depending on the given input
    private void addTo(String choice, String name) {
        switch (choice) {
            case "sun":
                thisWeek.add(thisWeek.getSunday(), name);
                break;
            case "mon":
                thisWeek.add(thisWeek.getMonday(), name);
                break;
            case "tue":
                thisWeek.add(thisWeek.getTuesday(), name);
                break;
            case "wed":
                thisWeek.add(thisWeek.getWednesday(), name);
                break;
            case "thu":
                thisWeek.add(thisWeek.getThursday(), name);
                break;
            case "fri":
                thisWeek.add(thisWeek.getFriday(), name);
                break;
            case "sat":
                thisWeek.add(thisWeek.getSaturday(), name);
                break;
        }
    }


    // EFFECTS: returns the menu messages after adding an item
    private void returnAddStatement(String choice, String name) {
        switch (choice) {
            case "sun":
                System.out.println(name + " has been added to Sunday");
                break;
            case "mon":
                System.out.println(name + " has been added to Monday");
                break;
            case "tue":
                System.out.println(name + " has been added to Tuesday");
                break;
            case "wed":
                System.out.println(name + " has been added to Wednesday");
                break;
            case "thu":
                System.out.println(name + " has been added to Thursday");
                break;
            case "fri":
                System.out.println(name + " has been added to Friday");
                break;
            case "sat":
                System.out.println(name + " has been added to Saturday");
                break;
            default:
                System.out.println("Invalid input. Please try again.");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: finds item given name and removes it from given day
    private void removeItem() {
        System.out.println("Enter name of item:");
        String name = input.next().toLowerCase();
        System.out.println("\nRemove from:");
        String day = pickDay();
        if (existsIn(day, name)) {
            removeFrom(day, name);
            returnRemoveStatement(day, name);
        } else {
            System.out.println("Item not found.");
        }
    }

    // EFFECTS: looks to see if a given name exists in the given day of the week
    private boolean existsIn(String choice, String name) {
        boolean exists = false;
        switch (choice) {
            case "sun":
                exists = thisWeek.getSunday().getMap().containsKey(name);
                break;
            case "mon":
                exists = thisWeek.getMonday().getMap().containsKey(name);
                break;
            case "tue":
                exists = thisWeek.getTuesday().getMap().containsKey(name);
                break;
            case "wed":
                exists = thisWeek.getWednesday().getMap().containsKey(name);
                break;
            case "thu":
                exists = thisWeek.getThursday().getMap().containsKey(name);
                break;
            case "fri":
                exists = thisWeek.getFriday().getMap().containsKey(name);
                break;
            case "sat":
                exists = thisWeek.getSaturday().getMap().containsKey(name);
                break;
        }
        return exists;
    }

    // MODIFIES: this
    // EFFECTS: removes an item from a day depending on given input
    private void removeFrom(String choice, String name) {
        switch (choice) {
            case "sun":
                thisWeek.remove(thisWeek.getSunday(), name);
                break;
            case "mon":
                thisWeek.remove(thisWeek.getMonday(), name);
                break;
            case "tue":
                thisWeek.remove(thisWeek.getTuesday(), name);
                break;
            case "wed":
                thisWeek.remove(thisWeek.getWednesday(), name);
                break;
            case "thu":
                thisWeek.remove(thisWeek.getThursday(), name);
                break;
            case "fri":
                thisWeek.remove(thisWeek.getFriday(), name);
                break;
            case "sat":
                thisWeek.remove(thisWeek.getSaturday(), name);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the menu messages after removing an item
    private void returnRemoveStatement(String choice, String name) {
        switch (choice) {
            case "sun":
                System.out.println(name + " has been removed from Sunday");
                break;
            case "mon":
                System.out.println(name + " has been removed from Monday");
                break;
            case "tue":
                System.out.println(name + " has been removed from Tuesday");
                break;
            case "wed":
                System.out.println(name + " has been removed from Wednesday");
                break;
            case "thu":
                System.out.println(name + " has been removed from Thursday");
                break;
            case "fri":
                System.out.println(name + " has been removed from Friday");
                break;
            case "sat":
                System.out.println(name + " has been removed from Saturday");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a new week with the current week's consumption stored as last week
    private void nextWeek() {
        int savedWeek = thisWeek.getWeeklyConsumption();
        int savedTarget = thisWeek.getTargetTotal();
        thisWeek = new Week("Your Weekly Tracker");
        thisWeek.updateLastWeek(savedWeek);
        thisWeek.setTargetTotal(savedTarget);
        System.out.println("The next week has been started.");
    }

    // MODIFIES: this
    // EFFECTS: displays menu to set target total and sets input as the target
    private void setTotal() {
        int target;
        System.out.println("Enter an amount for the target total:");
        target = input.nextInt();
        thisWeek.setTargetTotal(target);
        System.out.println("Target has been set to the indicated amount.");
    }

    // TODO add doc
    private void view(Day day) {
        String items = "";
        HashMap<String, Pill> dayMap = day.getMap();
        if (dayMap != null) {
            for (Pill p : dayMap.values()) {
                items += p.getName() + ", ";
            }
            System.out.println("\tSunday: " + items);
        } else {
            System.out.println("\tSunday: no items");
        }

    }

    // EFFECTS: saves the week to destination file
    private void saveWeek() {
        try {
            jsonWriter.open();
            jsonWriter.write(thisWeek);
            jsonWriter.close();
            System.out.println("Successfully saved " + thisWeek.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWeek() {
        try {
            thisWeek = jsonReader.read();
            System.out.println("Successfully loaded " + thisWeek.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}