package main.ActionWindow;


import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class FormationClassWindowController {

    private ObservableList<String> pupilsList = FXCollections.observableArrayList();
    private  ObservableList<String> teachersList = FXCollections.observableArrayList();
    private List<String> pupilClass = new ArrayList<>();
    private List<String> teacherClass = new ArrayList<>();
    private List<String> Listmonth = new ArrayList<>();


    @FXML
    private TextField classForm;
    @FXML
    private ChoiceBox<String> pupil=new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> classTeacher = new ChoiceBox<String>();


    @FXML
    private void  initialize() throws SQLException {
        Listmonth.add("октябрь");
        Listmonth.add("декабрь");
        Listmonth.add("март");
        Listmonth.add("май");
        fill();
        pupil.setItems(pupilsList);
        classTeacher.setItems(teachersList);

    }

    private void fill() throws SQLException {
        DBWorker dbWorker = new DBWorker();

        Statement statement = dbWorker.getConnection().createStatement();

        ResultSet setPupils = statement.executeQuery("select * from список_учеников");

        while (setPupils.next()){

            pupilsList.add(setPupils.getString("Логин"));
            pupilClass.add(setPupils.getString("Класс"));
        }
        List<String> copy_pupilList = new ArrayList<>();
        for(int i = 0; i<pupilClass.size(); i++)
        {
            if( pupilClass.get(i)==null)
            {
                copy_pupilList.add(pupilsList.get(i));
            }
            else if (pupilClass.get(i).isEmpty())
            {
                copy_pupilList.add(pupilsList.get(i));
            }
        }
        pupilsList.clear();
        pupilsList.addAll(copy_pupilList);

        ResultSet setTeachers = statement.executeQuery("select * from список_учителей");

        while (setTeachers.next()){
            teachersList.add(setTeachers.getString("Логин"));
            teacherClass.add(setTeachers.getString("Основной_класс"));
        }
    }

    private boolean check()
    {
        boolean flag = true;
        int count = 0;

        int indexP = pupilsList.indexOf(pupil.getValue());
        int indexT = teachersList.indexOf(classTeacher.getValue());

        if(teacherClass.get(indexT)!=null && !teacherClass.get(indexT).isEmpty()) {
            if (!teacherClass.get(indexT).trim().equals(classForm.getText())) {
                flag = false;
                count ++;
            }
        }
        if(teacherClass.contains(classForm.getText()))
        {
            int indexC =teacherClass.indexOf(classForm.getText());
            if(!teachersList.get(indexC).equals(classTeacher.getValue()))
            {
                flag = false;
                count +=2;
            }
        }
        switch (count)
        {
            case 1:Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Ошибка");
                alert1.setHeaderText("Ошибка добавления");
                alert1.setContentText(String.format("Данный учитель уже принадлежит классу '%s'",teacherClass.get(indexT)));
                alert1.showAndWait();
                break;
            case 2:Alert alert6 = new Alert(Alert.AlertType.ERROR);
                alert6.setTitle("Ошибка");
                alert6.setHeaderText("Ошибка добавления");
                alert6.setContentText(String.format("У данного класса другой классный руководитель",pupilClass.get(indexP)));
                alert6.showAndWait();
                break;
            case 3:Alert alert4 = new Alert(Alert.AlertType.ERROR);
                alert4.setTitle("Ошибка");
                alert4.setHeaderText("Ошибка добавления");
                alert4.setContentText(String.format("Данный учитель уже принадлежит классу '%s', а у данного класса другой классный руководитель",teacherClass.get(indexT)));
                alert4.showAndWait();
                break;
        }


        return flag;
    }

    private void createtables (String subject, List<String> Listmonth) throws SQLException {
        DBWorker dbWorker = new DBWorker();

        Statement statement = dbWorker.getConnection().createStatement();

        for (int i=0; i<4;i++)
        {
            String pattern = classForm.getText()+"_"+subject+"_"+Listmonth.get(i);
            statement.executeUpdate(String.format("CREATE TABLE %s (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,Список_учеников TEXT, 1_ TEXT, 2_ TEXT,3_ TEXT,4_ TEXT,5_ TEXT,6_ TEXT,7_ TEXT,8_ TEXT,9_ TEXT,10_ TEXT,11_ TEXT,12_ TEXT,13_ TEXT,14_ TEXT,15_ TEXT,16_ TEXT,17_ TEXT,18_ TEXT,19_ TEXT,20_ TEXT,21_ TEXT,22_ TEXT,23_ TEXT,24_ TEXT,25_ TEXT,26_ TEXT,27_ TEXT,28_ TEXT,29_ TEXT,30_ TEXT,31_ TEXT, Четверть TEXT)",pattern));
            statement.executeUpdate(String.format("INSERT INTO %s (Список_учеников) values ('%s')",pattern,pupil.getValue()));
        }
    }

    @FXML
    private void form() throws SQLException {
        List<String> tabsList = new ArrayList<String>();
        String ct = classTeacher.getValue().replace(' ', '_');
        String pattern = classForm.getText() + '_' + ct.toLowerCase();

        DBWorker dbWorker = new DBWorker();

        Statement statement = dbWorker.getConnection().createStatement();

        ResultSet setResult = statement.executeQuery("SHOW TABLES FROM diary");
        while (setResult.next()){
            tabsList.add(setResult.getString("Tables_in_diary"));
        }
        boolean isTrue=true;
        if(check()==true)
        {
            if(tabsList.contains(pattern))
            {
                statement.executeUpdate(String.format("INSERT INTO %s (Список_учеников) values ('%s')",pattern,pupil.getValue()));
                statement.executeUpdate(String.format("UPDATE список_учеников SET Класс = '%s' where Логин = '%s'",classForm.getText(),pupil.getValue()));
                for (String s:tabsList) {
                  if(s.contains(classForm.getText())&& !s.equals(pattern))
                  {
                      statement.executeUpdate(String.format("INSERT INTO %s (Список_учеников) values ('%s')",s,pupil.getValue()));
                  }
                }

            }
            else
            {
                statement.executeUpdate(String.format("CREATE TABLE %s (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,Список_учеников TEXT)",pattern));
                statement.executeUpdate(String.format("INSERT INTO %s (Список_учеников) values ('%s')",pattern,pupil.getValue()));
                statement.executeUpdate(String.format("UPDATE список_учеников SET Класс = '%s' where Логин = '%s'",classForm.getText(),pupil.getValue()));
                statement.executeUpdate(String.format("UPDATE список_учителей SET Основной_класс = '%s' where Логин = '%s'",classForm.getText(),classTeacher.getValue()));

                createtables("русскийязык",Listmonth);
                createtables("математика",Listmonth);
                createtables("история",Listmonth);
            }
            Alert alert5 = new Alert(Alert.AlertType.INFORMATION);
            alert5.setTitle("Формирование");
            alert5.setHeaderText("Формирование");
            alert5.setContentText("Формирование прошло успешно");
            alert5.showAndWait();
        }

    }
}

