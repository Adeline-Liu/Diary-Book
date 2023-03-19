package ui;

import model.Diary;
import model.DiaryBook;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Event;
import model.EventLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// the option and functionality page of the app
public class OptionsPage extends JFrame {
    JFrame optionsFrame;
    JFrame writeFrame;
    JFrame viewFrame;
    JFrame searchFrame;
    JFrame deleteFrame;
    JFrame saveFrame;
    JFrame loadFrame;

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;

    private static final String JSON_STORE = "./data/diaryBook.json";

    private DiaryBook book;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs the main frame of the app
    public OptionsPage() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        book = new DiaryBook("My Diary Book");

        optionsFrame = new JFrame("My Diary Book");
        initializeOptionsFrame();
    }

    // MODIFIES: this
    // EFFECTS: initializes the option frame of the app
    public void initializeOptionsFrame() {
        optionsFrame.setLayout(new BorderLayout());
        optionsFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        optionsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        optionsFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                for (Event e : EventLog.getInstance()) {
                    System.out.println(e.toString());
                }
                System.exit(0);
            }
        });

        optionsFrame.getContentPane().setLayout(new BorderLayout(10, 10));
        optionsFrame.setLocationRelativeTo(null);

        addButtonsAndMenus();

        optionsFrame.setVisible(true);
    }

    // EFFECTS: initializes the functionality frame of the app
    public void initializeSubFrames(JFrame frame) {
        frame.setLayout(new BorderLayout());
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(10, 10));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates buttons and menu of the ui
    private void addButtonsAndMenus() {
        createWriteButton();
        createDeleteButton();
        createViewMenu();
        createSaveButton();
        createLoadButton();
    }

    // MODIFIES: this
    // EFFECTS: creates load button
    private void createLoadButton() {
        JButton load = new JButton("Load My Diaries");
        optionsFrame.add(load, BorderLayout.NORTH);
        load.setFont(new Font("Arial", Font.PLAIN, 22));
        load.setBackground(new Color(26, 101, 101));
        load.addActionListener(e -> loadDiaryBook());
    }

    // MODIFIES: this
    // EFFECTS: creates save button
    private void createSaveButton() {
        JButton save = new JButton("Save My Diaries");
        optionsFrame.add(save, BorderLayout.SOUTH);
        save.setFont(new Font("Arial", Font.PLAIN, 22));
        save.setBackground(new Color(26, 101, 101));
        save.addActionListener(e -> saveDiaryBook());
    }

    // MODIFIES: this
    // EFFECTS: creates view menu
    private void createViewMenu() {
        JMenuBar view = new JMenuBar();

        view.setBackground(new Color(133, 224, 224));
        optionsFrame.add(view, BorderLayout.WEST);

        JMenu viewOrSearch = new JMenu("View Diaries");
        view.add(viewOrSearch);
        viewOrSearch.setFont(new Font("Arial", Font.PLAIN, 22));

        JMenuItem viewAll = new JMenu("View All");
        viewAll.setFont(new Font("Arial", Font.PLAIN, 20));
        JMenuItem search = new JMenu("Search");
        search.setFont(new Font("Arial", Font.PLAIN, 20));

        viewOrSearch.add(viewAll);
        viewOrSearch.add(search);

        viewAll.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                viewDiaries();
            }
        });

        search.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                searchForDiaries();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates delete button
    private void createDeleteButton() {
        JButton delete = new JButton("Delete a Diary");
        optionsFrame.add(delete, BorderLayout.EAST);
        delete.setFont(new Font("Arial", Font.PLAIN, 22));
        delete.setBackground(new Color(42, 162, 162));
        delete.addActionListener(e -> deleteDiary());
    }

    // MODIFIES: this
    // EFFECTS: creates write button
    private void createWriteButton() {
        JButton write = new JButton("Write a Diary");
        optionsFrame.add(write, BorderLayout.CENTER);
        write.setFont(new Font("Arial", Font.PLAIN, 22));
        write.setBackground(new Color(74, 207, 207));
        write.addActionListener(e -> writeDiary());
    }

    // EFFECTS: saves the diary book to file
    private void saveDiaryBook() {
        saveFrame = new JFrame();
        initializeSubFrames(saveFrame);

        try {
            jsonWriter.open();
            jsonWriter.write(book);
            jsonWriter.close();

            JLabel label = new JLabel("Saved " + book.getName() + " to " + JSON_STORE);
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            saveFrame.add(label);

        } catch (FileNotFoundException e) {
            JLabel notFoundLabel = new JLabel("Unable to write to file: " + JSON_STORE);
            notFoundLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            saveFrame.add(notFoundLabel);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads diary book from file
    private void loadDiaryBook() {
        loadFrame = new JFrame();
        initializeSubFrames(loadFrame);

        try {
            book = jsonReader.read();

            JLabel label = new JLabel("Loaded " + book.getName() + " from " + JSON_STORE);
            label.setFont(new Font("Arial", Font.PLAIN, 20));
            loadFrame.add(label);

        } catch (IOException e) {
            JLabel exceptionLabel = new JLabel("Unable to read from file: " + JSON_STORE);
            exceptionLabel.setFont(new Font("Arial", Font.PLAIN, 20));
            loadFrame.add(exceptionLabel);
        }
    }

    // MODIFIES: this
    // EFFECTS: writes a new diary and adds it to the diary list
    private void writeDiary() {
        writeFrame = new JFrame();
        initializeSubFrames(writeFrame);

        Diary d = new Diary();
        book.addDiary(d);

        Box verticalBox = Box.createVerticalBox();

        Box date = Box.createHorizontalBox();
        diaryItemDate(d, date);

        Box weather = Box.createHorizontalBox();
        diaryItemWeather(d, weather);

        Box mood = Box.createHorizontalBox();
        diaryItemMood(d, mood);

        Box text = Box.createHorizontalBox();
        diaryItemText(d, text);

        verticalBox.add(date);
        verticalBox.add(weather);
        verticalBox.add(mood);
        verticalBox.add(text);

        writeFrame.add(verticalBox, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: creates an input area for date
    public void diaryItemDate(Diary d, Box date) {
        JLabel label1 = new JLabel("What's the date today?");
        label1.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField field1 = new JTextField(20);
        field1.setFont(new Font("Arial", Font.PLAIN, 18));

        date.add(label1);
        date.add(Box.createHorizontalStrut(90));
        date.add(field1);

        JButton done = new JButton("Done!");
        done.setFont(new Font("Arial", Font.PLAIN, 18));
        done.setBackground(new Color(152, 230, 230));
        date.add(done);
        done.addActionListener(e -> d.setDate(field1.getText()));
    }

    // MODIFIES: this
    // EFFECTS: creates an input area for weather
    public void diaryItemWeather(Diary d, Box weather) {
        JLabel label2 = new JLabel("How's the weather today?");
        label2.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField field2 = new JTextField(20);
        field2.setFont(new Font("Arial", Font.PLAIN, 18));

        weather.add(label2);
        weather.add(Box.createHorizontalStrut(68));
        weather.add(field2);

        JButton done = new JButton("Done!");
        done.setFont(new Font("Arial", Font.PLAIN, 18));
        done.setBackground(new Color(152, 230, 230));
        weather.add(done);
        done.addActionListener(e -> d.setWeather(field2.getText()));
    }

    // MODIFIES: this
    // EFFECTS: creates an input area for mood
    public void diaryItemMood(Diary d, Box mood) {
        JLabel label3 = new JLabel("How do you feel right now?");
        label3.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField field3 = new JTextField(20);
        field3.setFont(new Font("Arial", Font.PLAIN, 18));

        mood.add(label3);
        mood.add(Box.createHorizontalStrut(58));
        mood.add(field3);

        JButton done = new JButton("Done!");
        done.setFont(new Font("Arial", Font.PLAIN, 18));
        done.setBackground(new Color(152, 230, 230));
        mood.add(done);
        done.addActionListener(e -> d.setMood(field3.getText()));
    }

    // MODIFIES: this
    // EFFECTS: creates an input area for diary text
    public void diaryItemText(Diary d, Box text) {
        JLabel label4 = new JLabel("Anything you'd like to write about?");
        label4.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField field4 = new JTextField(50);
        field4.setFont(new Font("Arial", Font.PLAIN, 18));

        text.add(label4);
        text.add(Box.createHorizontalStrut(5));
        text.add(field4);

        JButton done = new JButton("Finish");
        done.setFont(new Font("Arial", Font.PLAIN, 18));
        done.setBackground(new Color(152, 230, 230));
        text.add(done);
        done.addActionListener(e -> {
            d.setText(field4.getText());

            JLabel finish = new JLabel("Your diary has been added!");
            finish.setFont(new Font("Arial", Font.PLAIN, 18));
            writeFrame.add(finish, BorderLayout.AFTER_LAST_LINE);
            writeFrame.pack();
        });
    }

    // EFFECTS: views all the previous diaries
    private void viewDiaries() {
        viewFrame = new JFrame();

        viewFrame.setLayout(new GridLayout(2000, 1000));
        viewFrame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        viewFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewFrame.getContentPane().setLayout(new GridLayout(0, 1));
        viewFrame.setLocationRelativeTo(null);
        viewFrame.setVisible(true);

        JLabel label = new JLabel("Here are all the diaries you've kept :)");
        label.setFont(new Font("Arial", Font.PLAIN, 22));
        viewFrame.add(label);

        for (Diary d : book.getDiaryList()) {
            String s = book.viewDiary(d);

            JTextArea diary = new JTextArea(s);
            diary.setFont(new Font("Arial", Font.PLAIN, 20));
            viewFrame.add(diary, BorderLayout.AFTER_LAST_LINE);

            viewFrame.pack();
        }
    }

    // EFFECTS: views a diary on a specific date
    private void searchForDiaries() {
        searchFrame = new JFrame();
        initializeSubFrames(searchFrame);

        Box searchLine = Box.createHorizontalBox();

        JLabel label = new JLabel("Which day's diary do you want to view?");
        label.setFont(new Font("Arial", Font.PLAIN, 22));
        searchLine.add(label);

        JTextField dateInput = new JTextField(20);
        dateInput.setFont(new Font("Arial", Font.PLAIN, 22));
        searchLine.add(dateInput);

        JButton doneInput = new JButton("Search");
        doneInput.setFont(new Font("Arial", Font.PLAIN, 22));
        searchLine.add(doneInput);

        searchFrame.add(searchLine, BorderLayout.NORTH);

        doneInput.addActionListener(e -> {

            String date = dateInput.getText();
            Diary d = book.searchDiary(date);

            performSearch(d);
        });
    }

    // MODIFIES: this
    // EFFECTS: performs the search function of the button
    public void performSearch(Diary d) {
        if (d.getDate().equals("")) {
            JLabel emptyDiary = new JLabel("It seems you didn't keep a diary on this day");
            emptyDiary.setFont(new Font("Arial", Font.PLAIN, 22));
            searchFrame.add(emptyDiary, BorderLayout.AFTER_LAST_LINE);
            searchFrame.pack();
        } else {
            String s = book.viewDiary(d);
            JTextArea diary = new JTextArea(s);
            diary.setFont(new Font("Arial", Font.PLAIN, 20));
            searchFrame.add(diary, BorderLayout.AFTER_LAST_LINE);
            searchFrame.pack();
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a diary on a specific date
    private void deleteDiary() {
        deleteFrame = new JFrame();
        initializeSubFrames(deleteFrame);

        Box deleteLine = Box.createHorizontalBox();

        JLabel label = new JLabel("Which day's diary do you want to delete?");
        label.setFont(new Font("Arial", Font.PLAIN, 22));
        deleteLine.add(label);

        JTextField dateInput = new JTextField(20);
        dateInput.setFont(new Font("Arial", Font.PLAIN, 22));
        deleteLine.add(dateInput);

        JButton doneInput = new JButton("Delete");
        doneInput.setFont(new Font("Arial", Font.PLAIN, 22));
        deleteLine.add(doneInput);

        deleteFrame.add(deleteLine, BorderLayout.NORTH);

        deleteFrame.pack();

        doneInput.addActionListener(e -> {
            String date = dateInput.getText();
            book.deleteDiary(date);
            JLabel delete = new JLabel("Your diary has been deleted!");
            delete.setFont(new Font("Arial", Font.PLAIN, 22));
            deleteFrame.add(delete);
            deleteFrame.pack();
        });
    }
}
