package persistence;

// based on Workroom app; link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Diary;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests for Json
public class JsonTest {
    protected void checkDiary(String date, String weather, String mood, String text, Diary diary) {
        assertEquals(date, diary.getDate());
        assertEquals(weather, diary.getWeather());
        assertEquals(mood, diary.getMood());
        assertEquals(text, diary.getText());
    }
}

