package data.dao.models;

import java.time.LocalTime;

public class Trip {

    private int id;
    private LocalTime startTime;
    private LocalTime endTime;
    private int duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Trip(int id, LocalTime startTime, int duration) {
        this.id = id;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Trip(LocalTime startTime, LocalTime endTime, int duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public Trip() {
    }
}
