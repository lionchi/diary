package main.ActionWindow;


import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ReportWindowPController {
    private ObservableList<String> classes = FXCollections.observableArrayList();
    private ObservableList<String> timeList = FXCollections.observableArrayList("Четверть 1","Четверть 2","Четверть 3","Четверть 4","Год");
    private List<String> tabsList = new ArrayList<>();

    @FXML
    private ChoiceBox<String> Time = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> Class = new ChoiceBox<String>();

    @FXML
    private void initialize() throws SQLException {
        fill();
        Time.setItems(timeList);
        Class.setItems(classes);
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
}
