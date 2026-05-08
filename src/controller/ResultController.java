package controller;

import model.Result;

public class ResultController {
    public static String compare(Result r1, Result r2) {
        if (r1 == null || r2 == null) return "No Data Available";
        if (r1.avgWaitingTime == r2.avgWaitingTime) {
            return "FINAL VERDICT: BOTH ARE EQUAL!\n" +
                    "• For this specific workload, both algorithms behaved identically.";
        }

        String winner = (r1.avgWaitingTime < r2.avgWaitingTime) ? "SRTF Algorithm" : "Priority Algorithm";
        double diff = Math.abs(r1.avgWaitingTime - r2.avgWaitingTime);

        StringBuilder sb = new StringBuilder();
        sb.append("FINAL RESULT: ").append(winner.toUpperCase()).append(" is better!\n");
        sb.append("• Reason: It reduces Average Waiting Time by ").append(String.format("%.2f", diff)).append(" units.\n");
        sb.append("• Summary: For this workload, ").append(winner).append(" is the most optimal choice.");

        return sb.toString();
    }
}