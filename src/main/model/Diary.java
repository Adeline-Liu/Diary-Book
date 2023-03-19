package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a diary consisting a date, your mood, the weather and texts
public class Diary implements Writable {
    private String date;
    private String mood;
    private String weather;
    private String text;

    // EFFECTS: constructs a diary with empty strings of date, mood, weather, and text
    public Diary() {
        date = "";
        mood = "";
        weather = "";
        text = "";
    }

    // EFFECTS: constructs a diary with strings of date, mood, weather, and text
    public Diary(String date, String mood, String weather, String text) {
        this.date = date;
        this.mood = mood;
        this.weather = weather;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getMood() {
        return mood;
    }

    public String getWeather() {
        return weather;
    }

    public String getText() {
        return text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setText(String text) {
        this.text = text;
    }

    // EFFECTS: returns diary as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);
        json.put("weather", weather);
        json.put("mood", mood);
        json.put("text", text);
        return json;
    }
}
