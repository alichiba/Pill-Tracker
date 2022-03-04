package ui;

import model.Pill;
import model.Week;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

// Pill Tracker Application
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
        System.out.println("\ts -> save work room to file");
        System.out.println("\tl -> load work room from file");
        System.out.println("\te -> exit");
    }

    // MODIFIES: this
    // EFFECTS: processes the input choice for the main menu
    private void processCommand(String command) {
        if (command.equals("v")) {
            viewWeek();
        } else if (command.equals("a")) {
            addItem();
        } else if (command.equals("r")) {
            removeItem();
        } else if (command.equals("t")) {
            setTotal();
        } else if (command.equals("n")) {
            nextWeek();
        } else if (command.equals("s")) {
            saveWeek();
        } else if (command.equals("l")) {
            loadWeek();
        } else {
            System.out.println("Invalid Selection...");
        }
    }

    // MODIFIES: this
    // EFFECTS: produces the items in the days of the week and the totals
    private void viewWeek() {
        viewSunday();
        viewMonday();
        viewTuesday();
        viewWednesday();
        viewThursday();
        viewFriday();
        viewSaturday();
        System.out.println("\nWeekly Total: " + thisWeek.getWeeklyConsumption());
        System.out.println("Target Total: " + thisWeek.getTargetTotal());
        System.out.println(thisWeek.targetReached());
        System.out.println("Last Weeks Total: " + thisWeek.getLastWeek());
    }

    // MODIFIES: this
    // EFFECTS: runs the add menu and adds the desired item to the desired day
    private void addItem() {
        System.out.println("Enter name of item:");
        String name = input.next();
        System.out.println("\nAdd to:");
        String day = pickDay();
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
    // EFFECTS: Adds an item to a day depending on the given input
    private void addTo(String choice, String name) {
        switch (choice) {
            case "sun":
                thisWeek.addSunday(name);
                break;
            case "mon":
                thisWeek.addMonday(name);
                break;
            case "tue":
                thisWeek.addTuesday(name);
                break;
            case "wed":
                thisWeek.addWednesday(name);
                break;
            case "thu":
                thisWeek.addThursday(name);
                break;
            case "fri":
                thisWeek.addFriday(name);
                break;
            case "sat":
                thisWeek.addSaturday(name);
                break;
        }
    }


    // EFFECTS: returns the menu messages after adding an item
    private void returnAddStatement(String choice, String name) {
        if (choice.equals("sun")) {
            System.out.println(name + " has been added to Sunday");
        } else if (choice.equals("mon")) {
            System.out.println(name + " has been added to Monday");
        } else if (choice.equals("tue")) {
            System.out.println(name + " has been added to Tuesday");
        } else if (choice.equals("wed")) {
            System.out.println(name + " has been added to Wednesday");
        } else if (choice.equals("thu")) {
            System.out.println(name + " has been added to Thursday");
        } else if (choice.equals("fri")) {
            System.out.println(name + " has been added to Friday");
        } else if (choice.equals("sat")) {
            System.out.println(name + " has been added to Saturday");
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: finds item given name and removes it from given day
    private void removeItem() {
        System.out.println("Enter name of item:");
        String name = input.next();
        System.out.println("\nRemove from:");
        String day = pickDay();
        if (existsIn(day, name)) {
            removeFrom(day, name);
            returnRemoveStatement(day, name);
        } else {
            System.out.println("Item not found in specified day.");
        }
    }

    // EFFECTS: looks to see if a given name exists in the given day of the week
    private boolean existsIn(String choice, String name) {
        boolean exists = false;
        if (choice.equals("sun")) {
            exists = thisWeek.getSunday().containsKey(name);
        } else if (choice.equals("mon")) {
            exists = thisWeek.getMonday().containsKey(name);
        } else if (choice.equals("tue")) {
            exists = thisWeek.getTuesday().containsKey(name);
        } else if (choice.equals("wed")) {
            exists = thisWeek.getWednesday().containsKey(name);
        } else if (choice.equals("thu")) {
            exists = thisWeek.getThursday().containsKey(name);
        } else if (choice.equals("fri")) {
            exists = thisWeek.getFriday().containsKey(name);
        } else if (choice.equals("sat")) {
            exists = thisWeek.getSaturday().containsKey(name);
        }
        return exists;
    }

    // MODIFIES: this
    // EFFECTS: removes an item from a day depending on given input
    private void removeFrom(String choice, String name) {
        switch (choice) {
            case "sun":
                thisWeek.removeSunday(name);
                break;
            case "mon":
                thisWeek.removeMonday(name);
                break;
            case "tue":
                thisWeek.removeTuesday(name);
                break;
            case "wed":
                thisWeek.removeWednesday(name);
                break;
            case "thu":
                thisWeek.removeThursday(name);
                break;
            case "fri":
                thisWeek.removeFriday(name);
                break;
            case "sat":
                thisWeek.removeSaturday(name);
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the menu messages after removing an item
    private void returnRemoveStatement(String choice, String name) {
        if (choice.equals("sun")) {
            System.out.println(name + " has been removed from Sunday");
        } else if (choice.equals("mon")) {
            System.out.println(name + " has been removed from Monday");
        } else if (choice.equals("tue")) {
            System.out.println(name + " has been removed from Tuesday");
        } else if (choice.equals("wed")) {
            System.out.println(name + " has been removed from Wednesday");
        } else if (choice.equals("thu")) {
            System.out.println(name + " has been removed from Thursday");
        } else if (choice.equals("fri")) {
            System.out.println(name + " has been removed from Friday");
        } else if (choice.equals("sat")) {
            System.out.println(name + " has been removed from Saturday");
        } else {
            System.out.println("Invalid input. Please try again.");
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


    // MODIFIES: this
    // EFFECTS: lists the names of items stored in Sunday
    private void viewSunday() {
        String sundayItems = "";
        HashMap<String, Pill> sunday = thisWeek.getSunday();
        if (sunday != null) {
            for (Pill pill : thisWeek.getSunday().values()) {
                sundayItems += pill.getName() + ", ";
            }
            System.out.println("\tSunday: " + sundayItems);
        } else {
            System.out.println("\tSunday: no items");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists the names of items stored in Monday
    private void viewMonday() {
        String mondayItems = "";
        HashMap<String, Pill> monday = thisWeek.getMonday();
        if (monday != null) {
            for (Pill pill : thisWeek.getMonday().values()) {
                mondayItems += pill.getName() + ", ";
            }
            System.out.println("\tMonday: " + mondayItems);
        } else {
            System.out.println("\tMonday: no items");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists the names of items stored in Tuesday
    private void viewTuesday() {
        String tuesdayItems = "";
        HashMap<String, Pill> tuesday = thisWeek.getTuesday();
        if (tuesday != null) {
            for (Pill pill : thisWeek.getTuesday().values()) {
                tuesdayItems += pill.getName() + ", ";
            }
            System.out.println("\tTuesday: " + tuesdayItems);
        } else {
            System.out.println("\tTuesday: no items");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists the names of items stored in Wednesday
    private void viewWednesday() {
        String wednesdayItems = "";
        HashMap<String, Pill> wednesday = thisWeek.getWednesday();
        if (wednesday != null) {
            for (Pill pill : thisWeek.getWednesday().values()) {
                wednesdayItems += pill.getName() + ", ";
            }
            System.out.println("\tWednesday: " + wednesdayItems);
        } else {
            System.out.println("\tWednesday: no items");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists the names of items stored in Thursday
    private void viewThursday() {
        String thursdayItems = "";
        HashMap<String, Pill> thursday = thisWeek.getThursday();
        if (thursday != null) {
            for (Pill pill : thisWeek.getThursday().values()) {
                thursdayItems += pill.getName() + ", ";
            }
            System.out.println("\tThursday: " + thursdayItems);
        } else {
            System.out.println("\tThursday: no items");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists the names of items stored in Friday
    private void viewFriday() {
        String fridayItems = "";
        HashMap<String, Pill> friday = thisWeek.getFriday();
        if (friday != null) {
            for (Pill pill : thisWeek.getFriday().values()) {
                fridayItems += pill.getName() + ", ";
            }
            System.out.println("\tFriday: " + fridayItems);
        } else {
            System.out.println("\tFriday: no items");
        }
    }

    // MODIFIES: this
    // EFFECTS: lists the names of items stored in Saturday
    private void viewSaturday() {
        String saturdayItems = "";
        HashMap<String, Pill> saturday = thisWeek.getSaturday();
        if (saturday != null) {
            for (Pill pill : thisWeek.getSaturday().values()) {
                saturdayItems += pill.getName() + ", ";
            }
            System.out.println("\tSaturday: " + saturdayItems);
        } else {
            System.out.println("\tSaturday: no items");
        }
    }


    // EFFECTS: saves the workroom to file
    private void saveWeek() {
        try {
            jsonWriter.open();
            jsonWriter.write(thisWeek);
            jsonWriter.close();
            System.out.println("Saved " + thisWeek.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWeek() {
        try {
            thisWeek = jsonReader.read();
            System.out.println("Loaded " + thisWeek.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}