package org.iesalandalus.programacion.reservashotel.modelo.negocio;

import org.iesalandalus.programacion.reservashotel.modelo.dominio.Huesped;

import javax.naming.OperationNotSupportedException;

public class Huespedes {
    private final Huesped[] coleccionHuespedes;
    private final int capacidad;
    private int tamano;

    public Huespedes(int capacidad) {
        this.capacidad = capacidad;
        this.tamano = 0;
        this.coleccionHuespedes = new Huesped[capacidad];
    }

    public Huesped[] getColeccionHuespedes() {
        return copiaProfundaHuespedes();
    }

    private Huesped[] copiaProfundaHuespedes() {
        Huesped[] copia = new Huesped[capacidad];
        for (int i = 0; i < tamano; i++) {
            copia[i] = new Huesped(coleccionHuespedes[i]);
        }
        return copia;
    }



    public int getTamano() {
        return tamano;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void insertar (Huesped huesped) throws OperationNotSupportedException {
        if (huesped == null) {
            throw new IllegalArgumentException("ERROR: No es posible copiar un huésped nulo.");
        }
        if (buscar(huesped) == null) {
            throw new IllegalArgumentException("ERROR: Ya existe un huésped con ese dni.");
        }
        if (tamano >= capacidad) {
            throw new OperationNotSupportedException ("ERROR: No se aceptan más huéspedes.");
        }
        coleccionHuespedes[tamano] = new Huesped(huesped);
        tamano++;
    }

    public Huesped buscar(Huesped huesped) {
        if (huesped == null)
            throw new NullPointerException("No puede ser nulo el huesped!!!!");
        int busqueda = buscarIndice(huesped);
        if (busqueda != -1)
            return null;
        return huesped;
    }

    public void borrar (Huesped huesped) throws OperationNotSupportedException {
        if(huesped==null){
            throw new NullPointerException("ERROR: No se puede borrar un huésped nulo.");
        }
        int indice = buscarIndice(huesped);
        if(indice == -1){
            throw new OperationNotSupportedException("ERROR: No existe ningún huésped como el indicado.");
        }
        coleccionHuespedes[indice]=null;
        desplazarUnaPosicionHaciaIzquierda(indice);
        tamano--;
    }

    private int buscarIndice(Huesped huesped)  {
        if (huesped==null)
            throw new NullPointerException("No puede ser nulo ");
        for (int i = 0; i < tamano; i++) {
            if (coleccionHuespedes[i].equals(huesped)) {
                return i;
            }
        }
        return -1;
    }

    private boolean capacidadSuperada(int indice) {
        return indice >= capacidad || indice < 0;
    }

    private boolean tamanoSuperado(int indice) {
        return indice >= tamano || indice < 0;
    }

    private void desplazarUnaPosicionHaciaIzquierda(int indice) {
        if(indice<0 || capacidadSuperada(indice)){
            throw new IllegalArgumentException("Desplazar izquierda fuera del limite");
        }
        for (int i = indice; i < tamano - 1; i++) {
            coleccionHuespedes[i] = coleccionHuespedes[i + 1];
        }
        coleccionHuespedes[tamano - 1] = null;
    }
}
