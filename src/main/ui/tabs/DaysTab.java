package ui.tabs;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;

import com.toedter.calendar.JDateChooser;

import model.Day;
import model.Event;
import model.Journal;
import model.Tag;
import ui.JournalRunnerGUI;

// SOURCE: Inspired by SmartHome Project

// Represents the tab where all of the action takes place, where the user can add a
// day, an event, edit an event by starring/unstarring it, and adding tags to an event.
// Possesses JDialogs when user input is necessary.
public class DaysTab extends Tab {
    
    private static final Font TITLE_FONT = new Font("MV Boli", Font.PLAIN, 50);
    private static final Font YEAR_FONT = new Font("Times New Roman", Font.BOLD, 40);
    private static final Font MONTH_FONT = new Font("Calibri", Font.ITALIC, 32);
    private static final Font REGULAR_FONT = new Font("Calibri", Font.PLAIN, 24);

    private SimpleDateFormat dateFormat;
    private Day currentDay;

    private JDateChooser chooser;

    private JPanel chooserPanel;
    private JPanel currentPanel;

    private JTextArea nameAnswer;
    private JSlider ratingAnswer;
    private JTextArea quoteAnswer;
    private JTextArea imageAnswer;
    private JTextArea tagAnswer;

    private JDialog addDayPopUp;
    private JDialog addEventPopUp;
    private JDialog addTagPopUp;

    private ImageIcon emptyStar;
    private ImageIcon star;

    // REQUIRES: SmartHomeUI controller that holds this tab
    // EFFECTS: creates report tab with buttons and application status functionality
    public DaysTab(JournalRunnerGUI controller) {
        super(controller);
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.PAGE_AXIS); 
        setLayout(boxLayout);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        emptyStar = new ImageIcon("./data/images/empty-star.jpg");
        Image emptyStarImage = emptyStar.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        emptyStar = new ImageIcon(emptyStarImage);
        star = new ImageIcon("./data/images/star.jpg");
        Image starImage = star.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
        star = new ImageIcon(starImage);

