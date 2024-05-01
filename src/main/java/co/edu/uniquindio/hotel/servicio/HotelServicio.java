package co.edu.uniquindio.hotel.servicio;

import co.edu.uniquindio.hotel.modelo.Cliente;
import co.edu.uniquindio.hotel.modelo.Habitacion;
import co.edu.uniquindio.hotel.modelo.Reserva;
import java.time.LocalDate;

/**
 * Interfaz que define los servicios que se pueden realizar en el hotel
 * @author caflorezvi
 * @date Abril 2024
 */
public interface HotelServicio {

    Cliente buscarCliente(String cedula);

    Habitacion buscarHabitacion(int numero);

    Reserva crearReserva(String cedula, String nombre, String correo, LocalDate fechaInicio, LocalDate fechaFin, int numeroHabitacion, int cantidadPersonas) throws Exception;

    Reserva obtenerReserva(int codigoHabitacion);

    void liberarHabitacion(int numero);

}
