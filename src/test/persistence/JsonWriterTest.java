package persistence;

// based on Workroom app; link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Diary;
import model.DiaryBook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Tests for JsonWriter
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            DiaryBook db = new DiaryBook("My Diary Book");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyDiaryBook() {
        try {
            DiaryBook db = new DiaryBook("My Diary Book");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyDiaryBook.json");
            writer.open();
            writer.write(db);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyDiaryBook.json");
            db = reader.read();
            assertEquals("My Diary Book", db.getName());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralDiaryBook() {
        try {
            DiaryBook db = new DiaryBook("My Diary Book");
            Diary d1 = new Diary("2021", "Okay", "Sunny", "HAHA");
            Diary d2 = new Diary("2022", "Meh", "Rainy", "Meh");
            Diary d3 = new Diary("2023", "Calm", "Cloudy", "Phew");
            db.addDiary(d1);
            db.addDiary(d2);
            db.addDiary(d3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralDiaryBook.json");
            writer.open();
            writer.write(db);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralDiaryBook.json");
            db = reader.read();
            assertEquals("My Diary Book", db.getName());
            ArrayList<Diary> diaryList = db.getDiaryList();
            assertEquals(3, diaryList.size());
            assertEquals("2021", diaryList.get(0).getDate());
            assertEquals("2022", diaryList.get(1).getDate());
            assertEquals("2023", diaryList.get(2).getDate());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
