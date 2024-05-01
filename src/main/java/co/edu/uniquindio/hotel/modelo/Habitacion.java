package co.edu.uniquindio.hotel.modelo;

import co.edu.uniquindio.hotel.modelo.enums.TipoHabitacion;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Habitacion {

    private int numero;
    private int capacidad;
    private TipoHabitacion tipo;
    private boolean disponible;

}
