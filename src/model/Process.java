package model;

public class Process {
    public String id;
    public int arrivalTime;
    public int burstTime;
    public int priority;
    public int remainingTime;

    public int firstStart = -1;
    public int finish;
    public int waitingTime;
    public int turnAroundTime;
    public int responseTime;

    public Process(String id, int arrivalTime, int burstTime, int priority) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.remainingTime = burstTime;
    }

    public String getId() {
        return id;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    public int getBurstTime() {
        return burstTime;
    }
    public int getPriority() {
        return priority;
    }


    public int getWaitingTime() { return waitingTime; }
    public int getTurnAroundTime() { return turnAroundTime; }
    public int getResponseTime() { return responseTime; }
}