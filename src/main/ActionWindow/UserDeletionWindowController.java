package main.ActionWindow;


import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.ConfirmationDialog.DialogDeleteController;
import main.MainDiary;

import java.awt.*;
import java.awt.List;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDeletionWindowController
{

    @FXML
    private void initialize() throws SQLException {
        fill();
        Pupil.setItems(pupilsList);
        Teacher.setItems(teachersList);
    }

    private ObservableList<String> pupilsList = FXCollections.observableArrayList();
    private  ObservableList<String> teachersList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> Pupil=new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> Teacher = new ChoiceBox<String>();

    private void fill() throws SQLException {
        DBWorker dbWorker = new DBWorker();

        Statement statement = dbWorker.getConnection().createStatement();

        ResultSet setPupils = statement.executeQuery("select * from список_учеников");

        while (setPupils.next()){

            pupilsList.add(setPupils.getString("Логин"));
        }

        ResultSet setTeachers = statement.executeQuery("select * from список_учителей");

        while (setTeachers.next()){
            teachersList.add(setTeachers.getString("Логин"));
        }
    }

    @FXML
    private void buttonDeleteStudent() throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainDiary.class.getResource("ConfirmationDialog/DialogDelete.fxml"));

        AnchorPane page = loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Подтверждение удаления пользователя");
        dialogStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        DialogDeleteController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setFIO_pupil(Pupil.getValue());
        controller.setFIO_teacher(Teacher.getValue());
        controller.setPupilsList(pupilsList);
        controller.setTeachersList(teachersList);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();

    }
}
