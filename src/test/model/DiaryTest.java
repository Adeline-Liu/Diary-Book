package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Tests for Diary
public class DiaryTest {
    private Diary d;

    @BeforeEach
    void setup() {
        d = new Diary();
    }

    @Test
    void testConstructor() {
        assertEquals("", d.getMood());
        assertEquals("", d.getDate());
        assertEquals("", d.getWeather());
        assertEquals("", d.getText());
    }

    @Test
    void testGetDate() {
        d.setDate("2021/3/2");
        assertEquals("2021/3/2", d.getDate());
    }

    @Test
    void testGetMood() {
        d.setMood("Gloomy");
        assertEquals("Gloomy", d.getMood());
    }

    @Test
    void testGetWeather() {
        d.setWeather("Rainy");
        assertEquals("Rainy", d.getWeather());
    }

    @Test
    void testGetText() {
        d.setText("It's raining heavily.");
        assertEquals("It's raining heavily.", d.getText());
    }

    @Test
    void testSetDate() {
        d.setDate("2021/3/2");
        assertEquals("2021/3/2", d.getDate());
    }

    @Test
    void testSetMood() {
        d.setMood("Gloomy");
        assertEquals("Gloomy", d.getMood());
    }

    @Test
    void testSetWeather() {
        d.setWeather("Rainy");
        assertEquals("Rainy", d.getWeather());
    }

    @Test
    void testSetText() {
        d.setText("It's raining heavily.");
        assertEquals("It's raining heavily.", d.getText());
    }
}
