package main.ActionWindow;


import DBWorkerPackage.DBWorker;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WindowUserRegistrationController
{
    @FXML
    private TextField classes;
    @FXML
    private TextField subjects;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private RadioButton teacher;
    @FXML
    private RadioButton pupil;
    @FXML
    private boolean id;// true - учитель, false - ученик.

    private String FIO;
    private String pass;
    private String Classes;
    private String Subjects;




    @FXML
    private void  initialize(){
        login.setEditable(false);
        password.setEditable(false);
        classes.setEditable(false);
        subjects.setEditable(false);
    }

    @FXML
    private void hidingTextFild() {
        if(pupil.isSelected()) {
            id = false;
            classes.setTooltip(new Tooltip("Пример: 1а"));
            login.setEditable(true);
            password.setEditable(true);
            classes.setEditable(false);
            subjects.setEditable(false);

            teacher.setSelected(false);

            teacher.setDisable(true);
        }
        if(!pupil.isSelected()) {
            classes.setEditable(true);
            subjects.setEditable(true);

            teacher.setDisable(false);
        }

        if(teacher.isSelected()) {
            id = true;
            classes.setTooltip(new Tooltip("Пример: 1а,2б..."));
            subjects.setTooltip(new Tooltip("Пример: математика,чтение..."));

            login.setEditable(true);
            password.setEditable(true);

            pupil.setSelected(false);

            pupil.setDisable(true);
        }
        if(!teacher.isSelected()) {

            pupil.setDisable(false);
        }

        if(pupil.isSelected()==false && teacher.isSelected()==false) {
            login.setEditable(false);
            password.setEditable(false);
            classes.setEditable(false);
            subjects.setEditable(false);
        }
}
    @FXML
    private void buttonRegister() throws IOException, SQLException {
        FIO = login.getText();
        pass = password.getText();
        DBWorker dbWorker = new DBWorker();
        Statement statement = dbWorker.getConnection().createStatement();
        String query;
        if(id == true)
        {
            Classes = classes.getText();
            Subjects = subjects.getText();
            query = String.format("insert into список_учителей (Логин, Пароль, Предметы, Классы) values ('%s','%s','%s','%s')",FIO,pass,Subjects,Classes);
        }
        else
        {
            query = String.format("insert into список_учеников (Логин, Пароль) values ('%s','%s')",FIO,pass);
        }
        statement.executeUpdate(query);
        statement.close();

        //вывод сообщения
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Добавлено");
        alert.setHeaderText("Добавление");
        alert.setContentText("Добавление прошло успешно");
        alert.showAndWait();
    }
    }