        reload();
    }

    @Override
    // MODIFIES: this
    // EFFECTS: reloads the tab to its initial state, which is the screen that shows
    //          all days and lets the user adds days
    public void reload() {
        cleanScreen();
        loadTitle("Journal Entries: ");
        loadAllDays();
        loadAddDayButton();
        loadAddTodayButton();
    }

    // MODIFIES: this
    // EFFECTS: cleans the screen of this to prepare to draw a new page onto it
    private void cleanScreen() {
        removeAll();
        revalidate();
        getController().repaint();
    }

    // MODIFIES: this
    // EFFECTS: loads a label with given title and adds it to this
    private void loadTitle(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        this.add(titleLabel);
    }

    @SuppressWarnings("methodlength")
    // MODIFIES: this
    // EFFECTS: loads all days in the journal onto this, in order of most recent
    //          year, then most recent month, then least recent day
    private void loadAllDays() {
        Journal journal = getController().getJournal();
        List<Day> days = journal.sortDaysByTime();
        if (days.isEmpty()) {
            displayEmpty();
        } else {
            Day firstDay = days.get(0);
            int oldYear = firstDay.getYear() + 1;
            int oldMonth = firstDay.getMonth() + 1;
            for (Day day : days) {
                int year = day.getYear();
                int month = day.getMonth();
                if (year != oldYear) {
                    displayYear(year);
                    displayMonth(month);
                    oldYear = year;
                    oldMonth = month;
                } else if (month != oldMonth) {
                    displayMonth(month);
                    oldMonth = month;
                }
                loadDate(day);
            }
        }
    }

    // REQUIRES: called only when none of the corresponding object has been recorded so far
    // MODIFIES: this
    // EFFECTS: displays a message telling the user that none of the corresponding object
    //          has been recorded so far, adding it to this
    public void displayEmpty() {
        JLabel emptyLabel = new JLabel("* None recorded yet! Go wild!", JLabel.CENTER);
        emptyLabel.setFont(REGULAR_FONT);
        this.add(emptyLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display given year with YEAR_FONT on this
    public void displayYear(int year) {
        JLabel yearLabel = new JLabel(String.valueOf(year), JLabel.CENTER);
        yearLabel.setFont(YEAR_FONT);
        this.add(yearLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display given month with MONTH_FONT on this
    public void displayMonth(int month) {
        String monthName = Month.of(month).getDisplayName(TextStyle.FULL, Locale.US);
        JLabel monthLabel = new JLabel(monthName, JLabel.CENTER);
        monthLabel.setFont(MONTH_FONT);
        this.add(monthLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates a red JLabel to display given day with REGULAR_FONT on this.
    //          adds a MouseAdapter that allows the user to enter the day and 
    //          see its events when mouse is clicked, changes foreground of label
    //          to blue when mouse enters the label, and changes foreground to
    //          red when mouse exits the label.
    public void loadDate(Day day) {
        JLabel dateLabel = new JLabel(day.getDate() + ": " + day.getNumEvents() + " events", JLabel.CENTER);
        dateLabel.setName(day.toString());
        dateLabel.setFont(REGULAR_FONT);
        dateLabel.setForeground(Color.red);
        dateLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentDay = getController().getJournal().dateRecord(dateLabel.getName());
                loadDayScreen();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                dateLabel.setForeground(Color.blue);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                dateLabel.setForeground(Color.red);
            }
        });
        this.add(dateLabel);
    }

    // MODIFIES: this
    // EFFECTS: loads add day button and adds it to this with font set to REGULAR_FONT.
    //          adds ActionListener that loads new JDialog to add a day if button is clicked.
    public void loadAddDayButton() {
        JButton addDayButton = new JButton("Add Day");
        addDayButton.setFont(REGULAR_FONT);
        addDayButton.addActionListener(e -> loadAddDayPrompt());
        this.add(addDayButton);
    }

    // MODIFIES: this
    // EFFECTS: loads add today button and adds it to this with font set to REGULAR_FONT
    //          adds ActionListener that adds today and reloads the GUI if clicked.
    public void loadAddTodayButton() {
        if (getController().getJournal().dateRecord(LocalDate.now().toString()) == null) {
            JButton addTodayButton = new JButton("Add Today");
            addTodayButton.setFont(REGULAR_FONT);
            addTodayButton.addActionListener(e -> {
                addDay(LocalDate.now().toString());
                getController().reload();
            });
            this.add(addTodayButton);
        }
    }

    // EFFECTS: loads add day prompt with JDateChooser and button to confirm adding
    //          selected day.
    public void loadAddDayPrompt() {
        addDayPopUp = new JDialog();
        addDayPopUp.setModal(true);
        addDayPopUp.setLayout(new FlowLayout(FlowLayout.CENTER));
        JPanel calendarPanel = new JPanel();
        calendarPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        calendarPanel.setPreferredSize(new Dimension(150, 30));
        chooser = new JDateChooser();
        chooser.setLocale(Locale.US);
        calendarPanel.add(chooser);
        addDayPopUp.add(calendarPanel);
        loadConfirmAddDayButton();;
        
        addDayPopUp.pack();
        addDayPopUp.setResizable(false);
        addDayPopUp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: parses day from given yearMonthDate string and adds day to journal if 
    //          it has not yet been added yet. if day was added, return true. otherwise,
    //          return false and display an error message.
    public boolean addDay(String yearMonthDate) {
        Journal journal = getController().getJournal();
        if (journal.dateRecord(yearMonthDate) == null) {
            String[] splitDate = yearMonthDate.split("-");
            int year = Integer.valueOf(splitDate[0]);
            int month = Integer.valueOf(splitDate[1]);
            int date = Integer.valueOf(splitDate[2].substring(0, 2));
            journal.addDay(new Day(year, month, date));
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error: Day already added!", "Fail", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: loads confirm add day button to confirm adding selected day.
    //          adds ActionListener that adds day to journal if not already added,
    //          or displays an error message if the day has already been added or the 
    //          day provided is invalid
    public void loadConfirmAddDayButton() {
        JButton confirmAddDayButton = new JButton("Add This Day");
        confirmAddDayButton.setFont(REGULAR_FONT);
        confirmAddDayButton.addActionListener(e -> {
            Date date = chooser.getDate();
            try {
                String yearMonthDate = dateFormat.format(date);
                if (addDay(yearMonthDate)) {
                    addDayPopUp.dispose();
                    getController().reload();
                }     
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null, "Error: Invalid date!", "Fail", JOptionPane.ERROR_MESSAGE);
            }
            
        });
        addDayPopUp.add(confirmAddDayButton);
    }

    // MODIFIES: this
    // EFFECTS: loads/reloads the tab to the current day's screen, showing the events
    //          recorded under the day and associated characteristics
    public void loadDayScreen() {
        cleanScreen();
        loadBackButton();
        loadAddEventButton();
        loadTitle("Events: ");
        List<Event> events = currentDay.getEvents();
        if (events.isEmpty()) {
            displayEmpty();
        } else {
            Event firstEvent = currentDay.getMostHighlyRated();
            displayEvent(firstEvent);
            for (Event event : events) {
                if (!event.equals(firstEvent)) {
                    displayEvent(event);
                }
            }
        }
        loadAverageRatingButton();
    }

    // MODIFIES: this
    // EFFECTS: loads back button and adds it to this with font set to REGULAR_FONT.
    //          adds ActionListener that sends the user back to the initial page if 
    //          button is clicked.
    public void loadBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setFont(REGULAR_FONT);
        backButton.addActionListener(e -> reload());
        this.add(backButton);
    }

    // MODIFIES: this
    // EFFECTS: loads add event button and adds it to this with font set to REGULAR_FONT.
    //          adds ActionListener that loads new JDialog to add an event if button is 
    //          clicked.
    public void loadAddEventButton() {
        JButton addEventButton = new JButton("Add Event");
        addEventButton.setFont(REGULAR_FONT);
        addEventButton.addActionListener(e -> loadAddEventPrompt());
        this.add(addEventButton);
    }

    // MODIFIES: this
    // EFFECTS: loads show average rating button and adds it to this with font set to REGULAR_FONT.
    //          adds ActionListener that loads average rating and adds it to this if button clicked.
    public void loadAverageRatingButton() {
        JButton averageRatingButton = new JButton("Show Average Rating");
        averageRatingButton.setFont(REGULAR_FONT);
        averageRatingButton.addActionListener(e -> {
            remove(averageRatingButton);
            JLabel averageRating = new JLabel("Average Rating: " + currentDay.getAverageRating() + "/10");
            averageRating.setFont(REGULAR_FONT);
            add(averageRating);
            revalidate();
            repaint();
        });
        this.add(averageRatingButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a new panel stored under currentPanel and adds it to this. 
    //          displays given event and associated characteristics: title, tags, 
    //          rating, quote, image (if it exists). loads an add tag button onto 
    //          the panel.
    public void displayEvent(Event event) {
        currentPanel = new JPanel();
        currentPanel.setLayout(new BoxLayout(currentPanel, BoxLayout.PAGE_AXIS));

        displayTitle(event);
        displayTags(event);
        displayRating(event);
        displayQuote(event);
        displayImage(event);

        loadAddTagButton(event);

        JLabel space = new JLabel(" ", JLabel.CENTER);
        currentPanel.add(space);

        this.add(currentPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display title of given event with REGULAR_FONT 
    //          and in dark gray, as well as a star icon that depends on star status 
    //          (emptyStar if unstarred, star if starred). adds this label to 
    //          currentPanel. adds a MouseListener that allows the user to star or 
    //          unstar the event when label is clicked, updating the icon accordingly.
    public void displayTitle(Event event) {
        String title = event.getTitle();
        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setName(title);
        titleLabel.setFont(REGULAR_FONT);
        titleLabel.setForeground(Color.darkGray);
        updateStarIcon(event, titleLabel);
        titleLabel.setHorizontalTextPosition(JLabel.LEFT);
        titleLabel.setVerticalTextPosition(JLabel.CENTER);
        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                event.flipStar();
                updateStarIcon(event, titleLabel);
            }
        });
        currentPanel.add(titleLabel);
    }

    // REQUIRES: titleLabel has a star or emptyStar icon already
    // EFFECTS: updates star icon on given titleLabel based on given event's starred status
    public void updateStarIcon(Event event, JLabel titleLabel) {
        if (event.isStarred()) {
            titleLabel.setIcon(star);
        } else {
            titleLabel.setIcon(emptyStar);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display rating of given event with REGULAR_FONT 
    //          and in magenta. adds this ratingLabel to currentPanel.
    public void displayRating(Event event) {
        JLabel ratingLabel = new JLabel("Rating: " + event.getRating() + "/10", JLabel.CENTER);
        ratingLabel.setFont(REGULAR_FONT);
        ratingLabel.setForeground(Color.magenta);
        currentPanel.add(ratingLabel);
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display quote of given event with REGULAR_FONT 
    //          and in orange. adds this quoteLabel to currentPanel.
    public void displayQuote(Event event) {
        JLabel quoteLabel = new JLabel("Quote: " + event.getQuote(), JLabel.CENTER);
        quoteLabel.setFont(REGULAR_FONT);
        quoteLabel.setForeground(Color.orange);
        currentPanel.add(quoteLabel);
    }

    // MODIFIES: this
    // EFFECTS: if event has tags, creates a JLabel to display all tags of given event 
    //          with REGULAR_FONT and in gray. adds this tagLabel to currentPanel.
    public void displayTags(Event event) {
        event.sortTags();
        List<Tag> tags = event.getTags();
        if (!tags.isEmpty()) {
            String tagConcat = "";
            for (Tag tag : tags) {
                tagConcat += "#" + tag.getName() + " (" + tag.getNumEvents() + ")   ";
            }
            JLabel tagLabel = new JLabel("Tags: " + tagConcat, JLabel.CENTER);
            tagLabel.setFont(REGULAR_FONT);
            tagLabel.setForeground(Color.gray);
            currentPanel.add(tagLabel);
        }
    }

    // REQUIRES: given event's image relative file path != null and is a valid file path
    // MODIFIES: this
    // EFFECTS: gets event's image from its relative file path and rescales the image
    //          with height 300 and maintained aspect ratio. adds this imageLabel to
    //          currentPanel.
    public void displayImage(Event event) {
        ImageIcon imageIcon = new ImageIcon(event.getImagePath());
        double ratio = (double) imageIcon.getIconWidth() / imageIcon.getIconHeight();
        int newWidth = (int) (300 * ratio);
        Image image = imageIcon.getImage().getScaledInstance(newWidth, 300, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        currentPanel.add(imageLabel);
    }

    // MODIFIES: this
    // EFFECTS: loads add tag button and adds it to currentPanel with font set to REGULAR_FONT.
    //          adds ActionListener that loads new JDialog to add a tag if button is clicked.
    public void loadAddTagButton(Event event) {
        JButton addTagButton = new JButton("Add Tag");
        addTagButton.setFont(REGULAR_FONT);
        addTagButton.addActionListener(e -> {
            loadAddTagPrompt(event);
        });
        currentPanel.add(addTagButton);
    }

    // MODIFIES: this
    // EFFECTS: loads add event JDialog prompt with a name field, rating field, quote field, and 
    //          optional image field, as well as a button to confirm adding event.
    public void loadAddEventPrompt() {
        addEventPopUp = new JDialog();
        addEventPopUp.setModal(true);
        BoxLayout boxLayout = new BoxLayout(addEventPopUp.getContentPane(), BoxLayout.PAGE_AXIS);
        addEventPopUp.setLayout(boxLayout);
        addEventPopUp.setResizable(false);

        chooserPanel = new JPanel(new GridLayout(4, 2, 0, 10));

        loadNameHandler();
        loadRatingHandler();
        loadQuoteHandler();
        loadImageHandler();

        addEventPopUp.add(chooserPanel);

        loadConfirmAddEventButton();

        addEventPopUp.pack();
        addEventPopUp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads name prompt and name answer (text area). adds them to chooserPanel.
    public void loadNameHandler() {
        JLabel namePrompt = new JLabel("Name: ", JLabel.CENTER);
        nameAnswer = new JTextArea();
        nameAnswer.setSize(200, 50);
        chooserPanel.add(namePrompt);
        chooserPanel.add(nameAnswer);
    }
    
    // MODIFIES: this
    // EFFECTS: loads rating prompt and rating answer (slider). adds them to chooserPanel.
    public void loadRatingHandler() {
        JLabel ratingPrompt = new JLabel("Rating (1 to 10): ", JLabel.CENTER);
        ratingAnswer = new JSlider(0, 10, 1);
        ratingAnswer.setPreferredSize(new Dimension(200, 50));
        ratingAnswer.setMinorTickSpacing(1);
        ratingAnswer.setPaintTicks(true);
        ratingAnswer.setPaintTrack(true);
        ratingAnswer.setPaintLabels(true);
        ratingAnswer.setSnapToTicks(true);

        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        for (int i = 0; i <= 10; i++) {
            labelTable.put(i, new JLabel(String.valueOf(i)));
        }
        ratingAnswer.setLabelTable(labelTable);
        chooserPanel.add(ratingPrompt);
        chooserPanel.add(ratingAnswer);
    }

    // MODIFIES: this
    // EFFECTS: loads quote prompt and quote answer (text area). adds them to chooserPanel.
    public void loadQuoteHandler() {
        JLabel quotePrompt = new JLabel("Quote: ", JLabel.CENTER);
        quoteAnswer = new JTextArea();
        quoteAnswer.setSize(200, 150);
        chooserPanel.add(quotePrompt);
        chooserPanel.add(quoteAnswer);
    }

    // MODIFIES: this
    // EFFECTS: loads image prompt and image answer (text area that accepts a file path).
    //          adds them to chooserPanel.
    public void loadImageHandler() {
        JLabel imagePrompt = new JLabel("Image File Path (Leave Blank for no Image): ", JLabel.CENTER);
        imageAnswer = new JTextArea();
        imageAnswer.setSize(200, 50);
        chooserPanel.add(imagePrompt);
        chooserPanel.add(imageAnswer);
    }

    // MODIFIES: this
    // EFFECTS: loads confirm add event button to confirm adding event. adds 
    //          ActionListener that adds event to journal if button clicked
    //          or displays an error message if a file path is provided but 
    //          it is invalid.
    public void loadConfirmAddEventButton() {
        JButton confirmAddEventButton = new JButton("Add This Event");
        confirmAddEventButton.setFont(REGULAR_FONT);
        confirmAddEventButton.addActionListener(e -> {
            addEvent(nameAnswer.getText(), ratingAnswer.getValue(), quoteAnswer.getText(), imageAnswer.getText());
            addEventPopUp.dispose();
            getController().reloadHomeTab();
            loadDayScreen();
        });
        addEventPopUp.add(confirmAddEventButton);
    }

    // MODIFIES: this
    // EFFECTS: adds event to day if no file path is provided or a valid file path is 
    //          provided. displays an error message if a file path is provided but it 
    //          is invalid.
    public void addEvent(String name, int rating, String quote, String imagePath) {
        Event event;
        if (imagePath.equals("")) {
            event = new Event(name, rating, quote);
            currentDay.addEvent(event);
        } else {
            File file = new File(imagePath);
            try {
                if (imagePath.substring(0, 14).equals("./data/images/") && file.exists() && file.isFile()) {
                    event = new Event(name, rating, quote, imagePath);
                } else {
                    JOptionPane.showMessageDialog(null, 
                            "Error: Image Path is invalid!", 
                            "Fail", 
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                currentDay.addEvent(event);
            } catch (StringIndexOutOfBoundsException e) {
                JOptionPane.showMessageDialog(null, "Error: Image Path is invalid!", "Fail", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads add tag JDialog prompt with a text field in which the user can 
    //          type their desired tag, as well as a button to confirm adding tag.
    public void loadAddTagPrompt(Event event) {
        addTagPopUp = new JDialog();
        addTagPopUp.setModal(true);
        BoxLayout boxLayout = new BoxLayout(addTagPopUp.getContentPane(), BoxLayout.PAGE_AXIS);
        addTagPopUp.setLayout(boxLayout);
        addTagPopUp.setResizable(false);

        JPanel chooserPanel = new JPanel(new GridLayout(1, 2, 0, 10));
        JLabel tagPrompt = new JLabel("Tag: ", JLabel.CENTER);
        tagAnswer = new JTextArea();
        tagAnswer.setSize(200, 50);
        chooserPanel.add(tagPrompt);
        chooserPanel.add(tagAnswer);
        addTagPopUp.add(chooserPanel);

        loadConfirmAddTagButton(event);

        addTagPopUp.pack();
        addTagPopUp.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: loads confirm add tag button to confirm adding tag. adds 
    //          ActionListener that, if button is clicked, adds tag to event if 
    //          tag has not already been added to event or displays an error
    //          message saying the tag has already been added if it has
    //          already been added.
    public void loadConfirmAddTagButton(Event event) {
        JButton confirmAddTagButton = new JButton("Add This Tag");
        confirmAddTagButton.setFont(REGULAR_FONT);
        confirmAddTagButton.addActionListener(e -> {
            if (addTag(event, tagAnswer.getText())) {
                addTagPopUp.dispose();
                getController().reloadHomeTab();
                loadDayScreen();
            }
        });
        addTagPopUp.add(confirmAddTagButton);
    }

    // MODIFIES: this
    // EFFECTS: parses tag from given tagName string, getting existing tag if it exists
    //          or creating a new tag if it doesn't exist. adds tag to given event if 
    //          it has not yet been added yet. if tag was added, return true. otherwise,
    //          return false and display an error message.
    public boolean addTag(Event event, String tagName) {
        Tag tag = getController().getJournal().getTagFromName(tagName);
        if (tag == null) {
            tag = new Tag(tagName);
        }
        if (!event.addTag(tag)) {
            JOptionPane.showMessageDialog(null, "Error: Tag already added!", "Fail", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}