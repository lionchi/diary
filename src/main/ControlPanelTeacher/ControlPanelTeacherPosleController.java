package main.ControlPanelTeacher;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Avtorization.AvtorizationPageController;
import main.MainDiary;
import main.SpravkaANDPasswordChange.PasswordChangeController;
import main.SpravkaANDPasswordChange.SpravkaController;

import java.io.IOException;

public class ControlPanelTeacherPosleController {
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private ChoiceBox Class;
    @FXML
    private ChoiceBox subject;
    @FXML
    private AnchorPane anchorPane;

    private MainDiary mainDiary;

    private Stage stage;


    private String FIO;
    private String pass;
    private boolean id = true;

    @FXML
    private void initialize() throws IOException {
        setBackgroundImageImageView();

        BackgroundImage myBI= new BackgroundImage(new Image("file:resources/images/gradient.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        anchorPane.setBackground(new Background(myBI));
    }

    public void setBackgroundImageImageView()
    {
        imageView1.setImage(new Image("file:resources/images/iconAddOcenki.png"));
        imageView2.setImage(new Image("file:resources/images/iconRecoveryParol.png"));
        imageView3.setImage(new Image("file:resources/images/iconSpravka.png"));
        imageView4.setImage(new Image("file:resources/images/iconV.png"));
    }

    @FXML
    private void label1clickmouse ()
    {

    }

    @FXML
    private void label2clickmouse () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainDiary.class.getResource("SpravkaANDPasswordChange/PasswordChangeView.fxml"));

        AnchorPane page = loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Изменения пароля");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.getIcons().add(new Image("file:resources/images/iconRecoveryParol.png"));

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        PasswordChangeController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setId(id);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
    }
    @FXML
    private void label3clickmouse () throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainDiary.class.getResource("SpravkaANDPasswordChange/SpravkaView.fxml"));

        AnchorPane page = loader.load();

        // Создаём диалоговое окно Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Справка");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.getIcons().add(new Image("file:resources/images/iconSpravka.png"));

        Scene scene = new Scene(page);
        dialogStage.setScene(scene);


        SpravkaController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setId(id);

        // Отображаем диалоговое окно и ждём, пока пользователь его не закроет
        dialogStage.showAndWait();
    }

    @FXML
    private void label4clickmouse () throws IOException {
        stage.close();

        stage.getIcons().clear();

        stage.setTitle("Авторизация");

        // Устанавливаем иконку авторизации.
        stage.getIcons().add(new Image("file:resources/images/iconAA.png"));

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("Avtorization/AvtorizationPage.fxml"));

        AnchorPane anchorPane = fxmlLoader.load();

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);

        AvtorizationPageController controller = fxmlLoader.getController();
        //Нужно чтобы не потерять ссылку на Stage
        controller.setMainDiary(mainDiary);

        stage.show();
    }

    @FXML
    private void label1mousemoved() {label1.setStyle("-fx-text-fill: #00fff2;");}
    @FXML
    private void label2mousemoved() {label2.setStyle("-fx-text-fill: #00fff2;");}
    @FXML
    private void label3mousemoved() {label3.setStyle("-fx-text-fill: #00fff2;");}
    @FXML
    private void label4mousemoved() {label4.setStyle("-fx-text-fill: #00fff2;");}
    @FXML
    private void label1mouseexited() {label1.setStyle("-fx-text-fill: white;");}
    @FXML
    private void label2mouseexited() {label2.setStyle("-fx-text-fill: white;");}
    @FXML
    private void label3mouseexited() {label3.setStyle("-fx-text-fill: white;");}
    @FXML
    private void label4mouseexited() {label4.setStyle("-fx-text-fill: white;");}

    @FXML
    private void buttonselection() throws IOException {

    }


    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setMainDiary(MainDiary mainDiary) {
        this.mainDiary = mainDiary;
    }
}
