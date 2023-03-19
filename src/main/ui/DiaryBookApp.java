package ui;

// based on Teller app; link below
// https://github.students.cs.ubc.ca/CPSC210/TellerApp
// based on Workroom app; link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Diary;
import model.DiaryBook;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Diary Book application
public class DiaryBookApp {
    private static final String JSON_STORE = "./data/diaryBook.json";

    private Scanner input = new Scanner(System.in);

    private DiaryBook book;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the diary book application
    public DiaryBookApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runDiaryBook();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runDiaryBook() {
        boolean keepGoing = true;
        String command;

        initialize();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            input.nextLine();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("Goodbye! Hope you had a good day! :D");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        switch (command) {
            case "w":
                writeDiary();
                break;
            case "s":
                searchForDiaries();
                break;
            case "v":
                viewDiaries();
                break;
            case "d":
                deleteDiary();
                break;
            case "l":
                loadDiaryBook();
                break;
            case "b":
                saveDiaryBook();
                break;
            default:
                System.out.println("Selection not valid :(");
                break;
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes a diary on a specific date
    private void deleteDiary() {
        System.out.println("Which day's diary do you want to delete?");

        String date = input.nextLine();
        book.deleteDiary(date);

        System.out.println("Your diary has been deleted!");
    }

    // EFFECTS: views a diary on a specific date
    private void searchForDiaries() {
        System.out.println("Which day's diary do you want to view?");

        String date = input.nextLine();
        Diary d = book.searchDiary(date);

        if (d.getDate().equals("")) {
            System.out.println("It seems you didn't keep a diary on this day :O");
        } else {
            String s = book.viewDiary(d);
            System.out.println(s);
        }
    }

    // EFFECTS: views all the previous diaries
    private void viewDiaries() {
        System.out.println("Here are all the diaries you've kept :)");

        for (Diary d : book.getDiaryList()) {
            String s = book.viewDiary(d);
            System.out.println(s);
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: writes a new diary and adds it to the diary list
    private void writeDiary() {
        Diary d = new Diary();
        book.addDiary(d);

        System.out.println("What's the date today?");
        String date = input.nextLine();
        d.setDate(date);

        System.out.println("How's the weather today?");
        String weather = input.nextLine();
        d.setWeather(weather);

        System.out.println("How do you feel right now?");
        String mood = input.nextLine();
        d.setMood(mood);

        System.out.println("Anything you'd like to write about?");
        String text = input.nextLine();
        d.setText(text);

        System.out.println("Your diary has been added successfully :)");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nPlease select from:");
        System.out.println("\tw --> Write a diary");
        System.out.println("\tv --> View my diaries");
        System.out.println("\ts --> Search for a diary");
        System.out.println("\td --> Delete my diary");
        System.out.println("\tl --> Load diary book from file");
        System.out.println("\tb --> Save diary book to file");
        System.out.println("\tq --> Quit");
    }

    // MODIFIES: this
    // EFFECTS: initializes a diary book
    private void initialize() {
        book = new DiaryBook("My Diary Book");
        System.out.println("Hey, welcome to your own diary book! :D");
    }

    // EFFECTS: saves the diary book to file
    private void saveDiaryBook() {
        try {
            jsonWriter.open();
            jsonWriter.write(book);
            jsonWriter.close();
            System.out.println("Saved " + book.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads diary book from file
    private void loadDiaryBook() {
        try {
            book = jsonReader.read();
            System.out.println("Loaded " + book.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}