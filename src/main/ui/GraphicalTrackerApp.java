package ui;

import model.Pill;
import model.Week;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.*;

// basic code sourced from https://stackoverflow.com/questions/42867271/displaying-multiple-jlist-to-jframe
public class GraphicalTrackerApp extends JPanel implements ActionListener, ItemListener {
    private Week thisWeek;
    private static final String JSON_STORE = "./data/yourWeek.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String[] WEEK_DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday"};

    public GraphicalTrackerApp() {
        startUp();
    }

    public Container createContentPane() {
        setLayout(new GridLayout(0, 7));
        createDaysOfWeek();
        createTotals();
        createButtonPanel();
        return this;
    }

    private void createDaysOfWeek() {
        for (int i = 0; i < WEEK_DAYS.length; i++) {
            // new model for each week
            DefaultListModel<String> listModel = new DefaultListModel<>();
            for (String s : thisWeek.getSunday().keySet()) {
                // fill the model with elements of Sunday!!!
                listModel.addElement(s);
            }
            // new list for each day of the week with model
            JList<String> list = new JList<>(listModel);
            list.setVisibleRowCount(4);
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            // border around showing the day of the week
            JPanel container = new JPanel(new BorderLayout());
            container.add(scrollPane);
            container.setBorder(BorderFactory.createTitledBorder(WEEK_DAYS[i]));
            add(container);
        }
    }

    private void startUp() {
        thisWeek = new Week("Your Weekly Tracker");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public void createTotals() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.Y_AXIS));
        buttonPane.add(new JLabel("Weekly Total: " + thisWeek.getWeeklyConsumption()));
        buttonPane.add(new JLabel("Target Total: " + thisWeek.getTargetTotal()));
        buttonPane.add(new JLabel("Last Weeks Total: " + thisWeek.getLastWeek()));
        add(buttonPane, BorderLayout.PAGE_END);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    public Component createButtonPanel() {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(new Button());
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(new TextField());
        buttonPane.add(new Button());
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(buttonPane, BorderLayout.PAGE_END);
        return buttonPane;
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem saveItem;
        JMenuItem loadItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        //a group of JMenuItems
        saveItem = new JMenuItem("Save week to file", KeyEvent.VK_T);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
        saveItem.setActionCommand("save");
        saveItem.addActionListener(this);
        loadItem = new JMenuItem("Load week from file", KeyEvent.VK_L);
        loadItem.setMnemonic(KeyEvent.VK_B);
        loadItem.setActionCommand("save");
        loadItem.addActionListener(this);

        menu.add(saveItem);
        menu.add(loadItem);

        return menuBar;
    }

    private static void createAndShowGui() {
        GraphicalTrackerApp trackerApp = new GraphicalTrackerApp();
        JFrame frame = new JFrame("Pill Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(trackerApp.createContentPane());
        frame.setJMenuBar(trackerApp.createMenuBar());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("data/pillobject.png");
        frame.setIconImage(icon);
    }

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("save".equals(e.getActionCommand())) {
            saveWeek();
        } else {
            loadWeek();
        }
    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     *
     */
    @Override
    public void itemStateChanged(ItemEvent e) {

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }


    // EFFECTS: saves the week to destination file
    private void saveWeek() {
        try {
            jsonWriter.open();
            jsonWriter.write(thisWeek);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: File not found");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWeek() {
        try {
            thisWeek = jsonReader.read();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to read from file");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
//   UNUSED CURRENT TRACKER APP  //
    private Scanner input;

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
    // EFFECTS: adds an item to a day depending on the given input
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

}