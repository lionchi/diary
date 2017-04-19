package main.ConfirmationDialog;

import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import main.ActionWindow.FormationClassWindowController;
import main.ActionWindow.UserDeletionWindowController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DialogDeleteController
{
    public ObservableList<String> getPupilsList() {
        return pupilsList;
    }

    public void setPupilsList(ObservableList<String> pupilsList) {
        this.pupilsList = pupilsList;
    }

    public ObservableList<String> getTeachersList() {
        return teachersList;
    }

    public void setTeachersList(ObservableList<String> teachersList) {
        this.teachersList = teachersList;
    }

    public String getFIO_pupil() {
        return FIO_pupil;
    }

    public void setFIO_pupil(String FIO_pupil) {
        this.FIO_pupil = FIO_pupil;
    }

    public String getFIO_teacher() {
        return FIO_teacher;
    }

    public void setFIO_teacher(String FIO_teacher) {
        this.FIO_teacher = FIO_teacher;
    }

    private Stage dialogStage;
    private String FIO_pupil;
    private String FIO_teacher;
    private String Class;
    private ObservableList<String> pupilsList = FXCollections.observableArrayList();
    private  ObservableList<String> teachersList = FXCollections.observableArrayList();
    private List<String> tabsList = new ArrayList<>();

    @FXML
    private void initialize() {}

    @FXML
    private void buttonDa() throws SQLException {
        dialogStage.close();
        DBWorker db = new DBWorker();
        Statement statement = db.getConnection().createStatement();
        ResultSet setResult = statement.executeQuery("SHOW TABLES FROM diary");
        while (setResult.next()){
            tabsList.add(setResult.getString("Tables_in_diary"));
        }

        if(FIO_pupil!= null) {
            if (pupilsList.contains(FIO_pupil)) {
                ResultSet ClassSet = statement.executeQuery(String.format("select Класс from список_учеников where Логин = '%s'", FIO_pupil));
                while (ClassSet.next()) {
                    Class = ClassSet.getString("Класс");
                }
                statement.executeUpdate(String.format("DELETE from Список_учеников WHERE Логин = '%s'",FIO_pupil));
                if (Class != null) {
                    for (String s : tabsList) {
                        if (s.contains(Class)) {
                                statement.executeUpdate(String.format("DELETE from %s WHERE Список_учеников = '%s'", s, FIO_pupil));
                            }
                        }
                    }
                } else {
                    statement.executeUpdate(String.format("DELETE from список_учеников WHERE Логин = '%s'", FIO_pupil));
                }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Удаление");
            alert1.setHeaderText("Удаление");
            alert1.setContentText("Удаление прошло успешно");
            alert1.showAndWait();
            }

        if(FIO_teacher!=null)
        {
            if (teachersList.contains(FIO_teacher)) {
                statement.executeUpdate(String.format("DELETE from список_учителей WHERE Логин = '%s'", FIO_teacher));
            }
            Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
            alert1.setTitle("Удаление");
            alert1.setHeaderText("Удаление");
            alert1.setContentText("Удаление прошло успешно. Сменить классного руководителя Вы можете в разделе Редактирование класса");
            alert1.showAndWait();
        }

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
