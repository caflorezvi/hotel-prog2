package co.edu.uniquindio.hotel.modelo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Cliente {
    private String cedula, nombre, correo;
}
