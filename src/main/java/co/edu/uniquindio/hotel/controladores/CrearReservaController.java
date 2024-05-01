package co.edu.uniquindio.hotel.controladores;

import co.edu.uniquindio.hotel.controladores.observador.Observador;
import co.edu.uniquindio.hotel.modelo.Cliente;
import co.edu.uniquindio.hotel.modelo.Reserva;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Clase que representa el controlador de la vista CrearReserva
 * @author caflorezvi
 * @date Abril 2024
 */
public class CrearReservaController {

    @FXML
    private TextField txtDocumento;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private DatePicker txtFechaIngreso;

    @FXML
    private DatePicker txtFechaSalida;

    @FXML
    private TextField txtNumeroPersonas;

    @FXML
    private Button btnLiberar;

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnBuscar;

    private Observador observador;
    private int numeroHabitacion;
    private final ControladorPrincipal controladorPrincipal;

    public CrearReservaController(){
        controladorPrincipal = ControladorPrincipal.getInstancia();
    }

    /**
     * Método que se encarga de inicializar los datos de la vista
     * @param numeroHabitacion Número de la habitación
     * @param observador Observador
     */
    public void inicializarDatos(int numeroHabitacion, Observador observador){
        this.numeroHabitacion = numeroHabitacion;
        this.observador = observador;
        cargarDatos();
    }

    /**
     * Método que se encarga de crear una reserva con los datos ingresados
     */
    public void crearReserva(){
        try {
            Reserva reserva = controladorPrincipal.crearReserva(txtDocumento.getText(), txtNombre.getText(), txtCorreo.getText(), txtFechaIngreso.getValue(), txtFechaSalida.getValue(), numeroHabitacion, Integer.parseInt(txtNumeroPersonas.getText()));
            controladorPrincipal.mostrarAlerta("La reserva se ha creado correctamente, el valor a pagar es: $"+reserva.getValorTotal(), Alert.AlertType.INFORMATION);
            observador.actualizar(numeroHabitacion, true);
            controladorPrincipal.cerrarVentana(txtDocumento);
        } catch (Exception e) {
            controladorPrincipal.mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    /**
     * Método que se encarga de buscar un cliente por su documento
     * @param actionEvent Evento de acción
     */
    public void buscarCliente(ActionEvent actionEvent) {
        Cliente cliente = controladorPrincipal.buscarCliente(txtDocumento.getText());
        if(cliente != null) {
            txtNombre.setText(cliente.getNombre());
            txtCorreo.setText(cliente.getCorreo());
        }
    }

    /**
     * Método que se encarga de cargar los datos de la reserva en la vista si la habitación ya está reservada
     */
    public void cargarDatos(){
        Reserva reserva = controladorPrincipal.obtenerReserva(numeroHabitacion);

        if(reserva != null) {
            btnCrear.setDisable(true);
            btnBuscar.setDisable(true);
            btnLiberar.setDisable(false);
            txtDocumento.setText(reserva.getCliente().getCedula());
            txtNombre.setText(reserva.getCliente().getNombre());
            txtCorreo.setText(reserva.getCliente().getCorreo());
            txtFechaIngreso.setValue(reserva.getFechaInicio());
            txtFechaSalida.setValue(reserva.getFechaFin());
            txtNumeroPersonas.setText(String.valueOf(reserva.getCantidadPersonas()));
        }

    }

    /**
     * Método que se encarga de liberar la habitación seleccionada
     * @param actionEvent Evento de acción
     */
    public void liberarHabitacion(ActionEvent actionEvent) {
        controladorPrincipal.liberarHabitacion(numeroHabitacion);

        controladorPrincipal.mostrarAlerta("La habitación se ha liberado", Alert.AlertType.INFORMATION);

        observador.actualizar(numeroHabitacion, false);
        controladorPrincipal.cerrarVentana(txtDocumento);
    }
}
