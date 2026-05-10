package view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

import controller.SRTFController;
import controller.PriorityController;
import controller.ResultController;
import model.Process;
import model.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Input {

    @FXML private TextField pIdField, pArrivalField, pBurstField, pPriorityField;
    @FXML private TableView<model.Process> inputTable;
    @FXML private TableColumn<model.Process, String> colId;
    @FXML private TableColumn<model.Process, Integer> colArrival;
    @FXML private TableColumn<model.Process, Integer> colBurst;
    @FXML private TableColumn<model.Process, Integer> colPriority;

    private List<model.Process> processList = new ArrayList<>();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colArrival.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        colBurst.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        colPriority.setCellValueFactory(new PropertyValueFactory<>("priority"));
    }

    @FXML
    public void handleAddProcess() {
        try {
            if (pIdField.getText().isEmpty()) return;

            model.Process p = new model.Process(
                    pIdField.getText(),
                    Integer.parseInt(pArrivalField.getText()),
                    Integer.parseInt(pBurstField.getText()),
                    Integer.parseInt(pPriorityField.getText())
            );

            processList.add(p);
            inputTable.setItems(FXCollections.observableArrayList(processList));
            clearFields();
        } catch (Exception e) {
            System.out.println("Input Error!");
        }
    }

    @FXML
    public void handleRun() {
        if (processList.isEmpty()) {
            System.out.println("No processes to run!");
            return;
        }

        try {
            SRTFController srtfC = new SRTFController(new ArrayList<>(processList));
            model.Result res1 = srtfC.buildResult();
            PriorityController prioC = new PriorityController(new ArrayList<>(processList));
            model.Result res2 = prioC.buildResult();
            String englishSummary = controller.ResultController.compare(res1, res2);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/result.fxml"));
            Parent root = loader.load();
            view.Result resultView = loader.getController();
            resultView.setData(res1, res2, englishSummary);
            Stage stage = new Stage();
            stage.setTitle("Comparison Results");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            System.out.println("Error loading result.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    public void handleClear() {
        processList.clear();
        inputTable.setItems(FXCollections.observableArrayList(processList));
        clearFields();
    }

    private void clearFields() {
        pIdField.clear();
        pArrivalField.clear();
        pBurstField.clear();
        pPriorityField.clear();
    }
}