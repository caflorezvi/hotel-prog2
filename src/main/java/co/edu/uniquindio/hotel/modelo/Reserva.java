package co.edu.uniquindio.hotel.modelo;

import co.edu.uniquindio.hotel.modelo.enums.EstadoReserva;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reserva {

    private String codigo;
    private Cliente cliente;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Habitacion habitacion;
    private EstadoReserva estado;
    private int cantidadPersonas;
    private float valorTotal;

}
