package controller;

import model.GanttChart;
import model.Result;
import model.Process;
import java.util.ArrayList;
import java.util.List;

public class SRTFController {

    private List<model.Process> myProcesses;

    public SRTFController(List<model.Process> inputData) {
        this.myProcesses = inputData;
    }

    public model.Result buildResult() {
        if (myProcesses == null || myProcesses.isEmpty()) return new model.Result("Empty");

        int n = myProcesses.size();
        int currentTime = 0, completed = 0;
        List<GanttChart> ganttChartList = new ArrayList<>();
        model.Process lastProcess = null;
        int startTime = 0;
        for (model.Process p : myProcesses) {
            p.remainingTime = p.burstTime;
            p.firstStart = -1;
        }

        while (completed < n) {
            int shortestIdx = -1;
            int minRemaining = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                model.Process p = myProcesses.get(i);
                if (p.arrivalTime <= currentTime && p.remainingTime > 0 && p.remainingTime < minRemaining) {
                    minRemaining = p.remainingTime;
                    shortestIdx = i;
                }
            }

            if (shortestIdx != -1) {
                model.Process p = myProcesses.get(shortestIdx);
                if (p.firstStart == -1) p.firstStart = currentTime;
                if (lastProcess == null || !p.id.equals(lastProcess.id)) {
                    if (lastProcess != null) {
                        ganttChartList.add(new GanttChart(lastProcess.id, startTime, currentTime));
                    }
                    lastProcess = p;
                    startTime = currentTime;
                }

                p.remainingTime--;
                currentTime++;

                if (p.remainingTime == 0) {
                    completed++;
                    p.finish = currentTime;
                }
            } else {
                currentTime++;
            }
        }
        if (lastProcess != null) {
            ganttChartList.add(new GanttChart(lastProcess.id, startTime, currentTime));
        }
        double totalWT = 0, totalTAT = 0, totalRT = 0;
        for (model.Process p : myProcesses) {
            p.turnAroundTime = p.finish - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;
            p.responseTime = p.firstStart - p.arrivalTime;

            totalWT += p.waitingTime;
            totalTAT += p.turnAroundTime;
            totalRT += p.responseTime;
        }

        model.Result finalResult = new model.Result("SRTF Algorithm");
        finalResult.processes = myProcesses;
        finalResult.ganttCharts = ganttChartList;
        finalResult.avgWaitingTime = totalWT / n;
        finalResult.avgTurnaroundTime = totalTAT / n;
        finalResult.avgResponseTime = totalRT / n;

        return finalResult;
    }
}