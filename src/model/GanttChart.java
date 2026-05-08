package model;

public class GanttChart {
    public String processId;
    public int startTime, endTime;

    public GanttChart(String processId, int startTime, int endTime) {
        this.processId = processId;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}