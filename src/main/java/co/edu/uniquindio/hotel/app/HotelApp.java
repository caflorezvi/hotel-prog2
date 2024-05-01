package co.edu.uniquindio.hotel.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación que inicia la interfaz gráfica de usuario.
 * @author caflorezvi
 * @date Abril 2024
 */
public class HotelApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(HotelApp.class.getResource("/inicio.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Hotel Descanso Feliz");
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(HotelApp.class, args);
    }

}
