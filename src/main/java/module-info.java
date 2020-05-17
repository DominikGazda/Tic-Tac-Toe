module tictocgame {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports gazda.main to javafx.graphics;
    opens gazda.controller to javafx.fxml;
}