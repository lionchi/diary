package main.PasswordRecoveryView;


import DBWorkerPackage.DBWorker;
import DBWorkerPackage.Pupils;
import DBWorkerPackage.Teachers;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import main.Avtorization.AvtorizationPageController;
import main.MainDiary;

public class PasswordRecoveryController
{
    @FXML
    private TextField Mail;

    @FXML
    private TextField login;

    @FXML
    private Button send;

    private String pass;

    private boolean isTrue = true;

    private MainDiary mainDiary;

    private Stage dialogStage;

    private boolean ButtonClicked;

    Pupils pupils = new Pupils();
    Teachers teachers = new Teachers();

    private AvtorizationPageController pageController;

    private  List<Pupils> pupilsList = new ArrayList<>();
    private  List<Teachers> teachersList = new ArrayList<>();

    /**
     * Инициализирует класс-контроллер. Этот метод вызывается автоматически
     * после того, как fxml-файл будет загружен.
     */
    @FXML
    private void initialize()
    {
        Mail.setTooltip(new Tooltip("Введите почтовый адрес your.mail@yandex.ru"));
    }

    /**
     * Устанавливает сцену для этого окна.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Вызывается, когда пользователь кликнул по кнопке Отправить код.
     */

    private  String check() throws SQLException {
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


        for (Teachers teachers : teachersList) {
            if (teachers.getLogin().equals(login.getText())) {
                pass = teachers.getPassword();
                isTrue = false;
            }
            if (isTrue == false) {
                break;
            }
        }
        if (isTrue!=false) {
            for (Pupils pupils : pupilsList) {
                if (pupils.getLogin().equals(login.getText())) {
                    pass = pupils.getPassword();
                    isTrue = false;
                }
                if (isTrue == false) {
                    break;
                }
            }
        }
        if (isTrue == true) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(pageController.getStage());
            alert.setTitle("Ошибка");
            alert.setHeaderText("Такого логина не существует");
            alert.setContentText("Повторите попытку");

            alert.showAndWait();
        }

        return pass;
    }

    private void Recovery(String mail, String pass)
    {
        String to = mail;         // sender email
        String from = "tayfun.electronebook@mail.ru";       // receiver email

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", "smtp.mail.ru");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", 2525);
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("tayfun.electronebook", "tayfun12345"); //логин и пароль для аутентификации на сервере
                    }
                });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from)); //откуда отправляю письмо
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to)); //куда  отправляется письмо
            message.setSubject("Восстановление пароля");     //тема письма
            message.setText("Ваш пароль: " + pass); //содержимое письма
            Transport.send(message);
            System.out.println("Done");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.initOwner(pageController.getStage());
            alert.setTitle("Код");
            alert.setHeaderText("Код отправлен");
            alert.setContentText("На ваш почтовый адрес отправлен пароль");
            alert.showAndWait();
            send.setDisable(true);

        } catch (MessagingException e) {
            throw new RuntimeException(e);    }
    }

    @FXML
    private void handleButton() throws SQLException {
        String mail = Mail.getText();
        String FIO = login.getText();

        if(!mail.isEmpty()|| !FIO.isEmpty()) {
            pass = check();
            if(pass != null)
                Recovery(mail, pass);
        }

        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(pageController.getStage());
            alert.setTitle("Ошибка ввода почтового адреса");
            alert.setHeaderText("Заполните пустые поля");
            alert.setContentText("Повторите попытку");

            alert.showAndWait();
        }
    }

    public boolean isButtonClicked() {
        return ButtonClicked;
    }


    public void setPageController(AvtorizationPageController pageController) {
        this.pageController = pageController;
    }
}
