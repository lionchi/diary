package main.ConfirmationDialog;

import DBWorkerPackage.DBWorker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class DialogTranslationController
{
    private Stage dialogStage;
    private HashSet<String> pupilClass = new HashSet<>();
    private HashSet<String> teacherMainClass = new HashSet<String>();
    private List<String> teacherClasses = new ArrayList<>();
    private List<String> tabsList = new ArrayList<>();
    private List<String> pupils = new ArrayList<>();
    private List<String> teachers = new ArrayList<>();
    private List<String> Listmonth = new ArrayList<>();

    DBWorker dbWorker = new DBWorker();

    Statement statement = dbWorker.getConnection().createStatement();

    public DialogTranslationController() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        fill();
        Listmonth.add("октябрь");
        Listmonth.add("декабрь");
        Listmonth.add("март");
        Listmonth.add("май");
    }

    private void createtables (String subject, List<String> Listmonth, String cs, List<String> pupilsList) throws SQLException {

        for (int i=0; i<4;i++)
        {
            String pattern = cs+"_"+subject+"_"+Listmonth.get(i);
            statement.executeUpdate(String.format("CREATE TABLE %s (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,Список_учеников TEXT, 1_ TEXT, 2_ TEXT,3_ TEXT,4_ TEXT,5_ TEXT,6_ TEXT,7_ TEXT,8_ TEXT,9_ TEXT,10_ TEXT,11_ TEXT,12_ TEXT,13_ TEXT,14_ TEXT,15_ TEXT,16_ TEXT,17_ TEXT,18_ TEXT,19_ TEXT,20_ TEXT,21_ TEXT,22_ TEXT,23_ TEXT,24_ TEXT,25_ TEXT,26_ TEXT,27_ TEXT,28_ TEXT,29_ TEXT,30_ TEXT,31_ TEXT, Четверть TEXT)",pattern));
            for(String s:pupilsList)
            {
                statement.executeUpdate(String.format("INSERT INTO %s (Список_учеников) values ('%s')", pattern, s));
            }
        }
    }

    private void fill() throws SQLException {

        ResultSet setPupils = statement.executeQuery("select * from список_учеников");

        while (setPupils.next()){

            if(setPupils.getString("Класс")!=null) {
                if(!setPupils.getString("Класс").isEmpty()) {
                    pupilClass.add(setPupils.getString("Класс"));
                }
            }
        }

        ResultSet setTeachers = statement.executeQuery("select * from список_учителей");
        while (setTeachers.next()){
            teacherClasses.add(setTeachers.getString("Классы"));
        }

        ResultSet setTeachers2 = statement.executeQuery("select * from список_учителей");
        while (setTeachers2.next()){
            if(setTeachers2.getString("Основной_класс")!=null) {
                if (!setTeachers2.getString("Основной_класс").isEmpty()) {
                    teacherMainClass.add(setTeachers2.getString("Основной_класс"));
                }
            }
        }

        ResultSet setResult = statement.executeQuery("SHOW TABLES FROM diary");
        while (setResult.next()){
            tabsList.add(setResult.getString("Tables_in_diary"));
        }
    }

    @FXML
    private void buttonDa() throws SQLException {
        dialogStage.close();
        pupils.addAll(pupilClass);
        for(int i = 0; i<pupils.size(); i++)
        {
            int Class = Character.getNumericValue(pupils.get(i).charAt(0));
            String newClass = String.valueOf(Class+1)+ pupils.get(i).charAt(1);
            if(pupils.get(i).contains("11"))
            {
                statement.executeUpdate(String.format("DELETE from список_учеников WHERE Класс = '%s'",pupils.get(i)));
            }
            else statement.executeUpdate(String.format("UPDATE список_учеников SET Класс = '%s' where Класс = '%s'",newClass,pupils.get(i)));
        }

        teachers.addAll(teacherMainClass);
        for(int i = 0; i<teachers.size(); i++)
        {
            int Class = Character.getNumericValue(teachers.get(i).charAt(0));
            String newClass = String.valueOf(Class+1)+ teachers.get(i).charAt(1);
            if(teachers.get(i).contains("11"))
            {
                statement.executeUpdate(String.format("UPDATE список_учителей SET Основной_класс = NULL where Основной_класс = '%s'", teachers.get(i)));
            }
            else{
                statement.executeUpdate(String.format("UPDATE список_учителей SET Основной_класс = '%s' where Основной_класс = '%s'", newClass, teachers.get(i)));
            }
        }
        for(int i = 0; i<teacherClasses.size(); i++)
        {
            String result="";
            String[] Classes = teacherClasses.get(i).split(",");
            for(int j = 0; j<Classes.length; j++) {
                int Class = Character.getNumericValue(Classes[j].charAt(0));
                String newClass = String.valueOf(Class + 1) + Classes[j].charAt(1);
                if(!newClass.contains("11")) {
                    result += newClass + ",";
                }
            }
            result=result.substring(0,result.length()-1);
            statement.executeUpdate(String.format("UPDATE список_учителей SET Классы = '%s' where Классы = '%s'",result,teacherClasses.get(i)));
        }
        List<String> tabs = new ArrayList<>();
        for(String ss:tabsList)
        {
            if(ss.split("_").length==3)
            {
                statement.executeUpdate(String.format("DROP TABLE %s",ss));
            }
            else if(ss.split("_").length==4)
            {
                tabs.add(ss);
            }
        }

        for(String s:tabs)
        {
            String pattern;
            List<String> pupilsList = new ArrayList<>();
            if(s.contains("11"))
            {
                statement.executeUpdate(String.format("DROP TABLE %s",s));
            }
            else if(s.split("_").length == 4)
            {
                ResultSet pupilSet = statement.executeQuery(String.format("select * from %s",s));
                while (pupilSet.next())
                    pupilsList.add(pupilSet.getString("Список_учеников"));
                int Class = Character.getNumericValue(s.charAt(0));
                pattern = String.valueOf(Class + 1) + s.substring(1);
                statement.executeUpdate(String.format("DROP TABLE %s",s));
                statement.executeUpdate(String.format("CREATE TABLE %s (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,Список_учеников TEXT)",pattern));
                for(String s_:pupilsList)
                    statement.executeUpdate(String.format("INSERT INTO %s (Список_учеников) values ('%s')",pattern,s_));

                createtables("русскийязык",Listmonth, String.valueOf(Class + 1) + s.charAt(1),pupilsList);
                createtables("математика",Listmonth, String.valueOf(Class + 1) + s.charAt(1),pupilsList);
                createtables("история",Listmonth, String.valueOf(Class + 1) + s.charAt(1),pupilsList);
            }
        }
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Перевод на следующий год");
        alert1.setHeaderText("Перевод на следующий год");
        alert1.setContentText("Перевод на следующий год прошел успешно");
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
