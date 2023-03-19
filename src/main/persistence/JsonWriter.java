package persistence;

// based on Workroom app; link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.DiaryBook;
import org.json.JSONObject;

import java.io.*;

// Represents a writer that writes JSON representation of diary book to file
public class JsonWriter {
    private static final int TAB = 5;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of diary book to file
    public void write(DiaryBook db) {
        JSONObject json = db.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
