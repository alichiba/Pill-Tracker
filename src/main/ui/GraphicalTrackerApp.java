package ui;

import model.Week;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// basic code sourced from https://stackoverflow.com/questions/42867271/displaying-multiple-jlist-to-jframe
public class GraphicalTrackerApp extends JPanel implements ActionListener, ItemListener, ListSelectionListener {
    private Week thisWeek;
    private static final String JSON_STORE = "./data/yourWeek.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String[] WEEK_DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday"};
    private JFrame frame;
    private Container contentPane;
    private JList<String> sunday;
    private DefaultListModel<String> sundayListModel;
    private JButton addButton;
    private JTextField textField;

    public GraphicalTrackerApp() {
        startUp();
        setLayout(new GridLayout(0, 7));
        createDaysOfWeek();
        createRemoveButton();
        createAddPanel();
        createTotals();
    }

    private void startUp() {
        thisWeek = new Week("Your Weekly Tracker");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }


    private void createDaysOfWeek() {
//        for (int i = 0; i < WEEK_DAYS.length; i++) {
//            // new model for each week
//            DefaultListModel<String> listModel = new DefaultListModel<>();
//            thisWeek.addSunday("pill1");
//            thisWeek.addSunday("pill2");
//            for (String s : thisWeek.getSunday().keySet()) {
//                // fill the model with elements of Sunday!!!
//                listModel.addElement(s);
//            }
//            // new list for each day of the week with model
//            JList<String> list = new JList<>(listModel);
//            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//            list.setVisibleRowCount(4);
//            JScrollPane scrollPane = new JScrollPane(list);
//            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//
//            // border around showing the day of the week
//            JPanel container = new JPanel(new BorderLayout());
//            container.add(scrollPane);
//            container.setBorder(BorderFactory.createTitledBorder(WEEK_DAYS[i]));
//            add(container);
//        }
        for (int i = 0; i < WEEK_DAYS.length; i++) {
            add(createSunday());
        }
    }

    private Component createSunday() {
        sundayListModel = new DefaultListModel<>();
        thisWeek.addSunday("pill1");
        thisWeek.addSunday("pill2");
        for (String s : thisWeek.getSunday().keySet()) {
            // fill the model with elements of Sunday!!!
            sundayListModel.addElement(s);
        }
        // new sunday for each day of the week with model
        sunday = new JList<>(sundayListModel);
        sunday.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sunday.setSelectedIndex(0);
        sunday.addListSelectionListener(this);
        sunday.setVisibleRowCount(4);
        JScrollPane scrollPane = new JScrollPane(sunday);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // border around showing the day of the week
        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane);
        container.setBorder(BorderFactory.createTitledBorder("Sunday"));
