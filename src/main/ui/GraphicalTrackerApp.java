package ui;

import model.Day;
import model.Event;
import model.EventLog;
import model.Week;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

// graphical interface for the tracker app
// some basic code sourced from https://stackoverflow.com/questions/42867271/displaying-multiple-jlist-to-jframe
public class GraphicalTrackerApp extends JFrame implements ActionListener, ItemListener, ListSelectionListener {
    // week definitions
    private Week thisWeek;
    private static final String JSON_STORE = "./data/yourWeek.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    // Swing components
    private JButton addButton;
    private JTextField textField;
    private JButton removeButton;
    private JPanel totalsPane;
    private JTextField numField;
    private JButton targetButton;
    // target reached icon
    private Image coolImage = Toolkit.getDefaultToolkit().getImage("data/coolpillobject.png");
    private Image newImage = coolImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    private ImageIcon scaledCoolImage = new ImageIcon(newImage);
    // JLists for weekdays
    private JList<String> sunday;
    private JList<String> monday;
    private JList<String> tuesday;
    private JList<String> wednesday;
    private JList<String> thursday;
    private JList<String> friday;
    private JList<String> saturday;
    // ListModels for weekdays
    private DefaultListModel<String> sundayListModel;
    private DefaultListModel<String> mondayListModel;
    private DefaultListModel<String> tuesdayListModel;
    private DefaultListModel<String> wednesdayListModel;
    private DefaultListModel<String> thursdayListModel;
    private DefaultListModel<String> fridayListModel;
    private DefaultListModel<String> saturdayListModel;
    // JPanel containers for weekdays
    private JPanel mondayContainer;
    private JPanel tuesdayContainer;
    private JPanel wednesdayContainer;
    private JPanel thursdayContainer;
    private JPanel fridayContainer;
    private JPanel saturdayContainer;
    private JPanel sundayContainer;
    // selected day definitions
    private DefaultListModel<String> selectedListModel;
    private JList<String> selectedList;
    private Day selectedDay;

