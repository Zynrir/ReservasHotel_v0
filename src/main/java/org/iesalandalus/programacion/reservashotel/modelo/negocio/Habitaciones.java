package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.Arrays;

public class Habitaciones {
    private int capacidad;
    private int tamano;
    private final Habitacion[] coleccionHabitaciones;

    public Habitaciones(int capacidad) {
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionHabitaciones = new Habitacion[capacidad];
    }

    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public Habitacion[] get() {
        return copiaProfundaHabitaciones();
    }

    private Habitacion[] copiaProfundaHabitaciones() {
        Habitacion[] copia = new Habitacion[capacidad];
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Habitacion(coleccionHabitaciones[i]);
        }
        return copia;
    }


    public Habitacion[] get(TipoHabitacion tipoHabitacion) {
        Habitacion[] copiaPorTipo = new Habitacion[capacidad];
        int indice = 0;
        for (int i = 0; i < tamano; i++){
            if (coleccionHabitaciones[i].getTipoHabitacion() == tipoHabitacion){
                copiaPorTipo[indice] = new Habitacion(coleccionHabitaciones[i]);
                indice++;
            }
        }
        return Arrays.copyOf(copiaPorTipo, indice);
    }

    public void insertar (Habitacion habitacion) throws OperationNotSupportedException {
        if (habitacion == null) {
            throw new IllegalArgumentException("ERROR: No es posible copiar una habitacion nula.");
        }
        if (buscar(habitacion) != null) {
            throw new IllegalArgumentException("ERROR: Ya existe la habitacin.");
        }
        if (tamano >= capacidad) {
            throw new OperationNotSupportedException ("ERROR: No se aceptan mas habitaciones.");
        }
        coleccionHabitaciones[tamano] = new Habitacion(habitacion);
        tamano++;
        System.out.println("Habitacion insertada : " + habitacion);
    }

    private int buscarIndice(Habitacion habitacion) {
        if (habitacion == null) {
            throw new NullPointerException("Error: habitacion es null");
        }
        for (int i = 0; i < tamano; i++) {
            if (coleccionHabitaciones[i] != null && coleccionHabitaciones[i].equals(habitacion)) {
                System.out.println("Habitacion encontrada en el indice: " + i);
                return i;
            }
        }
        System.out.println("Habitacion no encontrada");
        return -1;
    }


    private boolean tamanoSuperado(int indice) {
        return indice >= tamano || indice < 0;
    }

    public boolean capacidadSuperada(int indice) {
        return indice >= capacidad || indice < 0;
    }

    public Habitacion buscar(Habitacion habitacion) {
        int indice = buscarIndice(habitacion);
        if (indice != -1) {
            System.out.println("Habitacion encontrada: " + coleccionHabitaciones[indice]);
        } else {
            System.out.println("Habitacion no encontrada");
        }
        return (indice != -1) ? coleccionHabitaciones[indice] : null;
    }

    public void borrar(Habitacion habitacion) throws OperationNotSupportedException {
        int indice = buscarIndice(habitacion);
        if (indice == -1) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna habitacion con ese identificador.");
        }
        desplazarUnaPosicionHaciaIzquierda(indice);
        coleccionHabitaciones[tamano - 1] = null;
        tamano--;
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        for (int i = indice; i < tamano - 1; i++) {
            coleccionHabitaciones[i] = coleccionHabitaciones[i + 1];
        }
    }

}
