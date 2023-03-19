package persistence;

// based on Workroom app; link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

import model.Diary;
import model.DiaryBook;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Tests for JsonReader
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            DiaryBook db = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyDiaryBook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyDiaryBook.json");
        try {
            DiaryBook db = reader.read();
            assertEquals("My Diary Book", db.getName());
            assertTrue(db.getDiaryList().isEmpty());
            assertEquals(0, db.getDiaryList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralDiaryBook() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralDiaryBook.json");
        try {
            DiaryBook db = reader.read();
            assertEquals("My Diary Book", db.getName());
            ArrayList<Diary> diaryList = db.getDiaryList();
            assertEquals(2, diaryList.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
