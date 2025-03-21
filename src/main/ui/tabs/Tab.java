package ui.tabs;

import javax.swing.*;

import ui.JournalRunnerGUI;

public abstract class Tab extends JPanel {

    private final JournalRunnerGUI controller;

    // REQUIRES: JournalRunnerGUI controller that holds this tab
    // EFFECTS: sets the controller of this tab to the JournalRunnerGUI class instance
    public Tab(JournalRunnerGUI controller) {
        this.controller = controller;
    }

    // EFFECTS: returns the JournalRunnerGUI controller for this tab
    public JournalRunnerGUI getController() {
        return controller;
    }

    // MODIFIES: this
    // EFFECTS: reloads the tab to its initial state
    public abstract void reload();

}
