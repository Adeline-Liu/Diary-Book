package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for DiaryBook
class DiaryBookTest {
    private DiaryBook testDiaryBook1;
    private DiaryBook testDiaryBook2;
    private Diary testDiary;

    @BeforeEach
    void setUp() {
        testDiaryBook1 = new DiaryBook();
        testDiaryBook2 = new DiaryBook("A BOOK");
        testDiary = new Diary();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testDiaryBook1.getDiaryList().size());
        assertEquals("A BOOK", testDiaryBook2.getName());
    }

    @Test
    void testSetName() {
        testDiaryBook2.setName("A REAL BOOK");
        assertEquals("A REAL BOOK", testDiaryBook2.getName());
    }

    @Test
    void testAddDiary() {
        testDiaryBook1.addDiary(testDiary);
        assertTrue(testDiaryBook1.getDiaryList().contains(testDiary));
        assertEquals(1, testDiaryBook1.getDiaryList().size());
    }

    @Test
    void testSearchDiary() {
        testDiary.setDate("2021/2/3");
        testDiary.setText("Had dinner with friends.");
        testDiaryBook1.addDiary(testDiary);
        assertEquals(testDiaryBook1.searchDiary("2021/2/3"), testDiary);
        assertTrue(testDiaryBook1.searchDiary("2021/2/3").equals(testDiary));
        Diary emptyD = testDiaryBook1.searchDiary("2041/5/10");
        assertEquals("", emptyD.getText());
    }

    @Test
    void testViewDiary() {
        testDiary.setDate("2021/2/3");
        testDiary.setWeather("Rainy");
        testDiary.setMood("Gloomy");
        testDiary.setText("Had dinner with friends.");
        testDiaryBook1.addDiary(testDiary);
        assertEquals("2021/2/3" + "\n" + "Rainy" + "\n" + "Gloomy" + "\n" + "Had dinner with friends.",
                testDiaryBook1.viewDiary(testDiary));
    }

    @Test
    void testDeleteDiary() {
        testDiary.setDate("2021/2/3");
        testDiaryBook1.addDiary(testDiary);
        testDiaryBook1.deleteDiary("2190/9/8");
        assertEquals(1, testDiaryBook1.getDiaryList().size());
        testDiaryBook1.deleteDiary("2021/2/3");
        assertEquals(0, testDiaryBook1.getDiaryList().size());
    }
}