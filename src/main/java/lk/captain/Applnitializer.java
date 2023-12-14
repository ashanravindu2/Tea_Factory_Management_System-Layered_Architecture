package lk.captain;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Applnitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login_form.fxml"))));
        primaryStage.centerOnScreen();
        primaryStage.setTitle("User Log In Page");
        primaryStage.show();
    }
}