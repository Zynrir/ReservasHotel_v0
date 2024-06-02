package org.iesalandalus.programacion.reservashotel.vista;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.*;
import org.iesalandalus.programacion.utilidades.Entrada;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Consola {
    private Consola() {
    }

    public static void mostrarMenu() {
        System.out.println("---- Menu ----");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion.ordinal() + " - " + opcion.name());
        }
    }

    public static Opcion elegirOpcion() {
        System.out.print("Elija una opcion: ");
        int opcionElegida = Entrada.entero();
        return Opcion.values()[opcionElegida];
    }

    public static Huesped leerHuesped() {
        System.out.println("---- Introducir datos de Huesped ----");
        System.out.print("Introduzca el Nombre del Huesped: ");
        String nombre = Entrada.cadena();
        System.out.print("Introduzca el DNI del Huesped: ");
        String dni = Entrada.cadena();
        System.out.print("Introduzca el Correo del Huesped: ");
        String correo = Entrada.cadena();
        System.out.print("Introduzca el Telefono del Huesped: ");
        String telefono = Entrada.cadena();
        System.out.print("Introduzca el Fecha de nacimiento del Huesped: ");
        LocalDate fechaNacimiento = Consola.leerFecha();
        try {
            return new Huesped(nombre, dni, correo, telefono, fechaNacimiento);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Habitacion leerHabitacion() {
        System.out.println("---- Introduce los datos de la habitacion ----");
        System.out.print("Introduzca la Planta de la habitacion: ");
        int planta = Entrada.entero();
        System.out.print("Introduzca el numero de la Puerta de la habitacion: ");
        int puerta = Entrada.entero();
        System.out.print("Introduzca el precio de la habitacion: ");
        double precio = Entrada.realDoble();
        TipoHabitacion tipoHabitacion = leerTipoHabitacion();
        try {
            return new Habitacion(planta, puerta, precio, tipoHabitacion);
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static Habitacion leerHabitacionPorIdentificador() {
        System.out.println("---- Introduce los datos de la habitacion ----");
        System.out.print("Introduzca la Planta de la habitacion: ");
        int planta = Entrada.entero();
        System.out.print("Introduzca el numero de la Puerta de la habitacion: ");
        int puerta = Entrada.entero();
        try {
            return new Habitacion(planta, puerta, leerHabitacion().getPrecio());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static TipoHabitacion leerTipoHabitacion() {
        System.out.println("Seleccione el tipo de habitacion:");
        System.out.println("1. Individual");
        System.out.println("2. Doble");
        System.out.println("3. Suite");
        System.out.println("4. Triple");
        System.out.print("Ingrese el numero correspondiente al tipo de habitacion: ");
        int opcion = Entrada.entero();
        return switch (opcion) {
            case 1 -> TipoHabitacion.SIMPLE;
            case 2 -> TipoHabitacion.DOBLE;
            case 3 -> TipoHabitacion.SUITE;
            case 4 -> TipoHabitacion.TRIPLE;
            default -> {
                System.out.println("Opcion invalida. Seleccionando tipo de habitación por defecto que es Simple.");
                yield TipoHabitacion.SIMPLE;
            }
        };
    }
    public static Regimen leerRegimen() {
        System.out.println("---- Introduce los datos del regimen ----");
        System.out.println("Seleccione el tipo de regimen:");
        System.out.println("1. Solo alojamiento");
        System.out.println("2. Alojamiento con desayuno");
        System.out.println("3. Media pension");
        System.out.println("4. Pensión completa");
        System.out.print("Introduzca el numero correspondiente al tipo de regimen: ");
        int opcion = Entrada.entero();
        return switch (opcion) {
            case 1 -> Regimen.SOLO_ALOJAMIENTO;
            case 2 -> Regimen.ALOJAMIENTO_CON_DESAYUNO;
            case 3 -> Regimen.MEDIA_PENSION;
            case 4 -> Regimen.PENSION_COMPLETA;
            default -> {
                System.out.println("Opción invalida. Seleccionando tipo de regimen por defecto.");
                yield Regimen.SOLO_ALOJAMIENTO;
            }
        };
    }

    public static Reserva leerReserva() {
        System.out.println("---- Introduce los datos de la Reserva ----");
        System.out.print("Introduzca el dni del huesped de la Reserva: ");
        Huesped huesped = getHuespedPorDni();
        System.out.print("Introduzca la habitacion de la Reserva: ");
        Habitacion habitacion = leerHabitacionPorIdentificador();
        System.out.print("Introduzca el regimen de la Reserva: ");
        Regimen regimen = leerRegimen();
        System.out.print("Introduzca el huesped de la Reserva: ");
        LocalDate fechalnicioReserva = leerFecha();
        System.out.print("Introduzca la habitacion de la Reserva: ");
        LocalDate fechaFinReserva = leerFecha();
        System.out.print("Introduzca el numero de personas de la Reserva: ");
        int numeroPersonas = Entrada.entero();
        try {
            return new Reserva(huesped ,habitacion ,regimen, fechalnicioReserva, fechaFinReserva, numeroPersonas);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Huesped getHuespedPorDni() {
        System.out.print("Introduzca el DNI del Huesped: ");
        String dni = Entrada.cadena();
        try {
            return new Huesped("nombre", dni, "correo@gmail.com", "623456789", LocalDate.of(2000,4,4));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
    public static LocalDate leerFecha() {
        try {
            String fecha = null;
            boolean fechaValida = false;
            while (!fechaValida) {
                System.out.println("Formato dd/MM/yyyy");
                fecha = Entrada.cadena();
                if (fecha.matches("[0-3][0-9]/[0-1][0-9]/[1-2][0-9]{3}"))
                    fechaValida = true;
            }
            DateTimeFormatter formato= DateTimeFormatter.ofPattern(Huesped.FORMATO_FECHA);
            LocalDate fechaFormato = LocalDate.parse(fecha, formato);
            return fechaFormato;
        }catch (DateTimeException e){
            System.out.println("Error: La fecha es invalida.");
        }
        return null;
    }
}

