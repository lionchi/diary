package main.ActionWindow;


import DBWorkerPackage.DBWorker;
import com.sun.org.apache.bcel.internal.generic.NEW;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.ConfirmationDialog.DialogChangeController;
import main.ConfirmationDialog.DialogDeleteController;
import main.ConfirmationDialog.DialogTranslationController;
import main.MainDiary;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditWindowController
{
    private ObservableList<String> classes = FXCollections.observableArrayList();
    private ObservableList<String> pupilsList = FXCollections.observableArrayList();
    private  ObservableList<String> teachersList = FXCollections.observableArrayList();
    private  List<String> classList = new ArrayList<String>();
    private List<String> tabsList = new ArrayList<>();

    @FXML
    private ChoiceBox<String> Pupil=new ChoiceBox<>();
    @FXML
    private ChoiceBox<String> Teacher = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> Class = new ChoiceBox<String>();
    @FXML
    private ChoiceBox<String> NewClass = new ChoiceBox<String>();

    private String new_class;
    private String old_class;

    @FXML
    private void initialize() throws SQLException {
        fill();
        Pupil.setItems(pupilsList);
        Teacher.setItems(teachersList);
        Class.setItems(classes);
        NewClass.setItems(classes);
    }

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
            classList.add((setTeachers.getString("Основной_класс")));
        }

        List<String> copy_teacherList = new ArrayList<>();
        for(int i = 0; i<teachersList.size(); i++)
        {
            if( classList.get(i)==null)
            {
                copy_teacherList.add(teachersList.get(i));
            }
            else if (classList.get(i).isEmpty())
            {
                copy_teacherList.add(teachersList.get(i));
            }
        }
        teachersList.clear();
        teachersList.addAll(copy_teacherList);

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
    private void buttonChangeCU() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainDiary.class.getResource("ConfirmationDialog/DialogChange.fxml"));

        AnchorPane page = loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Подтверждение внесенных изменений");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.getIcons().add(new Image("file:resources/images/iconP.png"));

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        DialogChangeController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setCclass(Class.getValue());
        controller.setFIO_teacher(Teacher.getValue());
        controller.setClassList(classList);
        controller.setTeachersList(teachersList);
        controller.setTabsList(tabsList);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
    }
    @FXML
    private void buttonBringStudent() throws SQLException {
        new_class = NewClass.getValue();
        DBWorker db = new DBWorker();
        Statement statement = db.getConnection().createStatement();
        ResultSet  resultSet = statement.executeQuery(String.format("select Класс from список_учеников where Логин = '%s'",Pupil.getValue()));
        while (resultSet.next()){
            old_class = resultSet.getString("Класс");
        }
        if(!old_class.equals(new_class)) {
            statement.executeUpdate(String.format("UPDATE список_учеников SET Класс = '%s' where Логин = '%s'", new_class, Pupil.getValue()));
            for (String s : tabsList) {
                if (s.contains(new_class)) {
                    statement.executeUpdate(String.format("INSERT INTO %s (Список_учеников) values ('%s')", s, Pupil.getValue()));
                } else if (s.contains(old_class)) {
                    statement.executeUpdate(String.format("DELETE from %s WHERE Список_учеников = '%s'", s, Pupil.getValue()));
                }
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Перевод ученика");
            alert1.setHeaderText("Перевод ученика");
            alert1.setContentText("Перевод ученика прошел успешно");
            alert1.showAndWait();
        }
        else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Перевод ученика");
            alert1.setHeaderText("Перевод ученика");
            alert1.setContentText("Ученик уже учится в этом классе");
            alert1.showAndWait();
        }
    }
    @FXML
    private void buttonTranslation() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainDiary.class.getResource("ConfirmationDialog/DialogTranslation.fxml"));

        AnchorPane page = loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Подтверждение перевода");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.getIcons().add(new Image("file:resources/images/iconP.png"));

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        DialogTranslationController controller = loader.getController();
        controller.setDialogStage(dialogStage);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
    }

}
