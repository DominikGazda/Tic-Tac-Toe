package gazda.controller;

import javafx.fxml.FXML;
import gazda.model.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class GamePaneController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField playerTextField;

    @FXML
    private Button button00Button;

    @FXML
    private Button button01Button;

    @FXML
    private Button button02Button;

    @FXML
    private Button button10Button;

    @FXML
    private Button button11Button;

    @FXML
    private Button button12Button;

    @FXML
    private Button button20Button;

    @FXML
    private Button button21Button;

    @FXML
    private Button button22Button;

    @FXML
    private TextField errorTextField;


    private static Shape shape;
    private Circle circle = new Circle();
    private Cross cross = new Cross();
    private Button[][] buttons;

    public void initialize() {
        changeTitleNameByShape();
        createButtonsTable();
        addButtonsAction();
    }

    private void changeTitleNameByShape() {
        if (shape instanceof Circle) {
            playerTextField.setText("Teraz wykonuje ruch kółko");
        } else if (shape instanceof Cross) {
            playerTextField.setText("Teraz wykonuje ruch krzyżyk");
        }
    }

    public static void getShape(Shape shape) {
        GamePaneController.shape = shape;
    }

    private void createButtonsTable() {
        Button[][] buttonsBoard = new Button[3][3];
        Button[] firstRow = {button00Button, button01Button, button02Button};
        Button[] secondRow = {button10Button, button11Button, button12Button};
        Button[] thirdRow = {button20Button, button21Button, button22Button};
        buttonsBoard[0] = firstRow;
        buttonsBoard[1] = secondRow;
        buttonsBoard[2] = thirdRow;
        buttons = buttonsBoard;
    }

    private void addButtonsAction() {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttonAction(buttons[i][j]);
            }
        }
    }

    private void buttonAction(Button button) {

        button.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (button.getText().equals("circle") || button.getText().equals("cross")) {
                showErrorMessage("To pole zostało odkryte");
                throw new IllegalArgumentException("To pole zostało wyświetlone");
            }

            if (shape instanceof Circle) {
                button.setId("circleGame");
                button.setText("circle");
                shape = cross;
                playerTextField.setText("Teraz ruch wykonuje krzyżyk");
            } else if (shape instanceof Cross) {
                button.setId("crossGame");
                button.setText("cross");
                shape = circle;
                playerTextField.setText("Teraz ruch wykonuje kółko");
            }
            applicationLogic();
        });
    }

    private void showErrorMessage(String message) {
        errorTextField.setText(message);
    }

    private void applicationLogic() {
        if (checkColumns(buttons) && checkCrossBoard(buttons) && checkRow(buttons)) {
            try {
                allLose(buttons);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkRow(Button[][] buttons) {
        int circleCounter = 0;
        int crossCounter = 0;
        for (int i = 0; i < buttons.length; i++) {
            for (Button button : buttons[i]) {
                if (button.getText().equals("circle"))
                    circleCounter++;
                else if (button.getText().equals("cross")) {
                    crossCounter++;
                }
            }
            if (circleCounter == 3) {
                try {
                    circleAlertWon();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            } else if (crossCounter == 3) {
                try {
                    crossAlertWon();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
            circleCounter = 0;
            crossCounter = 0;
        }
        return true;
    }

    private boolean checkColumns(Button[][] buttons) {
        try {
            if (buttons[0][0].getText().equals("circle") && buttons[1][0].getText().equals("circle") && buttons[2][0].getText().equals("circle")) {
                circleAlertWon();
                return false;
            } else if (buttons[0][1].getText().equals("circle") && buttons[1][1].getText().equals("circle") && buttons[2][1].getText().equals("circle")) {
                circleAlertWon();
                return false;
            }
            if (buttons[0][0].getText().equals("cross") && buttons[1][0].getText().equals("cross") && buttons[2][0].getText().equals("cross")) {
                crossAlertWon();
                return false;
            } else if (buttons[0][1].getText().equals("cross") && buttons[1][1].getText().equals("cross") && buttons[2][1].getText().equals("cross")) {
                crossAlertWon();
                return false;
            }
            if (buttons[0][2].getText().equals("cross") && buttons[1][2].getText().equals("cross") && buttons[2][2].getText().equals("cross")) {
                crossAlertWon();
                return false;
            } else if (buttons[0][2].getText().equals("circle") && buttons[1][2].getText().equals("circle") && buttons[2][2].getText().equals("circle")) {
                circleAlertWon();
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private boolean checkCrossBoard(Button[][] buttons) {
        try {
            if (buttons[0][0].getText().equals("circle") && buttons[1][1].getText().equals("circle") && buttons[2][2].getText().equals("circle")) {
                circleAlertWon();
                return false;
            } else if (buttons[0][2].getText().equals("circle") && buttons[1][1].getText().equals("circle") && buttons[2][0].getText().equals("circle")) {
                circleAlertWon();
                return false;
            } else if (buttons[0][0].getText().equals("cross") && buttons[1][1].getText().equals("cross") && buttons[2][2].getText().equals("cross")) {
                crossAlertWon();
                return false;
            } else if (buttons[0][2].getText().equals("cross") && buttons[1][1].getText().equals("cross") && buttons[2][0].getText().equals("cross")) {
                crossAlertWon();
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private void allLose(Button[][] buttons) throws IOException {
        int counter = 0;
        for (int i = 0; i < buttons.length; i++) {
            for (Button button : buttons[i]) {
                if (!button.getText().equals("")) {
                    counter++;
                }
            }
        }
        if (counter == 9) {
            lostAlert();
        }
    }

    private void lostAlert() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Niestety");
        alert.setHeaderText("Nikt nie wygrał");
        alert.setContentText("Spróbuj jeszcze raz !");
        alert.show();
        loadSecond();

    }

    private void circleAlertWon() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wygrałeś");
        alert.setHeaderText("Wygrały kółka !");
        alert.setContentText("Gratuluję");
        alert.show();
        loadSecond();
    }

    private void crossAlertWon() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wygrałeś");
        alert.setHeaderText("Wygrały krzyżyki !");
        alert.setContentText("Gratuluję");
        alert.show();
        loadSecond();
    }

    private void loadSecond() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/fxml/mainPane.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}
