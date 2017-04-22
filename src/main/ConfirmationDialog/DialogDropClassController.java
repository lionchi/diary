package main.ConfirmationDialog;

import DBWorkerPackage.DBWorker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Татьяна on 21.04.2017.
 */
public class DialogDropClassController {

    public DialogDropClassController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        fill();
    }
    private Stage dialogStage;
    private String Class;
    private List<String> pupilsClass = new ArrayList<>();
    private List<String> teachersMainClass = new ArrayList<>();
    private List<String> teachersClasses = new ArrayList<>();
    private List<String> tabsList = new ArrayList<>();

    DBWorker dbWorker = new DBWorker();
    Statement statement = dbWorker.getConnection().createStatement();

    public void setClasS(String aClass) {
        Class = aClass;
    }

    private void fill() throws SQLException {

        ResultSet setPupils = statement.executeQuery("select * from список_учеников");

        while (setPupils.next()){

            pupilsClass.add(setPupils.getString("Класс"));
        }

        ResultSet setTeachers = statement.executeQuery("select * from список_учителей");

        while (setTeachers.next()){
            teachersClasses.add(setTeachers.getString("Классы"));
            teachersMainClass.add((setTeachers.getString("Основной_класс")));
        }

        ResultSet setResult = statement.executeQuery("SHOW TABLES FROM diary");
        while (setResult.next()){
            tabsList.add(setResult.getString("Tables_in_diary"));
        }
    }

    @FXML
    private void buttonDa() throws SQLException {
        DBWorker dbWorker = new DBWorker();
        Statement statement = dbWorker.getConnection().createStatement();
        dialogStage.close();
        if (pupilsClass.contains(Class)) {
            statement.executeUpdate(String.format("UPDATE список_учеников SET Класс = NULL where Класс = '%s'", Class));
        }

        if (teachersMainClass.contains(Class)) {
            statement.executeUpdate(String.format("UPDATE список_учителей SET Основной_класс = NULL where Основной_класс = '%s'", Class));
        }
        for(String s: teachersClasses)
        {
            if(s.contains(Class))
            {
                String pattern="";
                String[] formClass = s.split(",");
                for(String s1:formClass)
                {
                    if(!s1.contains(Class))
                        pattern+=s1+",";
                }
                pattern = pattern.substring(0,pattern.length()-1);
                statement.executeUpdate(String.format("UPDATE список_учителей SET Классы = '%s' where Классы = '%s'", pattern,s));
            }
        }
        for(String s:tabsList)
        {
            if(s.contains(Class))
                statement.executeUpdate(String.format("DROP TABLE %s",s));
        }
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Удаление класса");
        alert1.setHeaderText("Удаление класса");
        alert1.setContentText("Удаление класса прошло успешно");
        alert1.showAndWait();
    }
    @FXML
    private void buttonNet()
    {
        dialogStage.close();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
