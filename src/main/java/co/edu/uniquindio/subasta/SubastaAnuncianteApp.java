package co.edu.uniquindio.subasta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SubastaAnuncianteApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("SubastaAnuncianteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Subasta");
        stage.setScene(scene);
        stage.show();
    }
}
