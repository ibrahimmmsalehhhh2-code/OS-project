package view;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.GanttChart;
import java.util.List;

public class Result {
    @FXML private HBox srtfGanttContainer;
    @FXML private HBox priorityGanttContainer;
    @FXML private Label srtfMetricsLabel;
    @FXML private Label priorityMetricsLabel;
    @FXML private Label finalConclusionLabel;

    public void setData(model.Result srtf, model.Result prio, String msg) {
        srtfGanttContainer.setAlignment(Pos.BOTTOM_LEFT);
        priorityGanttContainer.setAlignment(Pos.BOTTOM_LEFT);
        draw(srtfGanttContainer, srtf.ganttCharts);
        draw(priorityGanttContainer, prio.ganttCharts);
        srtfMetricsLabel.setText(String.format("Avg WT: %.2f | TAT: %.2f | RT: %.2f",
                srtf.avgWaitingTime, srtf.avgTurnaroundTime, srtf.avgResponseTime));
        priorityMetricsLabel.setText(String.format("Avg WT: %.2f | TAT: %.2f | RT: %.2f",
                prio.avgWaitingTime, prio.avgTurnaroundTime, prio.avgResponseTime));
        finalConclusionLabel.setText(msg);
    }

    private void draw(HBox container, List<GanttChart> list) {
        container.getChildren().clear();
        if (list == null) return;
        for (GanttChart gc : list) {
            VBox box = new VBox();
            box.setAlignment(Pos.BOTTOM_LEFT);
            StackPane pane = new StackPane();
            int duration = gc.endTime - gc.startTime;
            Rectangle r = new Rectangle(duration * 40, 50);
            r.setFill(Color.web("#E0E0E0"));
            r.setStroke(Color.BLACK);
            pane.getChildren().addAll(r, new Label(gc.processId));
            Label timeLabel = new Label(String.valueOf(gc.startTime));
            timeLabel.setStyle("-fx-font-size: 10px; -fx-padding: 0 0 0 2;");
            box.getChildren().addAll(pane, timeLabel);
            container.getChildren().add(box);
            if (list.indexOf(gc) == list.size() - 1) {
                Label endLabel = new Label(String.valueOf(gc.endTime));
                endLabel.setStyle("-fx-font-size: 10px; -fx-padding: 0 0 0 " + (duration * 35) + ";");
                box.getChildren().add(endLabel);
            }
        }
    }
}