//        add(container);
        return container;
    }


    public void createTotals() {
        JPanel totalsPane = new JPanel();
        totalsPane.setLayout(new BoxLayout(totalsPane, BoxLayout.Y_AXIS));
        totalsPane.add(new JLabel("Weekly Total: " + thisWeek.getWeeklyConsumption()));
        totalsPane.add(new JLabel("Target Total: " + thisWeek.getTargetTotal()));
        totalsPane.add(new JLabel("Last Weeks Total: " + thisWeek.getLastWeek()));
//        add(totalsPane, BorderLayout.PAGE_END);
//        totalsPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(totalsPane);
    }

    public Component createRemoveButton() {
        JButton removeButton = new JButton("remove");
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(removeButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        removeButton.setActionCommand("remove");
        removeButton.addActionListener(this);

        add(buttonPane, BorderLayout.PAGE_END);
        return buttonPane;
    }

    public void createAddPanel() {
        addButton = new JButton("add");
        GraphicalTrackerApp.HireListener hireListener = new GraphicalTrackerApp.HireListener(addButton);
        addButton.setActionCommand("add");
        addButton.addActionListener(hireListener);

        textField = new JTextField(10);
        textField.addActionListener(hireListener);
        textField.getDocument().addDocumentListener(hireListener);
    }

    class HireListener implements ActionListener, DocumentListener {
        private JButton button;

        public HireListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name = textField.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                textField.requestFocusInWindow();
                textField.selectAll();
                return;
            }

            int index = sunday.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            thisWeek.addSunday(name);
            //If we just wanted to add to the end, we'd do this:
            sundayListModel.addElement(textField.getText());

            //Reset the text field.
            textField.requestFocusInWindow();
            textField.setText("");

            //Select the new item and make it visible.
            sunday.setSelectedIndex(index);
            sunday.ensureIndexIsVisible(index);
        }

        protected boolean alreadyInList(String name) {
            return sundayListModel.contains(name);
        }

        /**
         * Gives notification that there was an insert into the document.  The
         * range given by the DocumentEvent bounds the freshly inserted region.
         *
         * @param e the document event
         */
        @Override
        public void insertUpdate(DocumentEvent e) {

        }

        /**
         * Gives notification that a portion of the document has been
         * removed.  The range is given in terms of what the view last
         * saw (that is, before updating sticky positions).
         *
         * @param e the document event
         */
        @Override
        public void removeUpdate(DocumentEvent e) {

        }

        /**
         * Gives notification that an attribute or set of attributes changed.
         *
         * @param e the document event
         */
        @Override
        public void changedUpdate(DocumentEvent e) {

        }
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
        loadItem.setActionCommand("load");
        loadItem.addActionListener(this);

        menu.add(saveItem);
        menu.add(loadItem);

        return menuBar;
    }

    public void createAndShowGui() {
        GraphicalTrackerApp trackerApp = new GraphicalTrackerApp();
        trackerApp.frame = new JFrame("Pill Tracker");
        trackerApp.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        trackerApp.frame.getContentPane().add(trackerApp.createContentPane());
        trackerApp.frame.setJMenuBar(trackerApp.createMenuBar());
        trackerApp.frame.pack();
        trackerApp.frame.setLocationRelativeTo(null);
        trackerApp.frame.setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage("data/pillobject.png");
        trackerApp.frame.setIconImage(icon);

        JComponent newTrackerApp = new GraphicalTrackerApp();
        JFrame jjFrame = new JFrame();
        jjFrame.setContentPane(newTrackerApp);
        jjFrame.setJMenuBar(createMenuBar());
        jjFrame.pack();
        jjFrame.setVisible(true);

    }

    /**
     * Invoked when an action occurs.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("save".equals(e.getActionCommand())) {
            saveWeek();
        } else if ("load".equals(e.getActionCommand())) {
            loadWeek();
        } else {
            removeAction();
        }
    }

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    @Override
    public void itemStateChanged(ItemEvent e) {

    }


    public void removeAction() {
        //This method can be called only if
        //there's a valid selection
        //so go ahead and remove whatever's selected.
        String name = sunday.getSelectedValue();
        int index = sunday.getSelectedIndex();
        sundayListModel.remove(index);
        if (index == sundayListModel.getSize()) {
            //removed item in last position
            index--;
        }
        sunday.setSelectedIndex(index);
        sunday.ensureIndexIsVisible(index);
        thisWeek.removeSunday(name);
        // !!! NEED TO UPDATE GUI -> OR ADD NEW TOTALS PANEL
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
            this.setVisible(false);
            this.frame.dispose();
            GraphicalTrackerApp trackerApp = new GraphicalTrackerApp();
            trackerApp.thisWeek = jsonReader.read();
            trackerApp.frame = new JFrame("Pill Tracker");
            trackerApp.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            trackerApp.frame.getContentPane().add(trackerApp.createContentPane());
            trackerApp.frame.setJMenuBar(trackerApp.createMenuBar());
            trackerApp.frame.pack();
            trackerApp.frame.setLocationRelativeTo(null);
            trackerApp.frame.setVisible(true);
            Image icon = Toolkit.getDefaultToolkit().getImage("data/pillobject.png");
            trackerApp.frame.setIconImage(icon);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to read from file");
        }
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
//   UNUSED CURRENT TRACKER APP  //
    private Scanner input;

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


    private void createMonday() {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        thisWeek.addMonday("pill3");
        for (String s : thisWeek.getMonday().keySet()) {
            // fill the model with elements of Sunday!!!
            listModel.addElement(s);
        }
        // new monday for each day of the week with model
        JList<String> monday = new JList<>(listModel);
        monday.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        monday.setVisibleRowCount(4);
        JScrollPane scrollPane = new JScrollPane(monday);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // border around showing the day of the week
        JPanel container = new JPanel(new BorderLayout());
        container.add(scrollPane);
        container.setBorder(BorderFactory.createTitledBorder("Monday"));
        add(container);
    }
}