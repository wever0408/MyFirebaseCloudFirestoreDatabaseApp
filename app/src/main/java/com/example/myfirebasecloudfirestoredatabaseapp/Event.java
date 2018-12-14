package com.example.myfirebasecloudfirestoredatabaseapp;

public class Event {
    private String id;
    private String name;
    private String place;
    private String type;
    private String startTime;
    private String endTime;

    public Event(String id, String name, String place, String type, String startTime, String endTime) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Event() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
