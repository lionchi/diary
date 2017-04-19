package main.ConfirmationDialog;


import DBWorkerPackage.DBWorker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DialogChangeController {

    private Stage dialogStage;

    public String getFIO_teacher() {
        return FIO_teacher;
    }

    public void setFIO_teacher(String FIO_teacher) {
        this.FIO_teacher = FIO_teacher;
    }

    public String getCclass() {
        return Class;
    }

    public void setCclass(String aClass) {
        Class = aClass;
    }

    public List<String> getTabsList() {
        return tabsList;
    }

    public void setTabsList(List<String> tabsList) {
        this.tabsList = tabsList;
    }

    public ObservableList<String> getTeachersList() {
        return teachersList;
    }

    public void setTeachersList(ObservableList<String> teachersList) {
        this.teachersList = teachersList;
    }

    public List<String> getClassList() {
        return classList;
    }

    public void setClassList(List<String> classList) {
        this.classList = classList;
    }

    private String FIO_teacher;
    private String Class;
    private List<String> tabsList = new ArrayList<>();
    private  ObservableList<String> teachersList = FXCollections.observableArrayList();
    private  List<String> classList = new ArrayList<String>();

    @FXML
    private void initialize() {}

    @FXML
    private void buttonDa() throws SQLException {
        dialogStage.close();
        DBWorker db = new DBWorker();
        Statement statement = db.getConnection().createStatement();
        String pattern = Class+"_"+FIO_teacher.replace(' ','_').toLowerCase();
        if(classList.contains(Class))
        {
            statement.executeUpdate(String.format("UPDATE список_учителей SET Основной_класс = NULL where Основной_класс = '%s'", Class));
            statement.executeUpdate(String.format("UPDATE список_учителей SET Основной_класс = '%s' where Логин = '%s'", Class,FIO_teacher));
        }
        else{
            statement.executeUpdate(String.format("UPDATE список_учителей SET Основной_класс = '%s' where Логин = '%s'", Class,FIO_teacher));
        }
        for (String s:tabsList) {
            if(s.split("_").length==4 && s.contains(Class))
            {
                statement.executeUpdate(String.format("RENAME TABLE %s TO %s", s,pattern));
                break;
            }
        }
        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
        alert1.setTitle("Смена КР");
        alert1.setHeaderText("Смена КР");
        alert1.setContentText("Смена классного руководителя прошла успешно");
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
