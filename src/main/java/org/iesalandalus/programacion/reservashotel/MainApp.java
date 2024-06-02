package org.iesalandalus.programacion.reservashotel;


import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Habitaciones;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Huespedes;
import org.iesalandalus.programacion.reservashotel.modelo.negocio.Reservas;
import org.iesalandalus.programacion.reservashotel.vista.Consola;
import org.iesalandalus.programacion.reservashotel.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;
import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;


public class MainApp {
    private static final int CAPACIDAD = 10;
    private final Huespedes huespedes;
    private static Habitaciones habitaciones;
    private static Reservas reservas;

    public MainApp() {
        this.huespedes = new Huespedes(CAPACIDAD);
        this.habitaciones = new Habitaciones(CAPACIDAD);
        this.reservas = new Reservas(CAPACIDAD);
    }

    public static void main(String[] args) {
        MainApp mainApp = new MainApp();
        mainApp.mostrarMenu();
    }

    private void mostrarMenu() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
        System.out.println("¡Gracias por utilizar la aplicacion! Hasta luego.");
    }

    private void ejecutarOpcion(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_HUESPED:
                insertarHuesped();
                break;
            case BUSCAR_HUESPED:
                buscarHuesped();
                break;
            case BORRAR_HUESPED:
                borrarHuesped();
                break;
            case MOSTRAR_HUESPEDES:
                mostrarHuespedes();
                break;
            case INSERTAR_HABITACION:
                insertarHabitacion();
                break;
            case BUSCAR_HABITACION:
                buscarHabitacion();
                break;
            case BORRAR_HABITACION:
                borrarHabitacion();
                break;
            case MOSTRAR_HABITACIONES:
                mostrarHabitaciones();
                break;
            case INSERTAR_RESERVA:
                insertarReserva();
                break;
            case ANULAR_RESERVA:
                anularReserva();
                break;
            case MOSTRAR_RESERVAS:
                mostrarReservas();
                break;
            case SALIR:
                break;
            default:
                System.out.println("Opcion no valida.");
        }
    }

    private void insertarHuesped() {
        Huesped nuevoHuesped = Consola.leerHuesped();
        try {
        if (nuevoHuesped != null) {
            huespedes.insertar(nuevoHuesped);
            System.out.println("Huesped insertado con exito.");
        } else {
            System.out.println("Error al insertar el huesped.");
        }
    } catch (IllegalArgumentException|NullPointerException|OperationNotSupportedException e) {
            System.out.println("Error: " + e);
        }
    }

    private void buscarHuesped() {
        Huesped huespedBuscado = Consola.getHuespedPorDni();
        Huesped huespedEncontrado = huespedes.buscar(huespedBuscado);
        if (huespedEncontrado != null) {
            System.out.println("Huesped encontrado: " + huespedEncontrado);
        } else {
            System.out.println("Huesped no encontrado.");
        }
    }

    private void borrarHuesped() {
        Huesped huespedABorrar = Consola.getHuespedPorDni();
        try {
        huespedes.borrar(huespedABorrar);
        System.out.println("Huesped borrado con exito.");
    }catch (IllegalArgumentException | NullPointerException | OperationNotSupportedException e) {
            System.out.println("Error: " + e);
        }

    }

    private void mostrarHuespedes() {
        Huesped[] lista = huespedes.getColeccionHuespedes();
        if (lista.length == 0) {
            System.out.println("No hay huespedes almacenados.");
        } else {
            System.out.println("---- Listado de Huespedes ----");
            for (Huesped huesped : lista) {
                if (huesped == null) {
                    System.out.println("No hay mas huespedes.");
                    break;
                }
                System.out.println(huesped);
            }
        }
    }

    private void insertarHabitacion() {
        Habitacion nuevaHabitacion = Consola.leerHabitacion();
        try {
            if (nuevaHabitacion != null) {
                habitaciones.insertar(nuevaHabitacion);
                System.out.println("Habitacion insertada con exito.");
            } else {
                System.out.println("Error al insertar la habitacion.");
            }
        } catch (IllegalArgumentException | OperationNotSupportedException | NullPointerException e) {
            System.out.println("Error:" + e);
        }
    }

        private void buscarHabitacion() {
        Habitacion habitacionBuscada = Consola.leerHabitacionPorIdentificador();
        Habitacion habitacionEncontrada = habitaciones.buscar(habitacionBuscada);
        if (habitacionEncontrada != null) {
            System.out.println("Habitacion encontrada: " + habitacionEncontrada);
        } else {
            System.out.println("Habitacion no encontrada.");
        }
    }

    private void borrarHabitacion() {
        Habitacion habitacionABorrar = Consola.leerHabitacionPorIdentificador();
        try {
            habitaciones.borrar(habitacionABorrar);
            System.out.println("Habitacion borrada con exito.");
        } catch (OperationNotSupportedException e) {
            System.out.println("Error: " + e);
        }
    }

    private void mostrarHabitaciones() {
        Habitacion[] lista = habitaciones.get();
        if (!(lista.length > 0)) {
            System.out.println("---- Listado de Habitaciones ----");
            for (Habitacion habitacion : lista) {
                System.out.println(habitacion);
            }
        } else {
            System.out.println("No hay habitaciones almacenadas.");
        }
    }

    private static void insertarReserva(){
        Reserva nuevaReserva = Consola.leerReserva();
        try {
            reservas.insertar(nuevaReserva);
            System.out.println("Reserva insertada correctamente.");
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void listarReservas(Huesped huesped){
        Reserva[] reservasHuesped = reservas.getReservas(huesped);
        if (reservasHuesped.length > 0) {
            for (Reserva reserva : reservasHuesped) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("El huésped no tiene reservas.");
        }

    }

    private static void listarReservas(TipoHabitacion tipoHabitacion){
        Reserva[] reservasTipoHabitacion = reservas.getReservas(tipoHabitacion);
        if (reservasTipoHabitacion.length > 0) {
            for (Reserva reserva : reservasTipoHabitacion) {
                System.out.println(reserva);
            }
        } else {
            System.out.println("No hay reservas para el tipo de habitación " + tipoHabitacion);
        }

    }

    private static Reserva [] getReservasAnulables( Reserva [] reservasAnular) {
        Reserva [] arrayNuevo= new Reserva[reservasAnular.length];
        int l=0;
        for(int i=0; i<reservasAnular.length;i++){
            if(reservasAnular[i].getFechaInicioReserva().isAfter(LocalDate.now())){
                arrayNuevo[l]= new Reserva(reservasAnular[i]);
                l++;
            }
        }

        return arrayNuevo;
    }

    private static void anularReserva(){
        Huesped huesped = Consola.getHuespedPorDni();
        Reserva[] reservasAnulables = reservas.getReservas(huesped);
        reservasAnulables = getReservasAnulables(reservasAnulables);
        if (reservasAnulables.length == 0) {
            System.out.println("No hay reservas para anular.");
        } else if (reservasAnulables.length == 1) {
            System.out.println("¿Confirma la anulación de la reserva?" + reservasAnulables[0]);
            if (Entrada.cadena().equalsIgnoreCase("si")){
                try {
                    reservas.borrar(reservasAnulables[0]);
                    System.out.println("Reserva anulada correctamente.");
                } catch (IllegalArgumentException | NullPointerException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Anulación cancelada.");
            }
        } else {
            int contador=0;
            for(Reserva elemento : reservasAnulables){
                System.out.println(contador+ " : " + elemento);
                contador++;
            }
            int indiceReserva;
            do {
                System.out.println("¿Qué reserva desea anular?");
                indiceReserva = Entrada.entero();
            }while(indiceReserva<0 || indiceReserva > reservasAnulables.length);
            try {
                reservas.borrar(reservasAnulables[indiceReserva]);
                System.out.println("Reserva anulada correctamente.");
            } catch (IllegalArgumentException | NullPointerException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void mostrarReservas() {
        Reserva[] lista = reservas.get();
        if (lista.length > 0) {
            System.out.println(reservas);
        } else {
            System.out.println("No hay reservas registradas.");
        }
    }

    private static int getNumElementosNoNulos(Reserva [] reservas){
        int contador=0;
        for(Reserva elemento : reservas){
            if(elemento !=null){
                contador++;
            }
        }
        return contador;
    }

    private static Habitacion consultarDisponibilidad(TipoHabitacion tipoHabitacion, LocalDate fechaInicioReserva, LocalDate fechaFinReserva)
    {
        boolean tipoHabitacionEncontrada=false;
        Habitacion habitacionDisponible=null;
        int numElementos=0;
        Habitacion[] habitacionesTipoSolicitado= habitaciones.get(tipoHabitacion);
        if (habitacionesTipoSolicitado==null)
            return habitacionDisponible;
        for (int i=0; i<habitacionesTipoSolicitado.length && !tipoHabitacionEncontrada; i++)
        {
            if (habitacionesTipoSolicitado[i]!=null)
            {
                Reserva[] reservasFuturas = reservas.getReservasFuturas(habitacionesTipoSolicitado[i]);
                numElementos=getNumElementosNoNulos(reservasFuturas);
                if (numElementos == 0)
                {
                    habitacionDisponible=new Habitacion(habitacionesTipoSolicitado[i]);
                    tipoHabitacionEncontrada=true;
                }
                else {
                    Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaFinReserva).reversed());
                    if (fechaInicioReserva.isAfter(reservasFuturas[0].getFechaFinReserva())) {
                        habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                        tipoHabitacionEncontrada = true;
                    }
                    if (!tipoHabitacionEncontrada)
                    {
                        Arrays.sort(reservasFuturas, 0, numElementos, Comparator.comparing(Reserva::getFechaInicioReserva));
                        if (fechaFinReserva.isBefore(reservasFuturas[0].getFechaInicioReserva())) {
                            habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                            tipoHabitacionEncontrada = true;
                        }
                    }
                    if (!tipoHabitacionEncontrada)
                    {
                        for(int j=1;j<reservasFuturas.length && !tipoHabitacionEncontrada;j++)
                        {
                            if (reservasFuturas[j]!=null && reservasFuturas[j-1]!=null)
                            {
                                if(fechaInicioReserva.isAfter(reservasFuturas[j-1].getFechaFinReserva()) &&
                                        fechaFinReserva.isBefore(reservasFuturas[j].getFechaInicioReserva())) {
                                    habitacionDisponible = new Habitacion(habitacionesTipoSolicitado[i]);
                                    tipoHabitacionEncontrada = true;
                                }
                            }
                        }
                    }


                }
            }
        }
        return habitacionDisponible;
    }
}
