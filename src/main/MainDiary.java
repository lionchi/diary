package main;

import DBWorkerPackage.DBWorker;
import DBWorkerPackage.Pupils;
import DBWorkerPackage.Teachers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.Avtorization.AvtorizationPageController;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainDiary extends Application {

    private Stage primaryStage;

    public static void main(String[] args) throws SQLException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Авторизация");

        // Устанавливаем иконку авторизации.
        this.primaryStage.getIcons().add(new Image("file:resources/images/iconAA.png"));

        initAutorization();
    }

    public void initAutorization() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainDiary.class.getResource("Avtorization/AvtorizationPage.fxml"));

        AnchorPane anchorPane = fxmlLoader.load();

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);

        AvtorizationPageController controller = fxmlLoader.getController();
        controller.setMainDiary(this);

        primaryStage.show();

    }

    public Stage getPrimaryStage()
    {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
