package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.util.Objects;

public class Habitacion {
    public final double MIN_PRECIO_HABITACION = 40.0;
    public final double MAX_PRECIO_HABITACION = 150.0;
    public final int MIN_NUMERO_PUERTA = 1;
    public final int MAX_NUMERO_PUERTA = 15;
    public final int MIN_NUMERO_PLANTA = 1;
    public final int MAX_NUMERO_PLANTA = 3;
    private String identificador;
    private int planta;
    private int puerta;
    private double precio;
    private TipoHabitacion tipoHabitacion;

    public Habitacion(int planta, int puerta, double precio) {
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setIdentificador();
    }

    public Habitacion(int planta, int puerta, double precio, TipoHabitacion tipoHabitacion) {
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setTipoHabitacion(tipoHabitacion);
        setIdentificador();
    }

    public Habitacion(Habitacion habitacion) {
        if (habitacion == null){
            throw new NullPointerException("Error: No es posible copiar una habitacion nula.");
        }
        setPlanta(planta);
        setPuerta(puerta);
        setPrecio(precio);
        setTipoHabitacion(tipoHabitacion);
        setIdentificador();
    }

    public String getIdentificador() {
            return identificador;
        }
    public void setIdentificador(){
        if (planta <= 0 || puerta <= 0) {
            throw new IllegalArgumentException("ERROR: La planta y la puerta deben ser mayores que cero.");
        }
        this.identificador = String.format("%d%d", this.planta, this.puerta);
    }

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        if (planta < MIN_NUMERO_PLANTA || planta > MAX_NUMERO_PLANTA) {
            throw new IllegalArgumentException("Número de planta no válido");
        } else {
            this.planta = planta;
        }
    }
        public int getPuerta() {
            return puerta;
        }

        public void setPuerta(int puerta) {
            if (puerta < MIN_NUMERO_PUERTA || puerta > MAX_NUMERO_PUERTA) {
                throw new IllegalArgumentException("Número de puerta no válido");
            }else {
                this.puerta = puerta;
            }
        }

        public double getPrecio() {
            return precio;
        }

        public void setPrecio(double precio) {
            if (precio < MIN_PRECIO_HABITACION || precio > MAX_PRECIO_HABITACION) {
                throw new IllegalArgumentException("Precio no válido");
            }else {
                this.precio = precio;
            }
        }

        public TipoHabitacion getTipoHabitacion() {
            return tipoHabitacion;
        }

        public void setTipoHabitacion(TipoHabitacion tipoHabitacion) {
            this.tipoHabitacion = Objects.requireNonNull(tipoHabitacion, "TipoHabitacion no puede ser nulo");
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habitacion that = (Habitacion) o;
        return planta == that.planta && puerta == that.puerta && Double.compare(precio, that.precio) == 0 && Objects.equals(identificador, that.identificador) && tipoHabitacion == that.tipoHabitacion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identificador, planta, puerta, precio, tipoHabitacion);
    }

    @Override
    public String toString() {

        return String.format("identificador=%s (%d-%d), precio habitación=%s, tipo habitación=%s",
                identificador, planta, puerta, precio, tipoHabitacion);
    }
}