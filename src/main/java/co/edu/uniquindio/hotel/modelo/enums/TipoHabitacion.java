package co.edu.uniquindio.hotel.modelo.enums;

import lombok.Getter;

@Getter
public enum TipoHabitacion {

    SENCILLA("Sencilla", 80000), DOBLE("Doble", 160000), LUJO("Lujo", 250000);

    private final String nombre;
    private final float precio;

    TipoHabitacion(String nombre, float precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

}
