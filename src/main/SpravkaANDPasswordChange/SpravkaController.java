package main.SpravkaANDPasswordChange;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class SpravkaController
{
    @FXML
    private TextArea text_spravka;

    private Stage dialogStage;

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    private boolean id;

    @FXML
    private void initialize() {
        text_spravka.setText("Здесь будет информации, как пользоваться ПО");

    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
}
