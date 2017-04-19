package main.ActionWindow;

import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ReportWindowUController {
    private ObservableList<String> classes = FXCollections.observableArrayList();
    private ObservableList<String> timeList = FXCollections.observableArrayList("Четверть 1","Четверть 2","Четверть 3","Четверть 4","Год");

    private List<String> tabsList = new ArrayList<>();
    private List<String> marks =  new ArrayList<>();

    private ObservableList<Double> report_marks = FXCollections.observableArrayList();
    private ObservableList<String> subs = FXCollections.observableArrayList();

    private HashSet<String> subjects = new HashSet<>();
    private boolean isNull = true;
    private double report;

    private DBWorker dbWorker = new DBWorker();
    private Statement statement = dbWorker.getConnection().createStatement();

    @FXML
    private Button report_button;

    @FXML
    private ChoiceBox<String> Time = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> Class = new ChoiceBox<String>();
    @FXML
    private BarChart chart;
    @FXML
    private CategoryAxis xAxis;

    public ReportWindowUController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        fill();
        Time.setItems(timeList);
        Class.setItems(classes);
    }

    private void fill() throws SQLException {

        ResultSet setResult = statement.executeQuery("SHOW TABLES FROM diary");
        while (setResult.next()){
            tabsList.add(setResult.getString("Tables_in_diary"));
        }
        for (String s:tabsList) {
            if(s.split("_").length==4)
            {
                classes.add(s.split("_")[0]);
            }
        }
    }

    @FXML
    private void BarChart() throws IOException {
        subs.addAll(subjects);
        xAxis.setCategories(subs);
        xAxis.setLabel("Предмет");
        xAxis.setTickLabelFill(Color.BROWN);
        xAxis.setTickLabelGap(10);
        xAxis.setTickLength(20);
        xAxis.setGapStartAndEnd(true);

        XYChart.Series series = new XYChart.Series<>();
        for(int i = 0; i<subs.size();i++) {
            series.getData().add(new XYChart.Data<>(subs.get(i), report_marks.get(i)));
        }
        chart.getData().addAll(series);
    }

    private void count(List<String> marks)
    {
        for(int i=0; i<marks.size(); i++)
        {
            report+=Double.parseDouble(marks.get(i));
        }
        report = report/marks.size();
        report_marks.add(report);
    }

    private void GetANDCheck(ResultSet set) throws SQLException {
        while (set.next()){
            marks.add(set.getString("Четверть"));
        }
        if(marks.contains(null))
        {
            isNull=false;
        }
        else if(!Time.getValue().equals("Год")){

            count(marks);
        }
    }

    @FXML
    private void Action() throws SQLException, IOException {;
        List<String> new_tabs = new ArrayList<>();
        for (int i=0; i<tabsList.size(); i++) {
            if(tabsList.get(i).contains(Class.getValue())&&tabsList.get(i).split("_").length<4)
            {
                subjects.add(tabsList.get(i).split("_")[1]);
                new_tabs.add(tabsList.get(i));
            }
        }
        for (String s:subjects) {
            create(s,new_tabs);
            marks.clear();
            report = 0;
            if(isNull==false)
            {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Ошибка");
                alert1.setHeaderText("Ошибка");
                alert1.setContentText("Четвертные оценки еще не проставлены");
                alert1.showAndWait();
                break;
            }
        }
        if(isNull==true) {
            BarChart();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Отчет");
            alert1.setHeaderText("Отчет");
            alert1.setContentText("Отчет успешно создан!");
            alert1.showAndWait();
            report_button.setDisable(true);
        }

    }

    private void create(String subject, List<String> new_tabs) throws SQLException {
        if(Time.getValue().equals("Четверть 1")||Time.getValue().equals("Год"))
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&s.contains("октябрь"))
                {
                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set);
                    break;
                }
            }
        }
        if((Time.getValue().equals("Четверть 2")||Time.getValue().equals("Год"))&&isNull!=false)
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&s.contains("декабрь")){
                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set);
                    break;
                }
            }
        }
        if((Time.getValue().equals("Четверть 3")||Time.getValue().equals("Год"))&&isNull!=false)
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&s.contains("март")){
                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set);
                    break;
                }
            }
        }
        if((Time.getValue().equals("Четверть 4")||Time.getValue().equals("Год")&&isNull!=false))
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&s.contains("май")){
                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set);
                    break;
                }
            }
        }
        if(Time.getValue().equals("Год")&& isNull!=false)
        {
            count(marks);
        }

    }
}
