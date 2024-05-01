package co.edu.uniquindio.hotel.controladores;

import co.edu.uniquindio.hotel.modelo.Cliente;
import co.edu.uniquindio.hotel.modelo.Habitacion;
import co.edu.uniquindio.hotel.modelo.Hotel;
import co.edu.uniquindio.hotel.modelo.Reserva;
import co.edu.uniquindio.hotel.servicio.HotelServicio;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;
import java.time.LocalDate;

/**
 * Clase que representa el controlador principal de la aplicación
 * @author caflorezvi
 * @date Abril 2024
 */
public class ControladorPrincipal implements HotelServicio {

    @Getter
    private final Hotel hotel;
    public static ControladorPrincipal INSTANCIA;

    private ControladorPrincipal(){
        hotel = new Hotel();
    }

    public static ControladorPrincipal getInstancia(){
        if(INSTANCIA == null){
            INSTANCIA = new ControladorPrincipal();
        }
        return INSTANCIA;
    }

    @Override
    public Cliente buscarCliente(String cedula) {
        return hotel.buscarCliente(cedula);
    }

    @Override
    public Habitacion buscarHabitacion(int numeroHabitacion) {
        return hotel.buscarHabitacion(numeroHabitacion);
    }

    @Override
    public Reserva crearReserva(String cedula, String nombre, String correo, LocalDate fechaInicio, LocalDate fechaFin, int numeroHabitacion, int cantidadPersonas) throws Exception {
        return hotel.crearReserva(cedula, nombre, correo, fechaInicio, fechaFin, numeroHabitacion, cantidadPersonas);
    }

    @Override
    public Reserva obtenerReserva(int codigoHabitacion) {
        return hotel.obtenerReserva(codigoHabitacion);
    }

    @Override
    public void liberarHabitacion(int numero) {
        hotel.liberarHabitacion(numero);
    }

    public void mostrarAlerta(String mensaje, Alert.AlertType tipo){
        Alert alert = new Alert(tipo);
        alert.setTitle("Alerta - Hotel");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public FXMLLoader navegar(String nombreVista, String titulo){
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(nombreVista));
            Parent root = loader.load();

            // Crear la escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle(titulo);

            // Mostrar la nueva ventana
            stage.show();

            return loader;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public void cerrarVentana(Node node){
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

}
