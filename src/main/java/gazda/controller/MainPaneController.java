package gazda.controller;

import gazda.model.Circle;
import gazda.model.Cross;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPaneController {
    @FXML
    private Button circleButton;
    @FXML
    private Button crossButton;


    public void initialize(){
        circleButtonAction();
        crossButtonAction();

    }


    private void circleButtonAction(){
        circleButton.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseEvent -> {
            GamePaneController.getShape(new Circle());
        });
        createNewScene(circleButton);
    }


    private void crossButtonAction(){{
        crossButton.addEventFilter(MouseEvent.MOUSE_PRESSED,mouseEvent -> {
            GamePaneController.getShape(new Cross());
        });
            createNewScene(crossButton);
        }
    }
    private void createNewScene(Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent parent = FXMLLoader.load(getClass().getResource("/fxml/gamePane.fxml"));
                    Scene gameScene = new Scene(parent);
                    Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(gameScene);
                    stage.show();

                }catch(IOException e){
                    System.out.println("Zła ścieżka");
                }
            }
        });
    }

}
