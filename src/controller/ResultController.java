package controller;

import model.Result;
import model.Process;

public class ResultController {
    public static String compare(Result r1, Result r2) {
        if (r1 == null || r2 == null) return "No Data Available";

        StringBuilder sb = new StringBuilder();

        boolean starvationFound = false;
        for (Process p : r2.processes) {
            if (p.waitingTime > (p.burstTime * 3)) {
                starvationFound = true;
                break;
            }
        }


        if (r1.avgWaitingTime == r2.avgWaitingTime) {
            sb.append("FINAL VERDICT: BOTH ARE EQUAL!\n");
            sb.append("• Both algorithms behaved identically for this workload.\n");



        } else {
            String winner = (r1.avgWaitingTime < r2.avgWaitingTime) ? "SRTF" : "Priority";
            double diff = Math.abs(r1.avgWaitingTime - r2.avgWaitingTime);
            sb.append("FINAL RESULT: ").append(winner.toUpperCase()).append(" is better!\n");
            sb.append("• Reason: It reduces Avg Waiting Time by ").append(String.format("%.2f", diff)).append(" units.\n");
        }


        if (starvationFound) {
            sb.append("\nSTARVATION DETECTED: Low-priority processes were delayed.\n");
            sb.append(" SOLUTION: Aging was automatically applied to ensure all processes finish.");
        }

        return sb.toString();
    }
}