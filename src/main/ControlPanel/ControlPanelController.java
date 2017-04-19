package main.ControlPanel;


import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.Avtorization.AvtorizationPageController;
import main.MainDiary;

import java.io.IOException;

public class ControlPanelController
{
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private ImageView imageView4;
    @FXML
    private ImageView imageView5;
    @FXML
    private ImageView imageView6;
    @FXML
    private ImageView imageView7;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private AnchorPane anchorPane1;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;

    private MainDiary mainDiary;

    private Stage stage;

    private AnchorPane rightPane;

    @FXML
    private void initialize() throws IOException {
        setBackgroundImageImageView();

        BackgroundImage myBI= new BackgroundImage(new Image("file:resources/images/gradient.jpg"),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        anchorPane1.setBackground(new Background(myBI));

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("ActionWindow/WindowNachalo.fxml"));

        rightPane = fxmlLoader.load();

        rightPane.setLayoutX(320);
        rightPane.setLayoutY(-3);

        anchorPane.getChildren().addAll(rightPane);
    }

    public void setBackgroundImageImageView()
    {
        imageView1.setImage(new Image("file:resources/images/iconAdd.png"));
        imageView2.setImage(new Image("file:resources/images/iconRemoveUser.png"));
        imageView3.setImage(new Image("file:resources/images/iconGroup.png"));
        imageView4.setImage(new Image("file:resources/images/iconE.png"));
        imageView5.setImage(new Image("file:resources/images/iconOtchet.png"));
        imageView6.setImage(new Image("file:resources/images/iconOtchet.png"));
        imageView7.setImage(new Image("file:resources/images/iconV.png"));
    }



    @FXML
    private void label1clickmouse () throws IOException {
        anchorPane.getChildren().remove(rightPane);
        label1.setStyle("-fx-text-fill: #00fff2;");
        label2.setStyle("-fx-text-fill: white;");
        label3.setStyle("-fx-text-fill: white;");
        label4.setStyle("-fx-text-fill: white;");
        label5.setStyle("-fx-text-fill: white;");
        label6.setStyle("-fx-text-fill: white;");
        label7.setStyle("-fx-text-fill: white;");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("ActionWindow/WindowUserRegistration.fxml"));

        rightPane = fxmlLoader.load();

        rightPane.setLayoutX(320);
        rightPane.setLayoutY(-3);

        anchorPane.getChildren().addAll(rightPane);
    }
    @FXML
    private void label2clickmouse () throws IOException {
        anchorPane.getChildren().remove(rightPane);
        label2.setStyle("-fx-text-fill: #00fff2;");
        label1.setStyle("-fx-text-fill: white;");
        label3.setStyle("-fx-text-fill: white;");
        label4.setStyle("-fx-text-fill: white;");
        label5.setStyle("-fx-text-fill: white;");
        label6.setStyle("-fx-text-fill: white;");
        label7.setStyle("-fx-text-fill: white;");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("ActionWindow/UserDeletionWindow.fxml"));

        rightPane = fxmlLoader.load();

        rightPane.setLayoutX(320);
        rightPane.setLayoutY(-3);

        anchorPane.getChildren().addAll(rightPane);
    }
    @FXML
    private void label3clickmouse () throws IOException {
        anchorPane.getChildren().remove(rightPane);
        label3.setStyle("-fx-text-fill: #00fff2;");
        label1.setStyle("-fx-text-fill: white;");
        label2.setStyle("-fx-text-fill: white;");
        label4.setStyle("-fx-text-fill: white;");
        label5.setStyle("-fx-text-fill: white;");
        label6.setStyle("-fx-text-fill: white;");
        label7.setStyle("-fx-text-fill: white;");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("ActionWindow/FormationClassWindow.fxml"));

        rightPane = fxmlLoader.load();

        rightPane.setLayoutX(320);
        rightPane.setLayoutY(-3);

        anchorPane.getChildren().addAll(rightPane);
    }
    @FXML
    private void label4clickmouse () throws IOException {
        anchorPane.getChildren().remove(rightPane);
        label4.setStyle("-fx-text-fill: #00fff2;");
        label1.setStyle("-fx-text-fill: white;");
        label2.setStyle("-fx-text-fill: white;");
        label3.setStyle("-fx-text-fill: white;");
        label5.setStyle("-fx-text-fill: white;");
        label6.setStyle("-fx-text-fill: white;");
        label7.setStyle("-fx-text-fill: white;");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("ActionWindow/EditWindow.fxml"));

        rightPane = fxmlLoader.load();

        rightPane.setLayoutX(320);
        rightPane.setLayoutY(-3);

        anchorPane.getChildren().addAll(rightPane);
    }
    @FXML
    private void label5clickmouse () throws IOException {
        anchorPane.getChildren().remove(rightPane);
        label5.setStyle("-fx-text-fill: #00fff2;");        label1.setStyle("-fx-text-fill: white;");
        label2.setStyle("-fx-text-fill: white;");
        label3.setStyle("-fx-text-fill: white;");
        label4.setStyle("-fx-text-fill: white;");
        label6.setStyle("-fx-text-fill: white;");
        label7.setStyle("-fx-text-fill: white;");


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("ActionWindow/ReportWindowU.fxml"));

        rightPane = fxmlLoader.load();

        rightPane.setLayoutX(320);
        rightPane.setLayoutY(-3);

        anchorPane.getChildren().addAll(rightPane);
    }
    @FXML
    private void label6clickmouse () throws IOException {
        anchorPane.getChildren().remove(rightPane);
        label6.setStyle("-fx-text-fill: #00fff2;");
        label1.setStyle("-fx-text-fill: white;");
        label2.setStyle("-fx-text-fill: white;");
        label3.setStyle("-fx-text-fill: white;");
        label4.setStyle("-fx-text-fill: white;");
        label5.setStyle("-fx-text-fill: white;");
        label7.setStyle("-fx-text-fill: white;");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("ActionWindow/ReportWindowP.fxml"));

        rightPane = fxmlLoader.load();

        rightPane.setLayoutX(320);
        rightPane.setLayoutY(-3);

        anchorPane.getChildren().addAll(rightPane);
    }
    @FXML
    private void label7clickmouse () throws IOException {
        label7.setStyle("-fx-text-fill: #00fff2;");
        label1.setStyle("-fx-text-fill: white;");
        label2.setStyle("-fx-text-fill: white;");
        label3.setStyle("-fx-text-fill: white;");
        label4.setStyle("-fx-text-fill: white;");
        label5.setStyle("-fx-text-fill: white;");
        label6.setStyle("-fx-text-fill: white;");
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
