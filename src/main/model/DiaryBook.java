package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a diary book consisting a list of diaries and a name
public class DiaryBook implements Writable {

    private ArrayList<Diary> diaryList;
    private String name;

    // EFFECTS: constructs a new diary book with an empty diary list and no name
    public DiaryBook() {
        diaryList = new ArrayList<>();
    }

    // EFFECTS: constructs a new diary book with an empty diary list and a name
    public DiaryBook(String name) {
        this.name = name;
        diaryList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Diary> getDiaryList() {
        return diaryList;
    }

    // MODIFIES: this
    // EFFECTS: adds a new diary to the diary list
    public void addDiary(Diary d) {
        diaryList.add(d);
        EventLog.getInstance().logEvent(new Event("Added diary to book"));
    }

    // EFFECTS: searches for diary of a specific date,
    // return this diary if found, return an empty diary otherwise
    public Diary searchDiary(String date) {
        for (Diary d : diaryList) {
            if (d.getDate().equals(date)) {
                return d;
            }
        }
        Diary emptyD = new Diary();
        return emptyD;
    }

    // EFFECTS: returns the content of the diary of a specific date
    public String viewDiary(Diary d) {
        return d.getDate() + "\n" + d.getWeather() + "\n" + d.getMood() + "\n" + d.getText();
    }

    // EFFECTS: deletes a diary of a specific date and return the diary list,
    // if there's no diary matches the date, return the original diary list
    public ArrayList<Diary> deleteDiary(String date) {
        ArrayList<Diary> d = diaryList;
        for (Diary diary : d) {
            if (diary.getDate().equals(date)) {
                d.remove(diary);
                EventLog.getInstance().logEvent(new Event("Deleted diary from book"));
                diaryList = d;
                return d;
            }
        }
        return diaryList;
    }

    // EFFECTS: returns diary book as a JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("diaryList", diaryListToJson());
        return json;
    }

    // EFFECTS: returns diaries in this diary book as a JSON array
    private JSONArray diaryListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Diary d : diaryList) {
            jsonArray.put(d.toJson());
        }
        return jsonArray;
    }
}
