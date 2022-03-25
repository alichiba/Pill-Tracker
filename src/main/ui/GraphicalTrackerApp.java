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
    JButton removeButton;
    JPanel sundayContainer;
    JPanel totalsPane;
    JTextField numField;
    JButton targetButton;
    Image coolImage = Toolkit.getDefaultToolkit().getImage("data/coolpillobject.png");
    Image newImage = coolImage.getScaledInstance(50, 50, Image.SCALE_DEFAULT);
    ImageIcon scaledCoolImage = new ImageIcon(newImage);


    public GraphicalTrackerApp() {
        super("Graphical Pill Tracker");
        startUp();
        setLayout(new GridLayout(0, 7));
        createDaysOfWeek();
        createRemoveButton();
        createAddPanel();
        createTargetButton();
        createTotals();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMenuBar());
        Image appIcon = Toolkit.getDefaultToolkit().getImage("data/pillobject.png");
        setIconImage(appIcon);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
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
            createSunday();
        }
    }

    private void createSunday() {
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
        sundayContainer = new JPanel(new BorderLayout());
        sundayContainer.add(scrollPane);
        sundayContainer.setBorder(BorderFactory.createTitledBorder("Sunday"));
        add(sundayContainer);
    }


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
//        add(totalsPane, BorderLayout.PAGE_END);
//        totalsPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(totalsPane);
    }

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


    class AddListener implements ActionListener, DocumentListener {

        public AddListener() {}

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

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
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

    /**
     * Invoked when an action occurs.
     */
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
        repaint();
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

    class TargetListener implements ActionListener, DocumentListener {
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

    //////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////
//   UNUSED CURRENT TRACKER APP  //
    private Scanner input;

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

//    GraphicalTrackerApp trackerApp = new GraphicalTrackerApp();
//            trackerApp.thisWeek = jsonReader.read();
//            JComponent newTrackerApp = trackerApp;
//            JFrame jjFrame = new JFrame();
//            jjFrame.setContentPane(newTrackerApp);
//            jjFrame.setJMenuBar(createMenuBar());
//            Image appIcon = Toolkit.getDefaultToolkit().getImage("data/pillobject.png");
//            jjFrame.setIconImage(appIcon);
//            jjFrame.pack();
//            jjFrame.setVisible(true);