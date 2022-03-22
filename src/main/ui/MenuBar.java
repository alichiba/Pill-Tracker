package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MenuBar {
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
        saveItem = new JMenuItem("Save week to file",
                KeyEvent.VK_T);
        saveItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        loadItem = new JMenuItem("Load week from file",
                KeyEvent.VK_L);
        loadItem.setMnemonic(KeyEvent.VK_B);
        menu.add(saveItem);
        menu.add(loadItem);

        saveItem.setActionCommand("save");
        loadItem.setActionCommand("load");

        return menuBar;
    }
}
