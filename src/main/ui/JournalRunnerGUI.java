package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import model.EventLog;
import model.EventToLog;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.tabs.DaysTab;
import ui.tabs.HomeTab;
import ui.tabs.Tab;

// SOURCE: Inspired by SmartHome Project

// Represents an instance of the GUI based UI for EasyJournal.
public class JournalRunnerGUI extends JFrame {

    public static final int HOME_TAB_INDEX = 0;
    public static final int DAYS_TAB_INDEX = 1;
    
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private Journal journal;
    private JMenuBar menuBar;
    private JMenu optionsMenu;
    private JMenuItem loadItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;

    private JTabbedPane navBar;
    private Tab homeTab;
    private Tab daysTab;

    private static final String JSON_STORE = "./data/persistence/journal.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: creates an instance of the JournalRunner GUI application
    public JournalRunnerGUI() {
        init();
    }

    // MODIFIES: this
    // EFFECTS: initializes the application with the starting values
    public void init() {
        setTitle("EasyJournal");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        journal = new Journal();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        
        loadMenuBar();
        loadTabs();

        setVisible(true);

        addWindowListener(new WindowAdapter() {
                            
            // Anonymous class to override windowClosing event
            public void windowClosing(WindowEvent e){
                terminate();
            }

        });
    }

    // MODIFIES: this
    // EFFECTS: loads the menu bar for the GUI application, with options to load
    //          data, save data, or exit the application
    private void loadMenuBar() {
        menuBar = new JMenuBar();
        optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        
        loadItem = new JMenuItem("Load");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        loadItem.addActionListener(e -> loadJournal());
        saveItem.addActionListener(e -> saveJournal());
        exitItem.addActionListener(e -> terminate());

        optionsMenu.add(loadItem);
        optionsMenu.add(saveItem);
        optionsMenu.add(exitItem);

        setJMenuBar(menuBar);
    }

    // MODIFIES: this
    // EFFECTS: adds home tab and days to this UI
    private void loadTabs() {
        navBar = new JTabbedPane();
        navBar.setTabPlacement(JTabbedPane.TOP);

        homeTab = new HomeTab(this);
        daysTab = new DaysTab(this);

        JScrollPane daysTabScroller = new JScrollPane(daysTab);

        navBar.add(homeTab, HOME_TAB_INDEX);
        navBar.setTitleAt(HOME_TAB_INDEX, "Home");
        navBar.add(daysTabScroller, DAYS_TAB_INDEX);
        navBar.setTitleAt(DAYS_TAB_INDEX, "My Days");

        add(navBar);
    }

    public Journal getJournal() {
        return journal;
    }

    public JTabbedPane getTabbedPane() {
        return navBar;
    }

    // MODIFIES: this
    // EFFECTS: loads journal from file
    public void loadJournal() {
        try {
            journal = jsonReader.read();
            reload();
            JOptionPane.showMessageDialog(null,
                    "Loaded journal from " + JSON_STORE,
                    "Success!",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, 
                    "Unable to read from file: " + JSON_STORE, 
                    "Success!", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // MODIFIES: this
    // EFFECTS: Reloads journal to its starting view with updated data
    public void reload() {
        reloadHomeTab();
        reloadDaysTab();
    }

    // MODIFIES: this
    // EFFECTS: Reloads home tab with updated data
    public void reloadHomeTab() {
        homeTab.reload();
    }
    
    // MODIFIES: this
    // EFFECTS: Reloads days tab to its starting view with updated data
    public void reloadDaysTab() {
        daysTab.reload();
    }

    // EFFECTS: saves the journal to file
    public void saveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, 
                    "Saved journal to " + JSON_STORE, 
                    "Success!", 
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, 
                    "Unable to write to file: " + JSON_STORE, 
                    "Success!", 
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // MODIFIES: this
    // EFFECTS: prints all logged events to console, closes the window, and exits the program
    public void terminate() {
        dispose();
        for (EventToLog etl : EventLog.getInstance()) {
            System.out.println(etl.getDescription());
        }
        System.exit(0);
    }
    
}
