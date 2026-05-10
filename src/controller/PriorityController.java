package controller;

import model.Process;
import model.Result;
import model.GanttChart;
import java.util.*;

public class PriorityController {
    private List<Process> processes;
    public List<GanttChart> ganttChartList;



    public PriorityController(List<Process> processes) {
        this.processes = new ArrayList<>();
        for(Process p : processes) {
            this.processes.add(new Process(p.id, p.arrivalTime, p.burstTime, p.priority));
        }
    }




    public Result   buildResult() {
        int n = processes.size();
        int currentTime = 0, completed = 0;
        ganttChartList = new ArrayList<>();
        boolean[] isStarted = new boolean[n];
        Process lastProcess = null;
        int startTime = 0;





        while (completed < n) {//aging
            if (currentTime > 0 && currentTime % 5 == 0) {
                for (Process p : processes) {
                    if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                        if (p.priority > 1) p.priority--;
                    }
                }
            }






            int bestIdx = -1;
            int minPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                Process p = processes.get(i);
                if (p.arrivalTime <= currentTime && p.remainingTime > 0) {
                    if (p.priority < minPriority) {
                        minPriority = p.priority;
                        bestIdx = i;
                    } else if (p.priority == minPriority) {
                        if (p.arrivalTime < processes.get(bestIdx).arrivalTime) bestIdx = i;
                    }
                }
            }





            if (bestIdx != -1) {
                Process p = processes.get(bestIdx);
                if (lastProcess == null || !p.id.equals(lastProcess.id)) {
                    if (lastProcess != null) ganttChartList.add(new GanttChart(lastProcess.id, startTime, currentTime));
                    lastProcess = p;
                    startTime = currentTime;
                }
                if (!isStarted[bestIdx]) {
                    p.firstStart = currentTime;
                    isStarted[bestIdx] = true;
                }
                p.remainingTime--;
                currentTime++;
                if (p.remainingTime == 0) {
                    completed++;
                    p.finish = currentTime;
                }
            } else { currentTime++; }
        }
        if (lastProcess != null) ganttChartList.add(new GanttChart(lastProcess.id, startTime, currentTime));

        return calculateAndPack("Priority (with Aging)");
    }




    private Result calculateAndPack(String name) {
        double tWT = 0, tTAT = 0, tRT = 0;
        for (Process p : processes) {
            p.turnAroundTime = p.finish - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;
            p.responseTime = p.firstStart - p.arrivalTime;
            tWT = tWT+p.waitingTime; tTAT += p.turnAroundTime; tRT += p.responseTime;
        }
        Result r = new Result(name);
        r.processes = processes;
        r.ganttCharts = ganttChartList;
        r.avgWaitingTime = tWT / processes.size();
        r.avgTurnaroundTime = tTAT / processes.size();
        r.avgResponseTime = tRT / processes.size();
        return r;
    }
}