    // EFFECTS: constructor for the graphical tracker app/frame
    public GraphicalTrackerApp() {
        super("Graphical Pill Tracker");
        startUp();
        setLayout(new GridLayout(0, 7));
        createDaysOfWeek();
        createDaySelector();
        createAddPanel();
        createRemoveButton();
        createTargetButton();
        createTotals();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                printLog(EventLog.getInstance());
            }
        });
        setJMenuBar(createMenuBar());
        Image appIcon = Toolkit.getDefaultToolkit().getImage("data/pillobject.png");
        setIconImage(appIcon);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: prints event to console for each event
    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.println(next.getDescription() + " on " + next.getDate());
        }
    }

    // EFFECTS: instantiates week, json writer and reader
    private void startUp() {
        thisWeek = new Week("Your Weekly Tracker");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        sundayListModel = new DefaultListModel<>();
        mondayListModel = new DefaultListModel<>();
        tuesdayListModel = new DefaultListModel<>();
        wednesdayListModel = new DefaultListModel<>();
        thursdayListModel = new DefaultListModel<>();
        fridayListModel = new DefaultListModel<>();
        saturdayListModel = new DefaultListModel<>();

        sunday = new JList<>(sundayListModel);
        monday = new JList<>(mondayListModel);
        tuesday = new JList<>(tuesdayListModel);
        wednesday = new JList<>(wednesdayListModel);
        thursday = new JList<>(thursdayListModel);
        friday = new JList<>(fridayListModel);
        saturday = new JList<>(saturdayListModel);

        // Default selected day is Sunday as it is at the top of the list
        selectedListModel = sundayListModel;
        selectedList = sunday;
        selectedDay = thisWeek.getSunday();
    }

    // MODIFIES: this
    // EFFECTS: creates weekday components
    private void createDaysOfWeek() {
        createDay(thisWeek.getSunday(), sunday, sundayListModel, sundayContainer);
        createDay(thisWeek.getMonday(), monday, mondayListModel, mondayContainer);
        createDay(thisWeek.getTuesday(), tuesday, tuesdayListModel, tuesdayContainer);
        createDay(thisWeek.getWednesday(), wednesday, wednesdayListModel, wednesdayContainer);
        createDay(thisWeek.getThursday(), thursday, thursdayListModel, thursdayContainer);
        createDay(thisWeek.getFriday(), friday, fridayListModel, fridayContainer);
        createDay(thisWeek.getSaturday(), saturday, saturdayListModel, saturdayContainer);
    }

    // MODIFIES: this
    // EFFECTS: creates a list panel for Sunday
    private void createDay(Day d, JList<String> day, DefaultListModel<String> listModel, JPanel container) {
        createListModel(d, listModel);

        // set given list of day to listModel and configure
        day = new JList<>(listModel);
        day.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        day.setSelectedIndex(0);
        day.addListSelectionListener(this);
        day.setVisibleRowCount(4);
        JScrollPane scrollPane = new JScrollPane(day);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // border around showing the day of the week
        container = new JPanel(new BorderLayout());
        container.add(scrollPane);
        container.setBorder(BorderFactory.createTitledBorder(d.getName().toUpperCase()));
        add(container);
    }


    // EFFECTS: fill the model with elements of Sunday
    public void createListModel(Day d, DefaultListModel<String> listModel) {
        for (String s : d.getMap().keySet()) {
            listModel.addElement(s);
        }
    }

    // MODIFIES: this
    // EFFECTS: create total statements; if total is reached a congratulatory image appears
    public void createTotals() {
        totalsPane = new JPanel();
        totalsPane.setLayout(new BoxLayout(totalsPane, BoxLayout.Y_AXIS));
        totalsPane.add(new JLabel("Weekly Total: " + thisWeek.getWeeklyConsumption()));
        totalsPane.add(new JLabel("Target Total: " + thisWeek.getTargetTotal()));
        totalsPane.add(new JLabel("Last Weeks Total: " + thisWeek.getLastWeek()));
        if (thisWeek.getWeeklyConsumption() == thisWeek.getTargetTotal()) {
            JLabel targetImage = new JLabel(scaledCoolImage);
            totalsPane.add(targetImage);
        }
        add(totalsPane);
    }

    // MODIFIES: this
    // EFFECTS: update total statements; if total is reached a congratulatory image appears
    public void updateTotals() {
        totalsPane.removeAll();
        totalsPane.add(new JLabel("Weekly Total: " + thisWeek.getWeeklyConsumption()));
        totalsPane.add(new JLabel("Target Total: " + thisWeek.getTargetTotal()));
        totalsPane.add(new JLabel("Last Weeks Total: " + thisWeek.getLastWeek()));
        if (thisWeek.getWeeklyConsumption() == thisWeek.getTargetTotal()) {
            JLabel targetImage = new JLabel(scaledCoolImage);
            totalsPane.add(targetImage);
        }
    }

    // MODIFIES: this
    // EFFECTS: add a remove button panel to frame
    public void createRemoveButton() {
        removeButton = new JButton("remove");
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        removeButton.setActionCommand("remove");
        removeButton.addActionListener(new RemoveListener());

        buttonPane.add(removeButton);
        add(buttonPane);
    }

    // MODIFIES: this
    // EFFECTS: add an add button panel to frame
    public void createAddPanel() {
        addButton = new JButton("add");
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        AddListener addListener = new AddListener();
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        addButton.setActionCommand("add");
        addButton.addActionListener(addListener);

        textField = new JTextField(10);
        textField.addActionListener(addListener);
        textField.getDocument().addDocumentListener(addListener);

        buttonPane.add(textField, BorderLayout.CENTER);
        buttonPane.add(addButton, BorderLayout.CENTER);
        add(buttonPane);
    }

    // EFFECTS: creates panel that selects which day to add and remove from
    public void createDaySelector() {
        JPanel daySelector = new JPanel();
        String[] weekDays = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
                "Saturday"};

        // create drop down menu
        final JComboBox comboBox = new JComboBox(weekDays);
        comboBox.setSelectedIndex(0);
        comboBox.addActionListener(e -> {
            String newMode = (String)comboBox.getSelectedItem();
            switch (newMode) {
                case "Sunday":
                    selectedListModel = sundayListModel;
                    selectedList = sunday;
                    selectedDay = thisWeek.getSunday();
                    break;
                case "Monday":
                    selectedListModel = mondayListModel;
                    selectedList = monday;
                    selectedDay = thisWeek.getMonday();
                    break;
                case "Tuesday":
                    selectedListModel = tuesdayListModel;
                    selectedList = tuesday;
                    selectedDay = thisWeek.getTuesday();
                    break;
                case "Wednesday":
                    selectedListModel = wednesdayListModel;
                    selectedList = wednesday;
                    selectedDay = thisWeek.getWednesday();
                    break;
                case "Thursday":
                    selectedListModel = thursdayListModel;
                    selectedList = thursday;
                    selectedDay = thisWeek.getThursday();
                    break;
                case "Friday":
                    selectedListModel = fridayListModel;
                    selectedList = friday;
                    selectedDay = thisWeek.getFriday();
                    break;
                case "Saturday":
                    selectedListModel = saturdayListModel;
                    selectedList = saturday;
                    selectedDay = thisWeek.getSaturday();
                    break;
            }
        });
        daySelector.add(new JLabel("Selected Day:"));
        daySelector.add(comboBox);
        add(daySelector);
    }

    // class that helps add a pill to the weekday
    class AddListener implements ActionListener, DocumentListener {

        // MODIFIES: this
        // EFFECTS: adds the text field item to Sunday and updates the pane
        public void actionPerformed(ActionEvent e) {
            String name = textField.getText();

            //User didn't type in a unique name...
            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                textField.requestFocusInWindow();
                textField.selectAll();
                return;
            }

            int index = selectedList.getSelectedIndex();
                if (index == -1) { //no selection, so insert at beginning
                    index = 0;
                } else {           //add after the selected item
                    index++;
                }

            thisWeek.add(selectedDay, name);
            updateTotals();
            selectedListModel.addElement(textField.getText());

            //Reset the text field.
            textField.requestFocusInWindow();
            textField.setText("");

            //Select the new item and make it visible.
            selectedList.setSelectedIndex(index);
            selectedList.ensureIndexIsVisible(index);

            repaint();
            revalidate();
        }

        // EFFECTS: checks if the string already exists in Sunday
        protected boolean alreadyInList(String name) {
            return selectedListModel.contains(name);
        }

        // EFFECTS: [required implementation]
        // Javadoc: Gives notification that there was an insert into the document.
        // The range given by the DocumentEvent bounds the freshly inserted region.
        @Override
        public void insertUpdate(DocumentEvent e) {}

        // EFFECTS: [required implementation]
        // Javadoc: Gives notification that a portion of the document has been removed.
        // The range is given in terms of what the view last saw (that is, before updating sticky positions).
        @Override
        public void removeUpdate(DocumentEvent e) {}

        // EFFECTS: [required implementation]
        // Javadoc: Gives notification that an attribute or set of attributes changed.
        @Override
        public void changedUpdate(DocumentEvent e) {}
    }


    // class that helps remove a pill from the weekday
    class RemoveListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: removes the selected item from sunday and updates the panes
        public void actionPerformed(ActionEvent e) {
            int index = selectedList.getSelectedIndex();
            String name = selectedListModel.getElementAt(index);
            thisWeek.remove(selectedDay, name);
            selectedListModel.remove(index);
            updateTotals();

            if (index == selectedListModel.getSize()) {
                //removed item in last position
                index--;
            }

            selectedList.setSelectedIndex(index);
            selectedList.ensureIndexIsVisible(index);

            repaint();
            revalidate();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a menu bar to save and load data from JSON file
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

    // EFFECTS: reads whether an action should save or load data from JSON
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("save".equals(e.getActionCommand())) {
            saveWeek();
        } else if ("load".equals(e.getActionCommand())) {
            loadWeek();
        }
    }

    // EFFECTS: [required implementation]
    // Javadoc: Invoked when an item has been selected or deselected by the user.
    // The code written for this method performs the operations that need to occur when an item is selected (or deselected)
    @Override
    public void itemStateChanged(ItemEvent e) {}

    // MODIFIES: this
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

            sundayListModel.clear();
            mondayListModel.clear();
            tuesdayListModel.clear();
            wednesdayListModel.clear();
            thursdayListModel.clear();
            fridayListModel.clear();
            saturdayListModel.clear();

            createListModel(thisWeek.getSunday(), sundayListModel);
            createListModel(thisWeek.getMonday(), mondayListModel);
            createListModel(thisWeek.getTuesday(), tuesdayListModel);
            createListModel(thisWeek.getWednesday(), wednesdayListModel);
            createListModel(thisWeek.getThursday(), thursdayListModel);
            createListModel(thisWeek.getFriday(), fridayListModel);
            createListModel(thisWeek.getSaturday(), saturdayListModel);

            updateTotals();

            repaint();
            revalidate();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: Unable to read from file");
        }
    }



    // MODIFIES: this
    // EFFECTS: displays menu to set target total and sets input as the target
    private void createTargetButton() {
        targetButton = new JButton("set total");
        JPanel buttonPane = new JPanel();
        TargetListener targetListener = new TargetListener();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.PAGE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        targetButton.setActionCommand("set total");
        targetButton.addActionListener(targetListener);

        numField = new JTextField(10);
        numField.addActionListener(targetListener);
        numField.getDocument().addDocumentListener(targetListener);

        buttonPane.add(numField, BorderLayout.CENTER);
        buttonPane.add(targetButton, BorderLayout.CENTER);
        add(buttonPane);
    }

    // class to help update set target
    class TargetListener implements ActionListener, DocumentListener {

        // EFFECTS: sets target total to the text amount and updates panels
        public void actionPerformed(ActionEvent e) {
            int target = Integer.parseInt(numField.getText());
            thisWeek.setTargetTotal(target);

            //Reset the text field.
            numField.requestFocusInWindow();
            numField.setText("");

            updateTotals();

            repaint();
            revalidate();
        }

        // EFFECTS: [required implementation]
        // Javadoc: Gives notification that there was an insert into the document.
        // The range given by the DocumentEvent bounds the freshly inserted region.
        @Override
        public void insertUpdate(DocumentEvent e) {

        }

        // EFFECTS: [required implementation]
        // Javadoc: Gives notification that a portion of the document has been removed.
        // The range is given in terms of what the view last saw (that is, before updating sticky positions).
        @Override
        public void removeUpdate(DocumentEvent e) {

        }

        // EFFECTS: [required implementation]
        // Javadoc: Gives notification that an attribute or set of attributes changed.
        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    // EFFECTS: [required implementation]
    // Javadoc: Called whenever the value of the selection changes.
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}

