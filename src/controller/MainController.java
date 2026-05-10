package controller;

import model.Process;
import model.Result;
import java.util.List;

public class MainController {


    public Result runSRTF(List<Process> p) {
        return new SRTFController(p).buildResult();
    }

    public Result runPriority(List<Process> p) {
        return new PriorityController(p).buildResult();
    }
}