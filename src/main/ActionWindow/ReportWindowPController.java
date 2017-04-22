package main.ActionWindow;


import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ReportWindowPController {
    private ObservableList<String> classes = FXCollections.observableArrayList();
    private ObservableList<String> timeList = FXCollections.observableArrayList("Четверть 1","Четверть 2","Четверть 3","Четверть 4","Год");
    private List<String> tabsList = new ArrayList<>();

    private List<String> percent = new ArrayList<>();
    private ObservableList<Integer> report_percent = FXCollections.observableArrayList();
    private ObservableList<String> subs = FXCollections.observableArrayList();
    private HashSet<String> subjects = new HashSet<>();

    private int days = 31;
    private int count = 0;
    private int size_class = 0;
    private boolean isTrue = true;
    private boolean isNull = true;

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

    public ReportWindowPController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        fill();
        Time.setItems(timeList);
        Class.setItems(classes);
        xAxis.setCategories(subs);
    }

    private void fill() throws SQLException {
        DBWorker dbWorker = new DBWorker();

        Statement statement = dbWorker.getConnection().createStatement();

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
        xAxis.setLabel("Предмет");
        xAxis.setTickLabelFill(Color.BROWN);
        xAxis.setTickLabelGap(10);
        xAxis.setTickLength(20);
        xAxis.setGapStartAndEnd(true);

        XYChart.Series series = new XYChart.Series<>();
        for(int i = 0; i<subs.size();i++) {
            series.getData().add(new XYChart.Data<>(subs.get(i), report_percent.get(i)));
        }
        chart.getData().addAll(series);
    }

    private void count(List<String> percent)
    {
        for(int i=0; i<percent.size(); i++)
        {
            if(percent.get(i)!=null) {
                if (percent.get(i).contains("н"))
                    count++;
                isNull = false;
            }
        }
        if(count!=0||isNull==false) {
            double plop = ((double) count / ((double) size_class * (double) days)) * 100;
            report_percent.add(100 - (int) plop);
        }
        else{
            report_percent.add(0);
            isTrue = false;
        }
    }

    private void GetANDCheck(ResultSet set,String s) throws SQLException {
        while (set.next()) {
            for (int i = 0; i < 31; i++) {
                if(s.contains("февраль")&& i==28)
                    break;
                else if((s.contains("апрель")||s.contains("апрель")||s.contains("апрель")||s.contains("апрель"))&& i==29)
                    break;
                String pattern = String.valueOf(i+1)+"_";
                percent.add(set.getString(String.format("%s",pattern)));
            }
        }
        if(!Time.getValue().equals("Год")){

            count(percent);
        }
    }
    @FXML
    private void Write() throws IOException {
        if( report_button.isDisable()==true) {
            String s = "\t" + "Отчет за " + Time.getValue() + "\r\n\r\n" + "Предмет: " + "\t\t" + "Процент посещаемости" + "\r\n";
            for (int i = 0; i < subs.size(); i++) {
                if (subs.get(i).length() < 10)
                    s += " ";
                s += subs.get(i) + "\t\t\t" + report_percent.get(i) + "\r\n";
            }

            FileNameExtensionFilter filter = new FileNameExtensionFilter("*.TXT", "*.*");
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(filter);
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                try (FileWriter fs = new FileWriter(fc.getSelectedFile())) {
                    fs.write(s);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Отчет");
            alert1.setHeaderText("Отчет");
            alert1.setContentText("Отчет успешно сохранен!");
            alert1.showAndWait();
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Отчет");
            alert1.setHeaderText("Отчет");
            alert1.setContentText("Перед сохранением необходимо создать график!");
            alert1.showAndWait();
        }
    }

    @FXML
    private void Action() throws SQLException, IOException {
        List<String> new_tabs = new ArrayList<>();
        for (int i=0; i<tabsList.size(); i++) {
            if(tabsList.get(i).contains(Class.getValue())&&tabsList.get(i).split("_").length<4)
            {
                subjects.add(tabsList.get(i).split("_")[1]);
                new_tabs.add(tabsList.get(i));
            }
            else if(tabsList.get(i).contains(Class.getValue())&&tabsList.get(i).split("_").length==4)
            {
                ResultSet sizeSet = statement.executeQuery(String.format("SELECT COUNT( * ) FROM  %s",tabsList.get(i)));
                while (sizeSet.next()) {
                    size_class = Integer.parseInt(sizeSet.getString("COUNT( * )"));
                }
            }
        }
        for (String s:subjects) {
            create(s, new_tabs);
            percent.clear();
            count = 0;
        }

        if(isTrue==true)
        {   BarChart();
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Отчет");
            alert1.setHeaderText("Отчет");
            alert1.setContentText("Отчет успешно создан!");
            alert1.showAndWait();
            report_button.setDisable(true);
        }
        else
        {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Ошибка");
            alert1.setHeaderText("Ошибка");
            alert1.setContentText("Данные по посещаемости отсутствуют");
            alert1.showAndWait();
            report_button.setDisable(true);
        }

    }

    private void create(String subject, List<String> new_tabs) throws SQLException {
        if(Time.getValue().equals("Четверть 1")||Time.getValue().equals("Год"))
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&(s.contains("октябрь")||s.contains("сентябрь")))
                {
                    // days = 61;
                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set,s);
                    break;
                }
            }
        }
        if(Time.getValue().equals("Четверть 2")||Time.getValue().equals("Год"))
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&(s.contains("декабрь")||s.contains("ноябрь"))){
                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set,s);
                    break;
                }
            }
        }
        if(Time.getValue().equals("Четверть 3")||Time.getValue().equals("Год"))
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&(s.contains("март")||s.contains("январь")||s.contains("февраль"))){
                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set,s);
                    break;
                }
            }
        }
        if(Time.getValue().equals("Четверть 4")||Time.getValue().equals("Год"))
        {
            for (String s:new_tabs) {
                if (s.contains(subject)&&(s.contains("май")||s.contains("апрель"))){

                    ResultSet set = statement.executeQuery(String.format("select * from %s",s));
                    GetANDCheck(set,s);
                    break;
                }
            }
        }
        if(Time.getValue().equals("Год"))
        {
            count(percent);
        }

    }
}
