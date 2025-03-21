package ui.tabs;

import ui.JournalRunnerGUI;

import javax.swing.*;

import model.Day;
import model.Journal;
import model.Tag;

import java.awt.*;
import java.util.List;

public class HomeTab extends Tab {

    private static final String INIT_GREETING = "Welcome back! Here are your user statistics: ";
    private static final int CONTENT_DIV = 4;

    private static final Font GREETING_FONT = new Font("DejaVu Sans", Font.ITALIC, 30);
    private static final Font TITLE_FONT = new Font("MV Boli", Font.BOLD, 20);
    private static final Font STAT_FONT = new Font("MV Boli", Font.PLAIN, 20);

    // EFFECTS: constructs a home tab for console with buttons and a greeting
    public HomeTab(JournalRunnerGUI controller) {
        super(controller);
        setLayout(new GridLayout(4, 1));
        reload();
    }

    @Override
    // EFFECTS: reloads this page with updated statistics
    public void reload() {
        removeAll();
        placeGreeting();
        placeMostEventfulDays();
        placeTopDays();
        placeTopTags();
    }

    //EFFECTS: creates greeting at top of console
    private void placeGreeting() {
        JLabel greeting = new JLabel(INIT_GREETING, JLabel.CENTER);
        greeting.setSize(WIDTH, HEIGHT / 4);
        greeting.setFont(GREETING_FONT);
        this.add(greeting);
    }

    // EFFECTS: displays days in user's journal with most events recorded. if no days
    //          added so far, tell user that no days have been recorded yet.
    public void placeMostEventfulDays() {
        Journal journal = getController().getJournal();
        JPanel eventfulDaysPanel = setupStatsPanel("Most Eventful Days: ");
        if (journal.getDays().isEmpty()) {
            placeEmptyEntry(eventfulDaysPanel);
        } else {
            List<Day> busiestDays = journal.getMostEventfulDays();
            for (Day day : busiestDays) {
                placeEntry(eventfulDaysPanel, "* " + day + " (" + day.getNumEvents() + ")");
            }
        }
        add(eventfulDaysPanel);
    }

    // EFFECTS: displays most highly rated days in user's journal. if no days added
    //          so far, tell user that no days have been recorded yet.
    public void placeTopDays() {
        Journal journal = getController().getJournal();
        JPanel topDaysPanel = setupStatsPanel("Top Days: ");
        if (journal.getDays().isEmpty()) {
            placeEmptyEntry(topDaysPanel);
        } else {
            List<Day> topDays = journal.getTopDays();
            for (Day day : topDays) {
                placeEntry(topDaysPanel, "* " + day + " (" + day.getAverageRating() + "/10)");
            }
        }
        add(topDaysPanel);
    }

    // EFFECTS: displays most used tags for user's journal. if no tags added so far, 
    //          tell user that no tags have been recorded yet.
    public void placeTopTags() {
        Journal journal = getController().getJournal();
        JPanel topTagsPanel = setupStatsPanel("Top Tags: ");
        if (journal.getTags().isEmpty()) {
            placeEmptyEntry(topTagsPanel);
        } else {
            List<Tag> topTags = journal.getTopTags();
            for (Tag tag : topTags) {
                placeEntry(topTagsPanel, "* " + tag.getName() + " (" + tag.getNumEvents() + ")   ");
            }
        }
        add(topTagsPanel);
    }

    // EFFECTS: sets up display for a statistics panel and puts title on the panel
    private JPanel setupStatsPanel(String title) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));
        JLabel titleEntry = initializeEntryLabel(title, TITLE_FONT);
        panel.add(titleEntry);
        return panel;
    }

    // REQUIRES: called only when none of the corresponding object has been recorded so far
    // EFFECTS: displays a message telling the user that none of the corresponding object
    //          has been recorded so far
    private void placeEmptyEntry(JPanel panel) {
        JLabel entry = initializeEntryLabel("* None yet! Go wild!", STAT_FONT);
        panel.add(entry);
    }

    // EFFECTS: adds an entry with the given text to the given panel
    private void placeEntry(JPanel panel, String text) {
        JLabel entry = initializeEntryLabel(text, STAT_FONT);
        panel.add(entry);
    }

    // EFFECTS: initializes entry label with the given title and font
    private JLabel initializeEntryLabel(String title, Font font) {
        JLabel entry = new JLabel(title, JLabel.CENTER);
        entry.setSize(WIDTH, HEIGHT / (4 * CONTENT_DIV));
        entry.setFont(font);
        return entry;
    }

}
