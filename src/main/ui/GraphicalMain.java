package ui;

import javax.swing.*;
import ui.GraphicalTrackerApp;

public class GraphicalMain {
    public static void main(String[] args) {
        GraphicalTrackerApp graphical = new GraphicalTrackerApp();
        SwingUtilities.invokeLater(() -> graphical.createAndShowGui());
    }
}
