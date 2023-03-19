package persistence;

// based on Workroom app; link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Diary;
import model.DiaryBook;
import org.json.*;

// Represents a reader that reads diaries from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads diary book from file and returns it
    // throws IOException if an error occurs reading data from file
    public DiaryBook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDiaryBook(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses diary book from JSON object and returns it
    private DiaryBook parseDiaryBook(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        DiaryBook db = new DiaryBook(name);
        addDiaries(db, jsonObject);
        return db;
    }

    // MODIFIES: db
    // EFFECTS: parses diaries from JSON object and adds them to the diary book
    private void addDiaries(DiaryBook db, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("diaryList");
        for (Object json : jsonArray) {
            JSONObject nextDiary = (JSONObject) json;
            addDiary(db, nextDiary);
        }
    }

    // MODIFIES: db
    // EFFECTS: parses diary from JSON object and adds it to diary book
    private void addDiary(DiaryBook db, JSONObject jsonObject) {
        String date = jsonObject.getString("date");
        String weather = jsonObject.getString("weather");
        String mood = jsonObject.getString("mood");
        String text = jsonObject.getString("text");
        Diary diary = new Diary(date, mood, weather, text);
        db.addDiary(diary);
    }
}
