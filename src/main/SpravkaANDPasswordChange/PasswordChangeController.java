package main.SpravkaANDPasswordChange;


import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PasswordChangeController
{
    @FXML
    private TextField old_pass;
    @FXML
    private TextField new_pass;
    @FXML
    private TextField confirmation_new_pass;

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }
    private List<String> pupilsList = new ArrayList<String>();
    private List<String> teacherList = new ArrayList<String>();

    private boolean id;


    private Stage dialogStage;

    @FXML
    private void initialize() throws SQLException {
        fill();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private void fill() throws SQLException {

        DBWorker dbWorker = new DBWorker();
        Statement statement = dbWorker.getConnection().createStatement();
        ResultSet setPupils = statement.executeQuery("select * from список_учеников");

        while (setPupils.next()){

            pupilsList.add(setPupils.getString("Пароль"));
        }

        ResultSet setTeachers = statement.executeQuery("select * from список_учителей");

        while (setTeachers.next()){
            teacherList.add(setTeachers.getString("Пароль"));

        }


    }

    @FXML
    private void buttonchange() throws SQLException {

        DBWorker dbWorker = new DBWorker();
        Statement statement = dbWorker.getConnection().createStatement();

        if (new_pass.getText().equals(confirmation_new_pass.getText())){
            if (id == false){
                if (pupilsList.contains(old_pass.getText())){
                    statement.executeUpdate(String.format("UPDATE список_учеников SET Пароль = '%s' where Пароль = '%s'", new_pass.getText(),old_pass.getText()));

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Изменение пароля");
                    alert1.setHeaderText("Изменение пароля");
                    alert1.setContentText("Изменение пароля прошло успешно");
                    alert1.showAndWait();
                }
                else
                {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Проверка пароля");
                    alert1.setHeaderText("Проверка пароля");
                    alert1.setContentText("Вы ввели неверный пароль");
                    alert1.showAndWait();
                }

            }
            else {
                if (teacherList.contains(old_pass.getText())){
                    statement.executeUpdate(String.format("UPDATE список_учителей SET Пароль = '%s' where Пароль = '%s'", new_pass.getText(),old_pass.getText()));

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Изменение пароля");
                    alert1.setHeaderText("Изменение пароля");
                    alert1.setContentText("Изменение пароля прошло успешно");
                    alert1.showAndWait();
                }
                else {Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setTitle("Проверка пароля");
                    alert1.setHeaderText("Проверка пароля");
                    alert1.setContentText("Вы ввели неверный пароль");
                    alert1.showAndWait();}
            }
        }
        else { Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Проверка пароля");
            alert1.setHeaderText("Проверка пароля");
            alert1.setContentText("Повторите ввод нового пароля");
            alert1.showAndWait();}
    }
}
