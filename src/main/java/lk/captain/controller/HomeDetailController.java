package lk.captain.controller;


import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lk.captain.bo.BOFactory;
import lk.captain.bo.custom.*;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class HomeDetailController {
    @FXML
    private PieChart pieChart;
    @FXML
    private Label lblTeaEntry;

    @FXML
    private Label lblLost;

    @FXML
    private BarChart<String, Integer> barChart;
    @FXML
    private Label lblProfit;

    @FXML
    private Label lblColec;

    @FXML
    private Label lblWork;
    @FXML
    private Label lblCollectors;
    @FXML
    private Label lblWorkers;

    @FXML
    private ProgressBar progressBar;
    @FXML
    private ProgressBar progressBar1;

    @FXML
    private ProgressBar progressBar11;


    WorkerBO workerBO = (WorkerBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WORKER);
    TeaCollectorBO teaCollectorBO = (TeaCollectorBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEACOLLECTOR);
    WareHouseBO wareHouseBO = (WareHouseBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WAREHOUSE);
    AttendenceBO attendenceBO = (AttendenceBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.ATTENDENCE);
    FuelBO fuelBO = (FuelBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.FUEL);
    WoodBO woodBO = (WoodBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.WOOD);
    TeaLeafEntryBO teaLeafEntryBO = (TeaLeafEntryBO) BOFactory.getBoFactory().getBOTypes(BOFactory.BOTypes.TEALEAFENTRY);

    public void initialize() throws SQLException, ClassNotFoundException {
    Piechart();BarCharts();
}

public void Piechart() throws SQLException, ClassNotFoundException {
    lblTeaEntry.setText(String.valueOf(teaLeafEntryBO.getNetTotal(String.valueOf(LocalDate.now()))));
    double entry = Double.parseDouble(lblTeaEntry.getText());
    if (entry<8000){
        lblTeaEntry.setStyle("-fx-text-fill: red;");
        lblLost.setVisible(true);
    }else {
        lblTeaEntry.setStyle("-fx-text-fill: #00dc1e;");
        lblProfit.setVisible(true);
        lblLost.setVisible(false);

    }
    ResultSet resultSet = fuelBO.getAllAvalable();
    double liter = 0;
    if (resultSet.next()){
        liter=resultSet.getDouble(2);
    }

    ResultSet resultSet2 = woodBO.getAllAvalable();
    double wood = 0;
    if (resultSet.next()){
        wood=resultSet2.getDouble(2);
    }


    double netPowder = wareHouseBO.getCount();

    double minLiter = liter/1000;
    double minwood = wood/1000;
    double minpowder = netPowder/1000;
    progressBar.setProgress(minLiter);
    if (0.35>=minLiter){
        progressBar.setStyle("-fx-accent:red;");
    } else if (0.50>=minLiter) {
        progressBar.setStyle("-fx-accent:#ff7100;");
    }else{
        progressBar.setStyle("-fx-accent:#00dc1e;");
    }
    progressBar11.setProgress(minwood);
    if (0.35>=minwood){
        progressBar11.setStyle("-fx-accent:red;");
    } else if (0.50>=minwood) {
        progressBar11.setStyle("-fx-accent:#ff7100;");
    }else{
        progressBar11.setStyle("-fx-accent:#00dc1e;");
    }
    progressBar1.setProgress(minpowder);
    if (0.35>=minpowder){
        progressBar1.setStyle("-fx-accent:red;");
    } else if (0.50>=minpowder) {
        progressBar1.setStyle("-fx-accent:#ff7100;");
    }else{
        progressBar1.setStyle("-fx-accent:#00dc1e;");
    }


    ObservableList<PieChart.Data> pieChartData= FXCollections.observableArrayList(
            new PieChart.Data("Fuel Available(l)",liter),
            new PieChart.Data("Wood Available (ton)",wood),
            new PieChart.Data("Tea Powder Available(Kg) ",netPowder));

    pieChartData.forEach(data ->
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName()," amount: ", data.pieValueProperty()
                    )
            )
    );

    pieChart.getData().addAll(pieChartData);
}

public void BarCharts() throws SQLException, ClassNotFoundException {
    ResultSet resultSet = attendenceBO.isTodayAtt();
    int wcount = workerBO.searchCount();
    int tcount = 0;
    try {
        tcount = teaCollectorBO.searchCount();
    } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
    }
    lblWorkers.setText(String.valueOf(wcount));
    lblCollectors.setText(String.valueOf(tcount));

    int workCount = 0;
    int colecCount = 0;
    if(resultSet.next()) {
        workCount = resultSet.getInt("WORKCOUNT");
        colecCount = resultSet.getInt("COLEC");
    }
    lblColec.setText(String.valueOf(colecCount));
    lblWork.setText(String.valueOf(workCount));

   XYChart.Series<String ,Integer> series1 =new XYChart.Series();
    series1.setName("Workers");
    series1.getData().add(new XYChart.Data("Today",workCount));


    XYChart.Series <String ,Integer> series2 = new XYChart.Series();
    series2.setName("Tea Collectors");
    series2.getData().add(new XYChart.Data("Today",colecCount));

   barChart.getData().addAll(series1,series2);

}

}
