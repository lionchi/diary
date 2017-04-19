package main.Avtorization;

import DBWorkerPackage.DBWorker;
import DBWorkerPackage.Pupils;
import DBWorkerPackage.Teachers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.ControlPanel.ControlPanelController;
import main.ControlPanelStudent.ControlPanelStudentController;
import main.ControlPanelTeacher.ControlPanelTeacherDoConroller;
import main.MainDiary;
import main.PasswordRecoveryView.PasswordRecoveryController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AvtorizationPageController
{
    @FXML
    private TextField FIO;
    @FXML
    private PasswordField pass;

    private boolean id;

    private String fio;
    private String Pas;

    @FXML
    private Label passrecovery;

    private MainDiary mainDiary;
    private Stage stage;

    Pupils pupils = new Pupils();
    Teachers teachers = new Teachers();

    private AvtorizationPageController pageController;

    private  List<Pupils> pupilsList = new ArrayList<>();
    private  List<Teachers> teachersList = new ArrayList<>();


    @FXML
    private void  initialize() throws SQLException {
        FIO.setTooltip(new Tooltip("Введите ФИО"));
        FIO.setPromptText("Ф.И.О.");
        pass.setPromptText("********");

    }

    @FXML
    private void buttonAutorization() throws IOException, SQLException {

        DBWorker dbWorker = new DBWorker();

        Statement statement = dbWorker.getConnection().createStatement();

        ResultSet setPupils = statement.executeQuery("select * from список_учеников");

        while (setPupils.next()){
            pupils = new Pupils();
            pupils.setLogin(setPupils.getString("Логин"));
            pupils.setPassword(setPupils.getString("Пароль"));

            pupilsList.add(pupils);
        }

        ResultSet setTeachers = statement.executeQuery("select * from список_учителей");

        while (setTeachers.next()){
            teachers = new Teachers();
            teachers.setLogin(setTeachers.getString("Логин"));
            teachers.setPassword(setTeachers.getString("Пароль"));;

            teachersList.add(teachers);
        }

        fio = FIO.getText();
        Pas = pass.getText();
        boolean isTrue = true;
        if (fio.equals("admin") && Pas.equals("admin"))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(mainDiary.getPrimaryStage());
            alert.setTitle("Успешная авторизация");
            alert.setHeaderText("Вы вошли как администратор");
            alert.setContentText("Добро пожаловать в панель управления");

            alert.showAndWait();

            stage = mainDiary.getPrimaryStage();
            stage.close();
            stage = new Stage();
            stage.setTitle("Панель управления");

            stage.getIcons().add(new Image("file:resources/images/iconP.png"));

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainDiary.class.getResource("ControlPanel/ControlPanel.fxml"));

            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setScene(scene);
            mainDiary.setPrimaryStage(stage);

            ControlPanelController controller = fxmlLoader.getController();
            controller.setStage(stage);
            controller.setMainDiary(mainDiary);

            stage.show();

            isTrue = false;
        }
        else {
            for (Teachers teachers : teachersList) {
                if (teachers.getLogin().equals(fio) && teachers.getPassword().equals(Pas)) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initOwner(mainDiary.getPrimaryStage());
                    alert.setTitle("Успешная авторизация");
                    alert.setHeaderText("Вы вошли как учитель");
                    alert.setContentText("Добро пожаловать в панель учителя");

                    alert.showAndWait();

                    stage = mainDiary.getPrimaryStage();
                    stage.close();
                    stage = new Stage();
                    stage.setTitle("Панель учителя");

                    stage.getIcons().add(new Image("file:resources/images/iconP.png"));

                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(MainDiary.class.getResource("ControlPanelTeacher/ControlPanelTeacherViewDo.fxml"));

                    AnchorPane anchorPane = fxmlLoader.load();
                    Scene scene = new Scene(anchorPane);
                    stage.setScene(scene);
                    mainDiary.setPrimaryStage(stage);

                    ControlPanelTeacherDoConroller controller = fxmlLoader.getController();
                    controller.setStage(stage);
                    controller.setMainDiary(mainDiary);
                    controller.setFIO(fio);
                    controller.setPass(Pas);

                    stage.show();

                    isTrue = false;
                }
                if (isTrue == false) {
                    id = true;
                    break;
                }
            }
        }
        if (isTrue!=false){
                for (Pupils pupils : pupilsList) {
                    if (pupils.getLogin().equals(fio) && pupils.getPassword().equals(Pas)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.initOwner(mainDiary.getPrimaryStage());
                        alert.setTitle("Успешная авторизация");
                        alert.setHeaderText("Вы вошли как ученик");
                        alert.setContentText("Добро пожаловать в панель ученика");

                        alert.showAndWait();

                        stage = mainDiary.getPrimaryStage();
                        stage.close();
                        stage = new Stage();
                        stage.setTitle("Панель ученика");

                        stage.getIcons().add(new Image("file:resources/images/iconP.png"));

                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(MainDiary.class.getResource("ControlPanelStudent/ControlPanelStudentView.fxml"));

                        AnchorPane anchorPane = fxmlLoader.load();
                        Scene scene = new Scene(anchorPane);
                        stage.setScene(scene);
                        mainDiary.setPrimaryStage(stage);

                        ControlPanelStudentController controller = fxmlLoader.getController();
                        controller.setStage(stage);
                        controller.setMainDiary(mainDiary);
                        controller.setFIO(fio);
                        controller.setPass(Pas);


                        stage.show();

                        isTrue = false;
                    }
                if (isTrue == false) {
                    id = false;
                    break;
                }
            }
        }
        if (isTrue==true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainDiary.getPrimaryStage());
            alert.setTitle("Ошибка авторизации");
            alert.setHeaderText("Введен неверный логин/пароль");
            alert.setContentText("Повторите попытку");

            alert.showAndWait();
        }
    }

    @FXML
    private boolean labelclickmouse () throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainDiary.class.getResource("PasswordRecoveryView/PasswordRecovery.fxml"));

        AnchorPane page = loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Восстановление пароля");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(mainDiary.getPrimaryStage());
        dialogStage.getIcons().add(new Image("file:resources/images/iconPR.png"));
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        PasswordRecoveryController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setPageController(this);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();

        return controller.isButtonClicked();
    }

    @FXML
    private void labelmousemoved() {
        passrecovery.setStyle("-fx-text-fill: blue;");
        passrecovery.setCursor(Cursor.HAND);
    }

    @FXML
    private void labelmouseexited()
    {
        passrecovery.setStyle("-fx-text-fill: black;");
    }

    public  void  setMainDiary(MainDiary mainDiary)
    {
        this.mainDiary = mainDiary;
    }

    public Stage getStage() {
        return stage;
    }
}
