package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Habitacion;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.Reserva;
import org.iesalandalus.programacion.reservashotel.modelo.dominio.TipoHabitacion;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Arrays;

public class Reservas {
    private int capacidad;
    private int tamano;
    private final Reserva[] coleccionReservas;

    public Reservas(int capacidad) {
        if (capacidad <= 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionReservas = new Reserva[capacidad];
    }

    public Reserva[] get() {
        return copiaProfundaReservas();
    }

    public void insertar (Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede insertar una reserva nula.");
        }
        if (buscar(reserva) != null) {
            throw new IllegalArgumentException("ERROR: La reserva ya existe en la colección.");
        }
        if (capacidad < 0) {
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }
        if (tamano >= capacidad) {
            throw new IllegalArgumentException("ERROR: No se aceptan más reservas.");
        }
        coleccionReservas[getTamano()] = new Reserva(reserva);
        tamano++;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getTamano() {
        return tamano;
    }

    public Reserva buscar(Reserva reserva) {
        if (reserva==null){
            throw new NullPointerException("Error");
        }
        int indice = buscarIndice(reserva);
        return (indice != -1) ? coleccionReservas[indice] : null;
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        if(indice<0 || capacidadSuperada(indice)){
            throw new NullPointerException("El desplazarunaPosicion no puede ser menor que 0");
        }
        for (int i = indice; i < tamano - 1; i++) {
            coleccionReservas[i] = coleccionReservas[i + 1];
        }
        coleccionReservas[tamano - 1] = null;
    }

    private boolean capacidadSuperada(int indice) {
        return indice >= capacidad || indice < 0;
    }

    private boolean tamanoSuperado(int indice) {
        return indice >= tamano || indice < 0;
    }

    private int buscarIndice(Reserva reserva) {
        for (int i = 0; i < tamano; i++) {
            if (coleccionReservas[i].equals(reserva)) {
                return i;
            }
        }
        return -1;
    }

    public void borrar(Reserva reserva) {
        if (reserva == null) {
            throw new NullPointerException("ERROR: No se puede borrar una reserva nula.");
        }
        int indice = buscarIndice(reserva);
        if (indice == -1) {
            throw new IllegalArgumentException("ERROR: No existe ninguna reserva como la indicada.");
        }
        if (indice != -1) {
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        }
    }

    public Reserva[] getReservas(Huesped huesped) {
        if(huesped==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un huesped nulo.");
        }
        int posicion=0;
        Reserva[] miReserva= new Reserva[capacidad];
        for(Reserva elemento : coleccionReservas){
            if(elemento.getHuesped().equals(huesped)) {
                miReserva[posicion]= new Reserva(elemento);
                posicion++;
            }
        }
        return miReserva;
    }

    public Reserva[] getReservas(TipoHabitacion tipoHabitacion) {
        if(tipoHabitacion==null){
            throw new NullPointerException("ERROR: No se pueden buscar reservas de un tipo de habitación nula.");
        }
        int posicion=0;
        Reserva[] miReserva= new Reserva[capacidad];
        for(Reserva elemento : coleccionReservas){
            if(elemento.getHabitacion().getTipoHabitacion().equals(tipoHabitacion)) {
                miReserva[posicion]= new Reserva(elemento);
                posicion++;
            }
        }
        return miReserva;
    }

    public Reserva[] getReservasFuturas(Habitacion habitacion) {
        if (habitacion == null) {
            throw new IllegalArgumentException("ERROR: La habitación no puede ser nula.");
        }
        LocalDate fechaActual = LocalDate.now();
        Reserva[] reservasFuturas = new Reserva[tamano];
        int contador = 0;
        for (int i = 0; i < tamano; i++) {
            if (coleccionReservas[i].getHabitacion().equals(habitacion) && coleccionReservas[i].getFechaInicioReserva().isAfter(fechaActual)) {
                reservasFuturas[contador] = new Reserva(coleccionReservas[i]);
                contador++;
            }
        }
        return Arrays.copyOf(reservasFuturas, contador);
    }

    private Reserva[] copiaProfundaReservas() {
        Reserva[] copia = new Reserva[getCapacidad()];
        int indice = 0;
        for (Reserva reserva : coleccionReservas) {
            copia[indice] = (new Reserva(reserva));
            indice++;
        }
        return copia;
    }

}

