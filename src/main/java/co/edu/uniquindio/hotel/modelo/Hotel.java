package co.edu.uniquindio.hotel.modelo;

import co.edu.uniquindio.hotel.modelo.constantes.TamanoMatriz;
import co.edu.uniquindio.hotel.modelo.enums.EstadoReserva;
import co.edu.uniquindio.hotel.modelo.enums.TipoHabitacion;
import co.edu.uniquindio.hotel.servicio.HotelServicio;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Clase que representa un hotel con sus habitaciones, reservas y clientes asociados
 * @author caflorezvi
 * @date Abril 2024
 */
@Getter
@Setter
public class Hotel implements HotelServicio {

    private final Habitacion[][] habitaciones;
    private final ArrayList<Reserva> reservas;
    private final ArrayList<Cliente> clientes;

    public Hotel(){
        this.habitaciones = new Habitacion[TamanoMatriz.FILAS][TamanoMatriz.COLUMNAS];
        this.reservas = new ArrayList<>();
        this.clientes = new ArrayList<>();
        inicializarHabitaciones();
    }

    /**
     * Método que inicializa las habitaciones del hotel con valores por defecto y las almacena en la matriz de habitaciones
     */
    private void inicializarHabitaciones(){

        for(int i = 0; i < habitaciones[0].length; i++){
            habitaciones[0][i] = new Habitacion(100+(i+1), 2, TipoHabitacion.SENCILLA, true);
        }

        for(int i = 1; i <= 2; i++){
            for(int j = 0; j < habitaciones[0].length; j++) {
                habitaciones[i][j] = new Habitacion((i+1)*100+(j+1), 4, TipoHabitacion.DOBLE, true);
            }
        }

        for(int i = 0; i < habitaciones[0].length; i++){
            habitaciones[3][i] = new Habitacion(400+(i+1), 6, TipoHabitacion.LUJO, true);
        }

    }

    /**
     * Método que crea un cliente y lo almacena en la lista de clientes
     * @param cedula Cédula del cliente
     * @param nombre Nombre del cliente
     * @param correo Correo del cliente
     * @return Cliente creado
     * @throws Exception Si la cédula, nombre o correo son nulos o vacíos
     */
    private Cliente crearCliente(String cedula, String nombre, String correo) throws Exception{

        if(cedula == null || cedula.isBlank()){
            throw new Exception("La cédula es obligatoria");
        }

        if(nombre == null || nombre.isBlank()){
            throw new Exception("El nombre es obligatorio");
        }

        if(correo == null || correo.isBlank()){
            throw new Exception("El correo es obligatorio");
        }

        if(buscarCliente(cedula) != null){
            throw new Exception("El cliente ya existe");
        }

        Cliente cliente = Cliente.builder()
                .cedula(cedula)
                .nombre(nombre)
                .correo(correo)
                .build();

        clientes.add(cliente);
        return cliente;

    }

    /**
     * Método que busca un cliente por su cédula en la lista de clientes
     * @param cedula Cédula del cliente
     * @return Cliente encontrado
     */
    @Override
    public Cliente buscarCliente(String cedula){

        for(Cliente cliente : clientes){
            if(cliente.getCedula().equals(cedula)){
                return cliente;
            }
        }

        return null;
    }

    /**
     * Método que busca una habitación por su número en la matriz de habitaciones
     * @param numero Número de la habitación
     * @return Habitación encontrada
     */
    @Override
    public Habitacion buscarHabitacion(int numero){
        for(int i = 0; i < habitaciones.length; i++){
            for(int j = 0; j < habitaciones[i].length; j++){
                if(habitaciones[i][j].getNumero() == numero){
                    return habitaciones[i][j];
                }
            }
        }
        return null;
    }

    /**
     * Método que crea una reserva y la almacena en la lista de reservas
     * @param cedula Cédula del cliente
     * @param nombre Nombre del cliente
     * @param correo Correo del cliente
     * @param fechaInicio Fecha de inicio de la reserva
     * @param fechaFin Fecha de fin de la reserva
     * @param numeroHabitacion Número de la habitación
     * @param cantidadPersonas Cantidad de personas
     * @return Reserva creada
     * @throws Exception Si la habitación no existe, la cantidad de personas supera la capacidad de la habitación, la fecha de inicio es nula, la fecha de fin es nula, la cantidad de personas es menor o igual a 0, la fecha de inicio es mayor a la fecha de fin
     */
    @Override
    public Reserva crearReserva(String cedula, String nombre, String correo, LocalDate fechaInicio, LocalDate fechaFin, int numeroHabitacion, int cantidadPersonas) throws Exception{

        // Buscar la habitación por el número
        Habitacion habitacion = buscarHabitacion(numeroHabitacion);

        // Validar si la habitación existe
        if(habitacion == null){
            throw new Exception("La habitación no existe");
        }

        // Validar si la habitación tiene la capacidad para la cantidad de personas
        if( habitacion.getCapacidad() < cantidadPersonas){
            throw new Exception("La cantidad de personas supera la capacidad de la habitación");
        }

        // Buscar el cliente por la cédula
        Cliente cliente = buscarCliente(cedula);

        // Si el cliente no existe, se crea uno nuevo
        if(cliente == null){
            cliente = crearCliente(cedula, nombre, correo);
        }

        if(fechaInicio == null){
            throw new Exception("La fecha de inicio es obligatoria");
        }

        if(fechaFin == null){
            throw new Exception("La fecha de fin es obligatoria");
        }

        if(cantidadPersonas <= 0){
            throw new Exception("La cantidad de personas debe ser mayor a 0");
        }

        if(fechaInicio.isBefore(LocalDate.now())){
            throw new Exception("La fecha de inicio debe ser mayor o igual a la fecha actual");
        }

        if(fechaInicio.isAfter(fechaFin)){
            throw new Exception("La fecha de inicio debe ser menor a la fecha de fin");
        }

        // Calcular el valor total de la reserva
        int dias = fechaInicio.until(fechaFin).getDays();
        float valorTotal = dias * habitacion.getTipo().getPrecio();

        // Crear la reserva
        Reserva reserva = Reserva.builder()
                .codigo( UUID.randomUUID().toString() )
                .cliente(cliente)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .habitacion(habitacion)
                .cantidadPersonas(cantidadPersonas)
                .valorTotal(valorTotal)
                .estado(EstadoReserva.ACTIVA)
                .build();

        // Cambiar el estado de la habitación
        habitacion.setDisponible(false);

        // Agregar la reserva a la lista de reservas
        reservas.add(reserva);

        return reserva;
    }

    /**
     * Método que obtiene una reserva por el número de la habitación
     * @param codigoHabitacion Número de la habitación
     * @return Reserva encontrada
     */
    @Override
    public Reserva obtenerReserva(int codigoHabitacion){
        for(Reserva reserva : reservas){
            if(reserva.getHabitacion().getNumero() == codigoHabitacion && reserva.getEstado() == EstadoReserva.ACTIVA ){
                return reserva;
            }
        }
        return null;
    }

    /**
     * Método que libera una habitación por su número
     * @param numero Número de la habitación
     */
    @Override
    public void liberarHabitacion(int numero){
        Habitacion habitacion = buscarHabitacion(numero);
        Reserva reserva = obtenerReserva(numero);

        if(reserva != null){
            reserva.setEstado(EstadoReserva.FINALIZADA);
        }

        if(habitacion != null){
            habitacion.setDisponible(true);
        }
    }


}
