package model;

import java.util.List;

public class Result {
    public String algorithmName;
    public List<model.Process> processes;
    public List<GanttChart> ganttCharts;
    public double avgWaitingTime;
    public double avgTurnaroundTime;
    public double avgResponseTime;

    public Result(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}