package ui;

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
// basic code sourced from https://stackoverflow.com/questions/42867271/displaying-multiple-jlist-to-jframe
public class GraphicalTrackerApp extends JFrame implements ActionListener, ItemListener, ListSelectionListener {
    private Week thisWeek;
    private static final String JSON_STORE = "./data/yourWeek.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String[] WEEK_DAYS = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
            "Saturday"};
    private JList<String> sunday;
    private DefaultListModel<String> sundayListModel;
    private JButton addButton;
    private JTextField textField;
    private JButton removeButton;
    private JPanel sundayContainer;
    private JPanel totalsPane;
    private JTextField numField;
    private JButton targetButton;
    private Image coolImage = Toolkit.getDefaultToolkit().getImage("data/coolpillobject.png");
    private Image newImage = coolImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    private ImageIcon scaledCoolImage = new ImageIcon(newImage);

    // EFFECTS: constructor for the graphical tracker app/frame
    public GraphicalTrackerApp() {
        super("Graphical Pill Tracker");
        startUp();
        setLayout(new GridLayout(0, 7));
        createDaysOfWeek();
        createRemoveButton();
        createAddPanel();
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
    }

    // MODIFIES: this
    // EFFECTS: creates weekday components
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
            createSunday();
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a list panel for Sunday
    private void createSunday() {
        sundayListModel = new DefaultListModel<>();
        for (String s : thisWeek.getSunday().keySet()) {
            // fill the model with elements of Sunday
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
        sundayContainer = new JPanel(new BorderLayout());
        sundayContainer.add(scrollPane);
        sundayContainer.setBorder(BorderFactory.createTitledBorder("Sunday"));
        add(sundayContainer);
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

            int index = sunday.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }

            thisWeek.addSunday(name);
            updateTotals();
            //If we just wanted to add to the end, we'd do this:
            sundayListModel.addElement(textField.getText());

            //Reset the text field.
            textField.requestFocusInWindow();
            textField.setText("");

            //Select the new item and make it visible.
            sunday.setSelectedIndex(index);
            sunday.ensureIndexIsVisible(index);

            repaint();
            revalidate();
        }

        // EFFECTS: checks if the string already exists in Sunday
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

    // class that helps remove a pill from the weekday
    class RemoveListener implements ActionListener {

        // MODIFIES: this
        // EFFECTS: removes the selected item from sunday and updates the panes
        public void actionPerformed(ActionEvent e) {
            int index = sunday.getSelectedIndex();
            String name = sundayListModel.getElementAt(index);
            thisWeek.removeSunday(name);
            sundayListModel.remove(index);
            updateTotals();

            if (index == sundayListModel.getSize()) {
                //removed item in last position
                index--;
            }

            sunday.setSelectedIndex(index);
            sunday.ensureIndexIsVisible(index);

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

    /**
     * Invoked when an item has been selected or deselected by the user.
     * The code written for this method performs the operations
     * that need to occur when an item is selected (or deselected).
     */
    @Override
    public void itemStateChanged(ItemEvent e) {

    }

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
            for (String s : thisWeek.getSunday().keySet()) {
                // fill the model with elements of Sunday!!!
                sundayListModel.addElement(s);
            }

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

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {

    }
}

