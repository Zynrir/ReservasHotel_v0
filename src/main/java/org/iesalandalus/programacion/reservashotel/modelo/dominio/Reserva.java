package org.iesalandalus.programacion.reservashotel.modelo.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Reserva {
    private static final int MAX_NUMERO_MESES_RESERVA = 5;
    private static final int MAX_HORAS_POSTERIOR_CHECKOUT = 5;
    private static final String FORMATO_FECHA_RESERVA = "dd/MM/yyyy";
    public static final String FORMATO_FECHA_HORA_RESERVA="dd/MM/yyyy hh:mm:ss";

    private Huesped huesped;
    private Habitacion habitacion;
    private Regimen regimen;
    private LocalDate fechaInicioReserva;
    private LocalDate fechaFinReserva;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private double precio;
    private int numeroPersonas;

    public Reserva(Huesped huesped, Habitacion habitacion, Regimen regimen, LocalDate fechaInicioReserva, LocalDate fechaFinReserva, int numeroPersonas) {
        setHuesped(huesped);
        setHabitacion(habitacion);
        setRegimen(regimen);
        setFechaInicioReserva(fechaInicioReserva);
        setFechaFinReserva(fechaFinReserva);
        setNumeroPersonas(numeroPersonas);
    }

    public Reserva(Reserva reserva) {
        if(reserva==null){
            throw new NullPointerException("ERROR: No es posible copiar una reserva nula.");
        }
        setHuesped(reserva.getHuesped());
        setHabitacion(reserva.getHabitacion());
        setRegimen(reserva.getRegimen());
        setFechaInicioReserva(reserva.getFechaInicioReserva());
        setFechaFinReserva(reserva.getFechaFinReserva());
        if (reserva.getCheckIn() != null) {
            setCheckIn(reserva.getCheckIn());
        }
        if(reserva.getCheckOut() != null){
            setCheckOut(reserva.getCheckOut());
        }
        setNumeroPersonas(reserva.getNumeroPersonas());
        setPrecio();
    }

    public Huesped getHuesped() {
        return huesped;
    }

    public void setHuesped(Huesped huesped) {
        if(huesped == null){
            throw new NullPointerException("ERROR: El hu�sped de una reserva no puede ser nulo.");
        }
        this.huesped = huesped;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        if(habitacion == null){
            throw new NullPointerException("ERROR: La habitaci�n no puede ser nula.");
        }
        this.habitacion = habitacion;
    }

    public Regimen getRegimen() {
        return regimen;
    }

    public void setRegimen(Regimen regimen) {
        if (regimen == null){
            throw new NullPointerException("ERROR: El r�gimen no puede ser nulo.");
        }
        this.regimen = regimen;
    }

    public LocalDate getFechaInicioReserva() {
        return fechaInicioReserva;
    }

    public void setFechaInicioReserva(LocalDate fechaInicioReserva) {
        if(fechaInicioReserva==null){
            throw new NullPointerException("ERROR: La fecha de inicio de una reserva no puede ser nula.");
        }
        if(fechaInicioReserva.isBefore(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser anterior al d�a de hoy.");
        }
        if(fechaInicioReserva.isAfter(LocalDate.now().plusMonths(MAX_NUMERO_MESES_RESERVA))){
            throw new IllegalArgumentException("ERROR: La fecha de inicio de la reserva no puede ser posterior a seis meses.");
        }
        this.fechaInicioReserva = fechaInicioReserva;
    }

    public LocalDate getFechaFinReserva() {
        return fechaFinReserva;
    }

    public void setFechaFinReserva(LocalDate fechaFinReserva) {
        if(fechaFinReserva==null){
            throw new NullPointerException("ERROR: La fecha de fin de una reserva no puede ser nula.");
        }
        if(fechaFinReserva.isBefore(fechaInicioReserva) || fechaFinReserva.isEqual(fechaInicioReserva)){
            throw new IllegalArgumentException("ERROR: La fecha de fin de la reserva debe ser posterior a la de inicio.");
        }
        this.fechaFinReserva = fechaFinReserva;
    }

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        if(checkIn==null) {
            throw new NullPointerException("ERROR: El checkin de una reserva no puede ser nulo.");
        }
        if(checkIn.isBefore(fechaInicioReserva.atStartOfDay())){
            throw new IllegalArgumentException("ERROR: El checkin de una reserva no puede ser anterior a la fecha de inicio de la reserva.");
        }
        if(checkIn.isAfter((fechaFinReserva.atStartOfDay()))){
            throw new IllegalArgumentException("ERROR: El checkin no puede ser posterior al inicio de la reserva.");
        }
        this.checkIn = checkIn;
    }

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        if(checkOut==null){
            throw new NullPointerException("ERROR: El checkout de una reserva no puede ser nulo.");
        }
        if(checkOut.isBefore(getCheckIn())){
            throw new IllegalArgumentException("ERROR: El checkout de una reserva no puede ser anterior al checkin.");
        }
        if(checkOut.isAfter(getFechaFinReserva().atStartOfDay().plusHours(MAX_HORAS_POSTERIOR_CHECKOUT))){
            throw new IllegalArgumentException("ERROR: El checkout de una reserva puede ser como m�ximo 12 horas despu�s de la fecha de fin de la reserva.");
        }
        this.checkOut = checkOut;
    }

    public double getPrecio() {
        return precio;
    }

    private void setPrecio() {
        long diasReserva=fechaInicioReserva.until(fechaFinReserva, ChronoUnit.DAYS);
        precio = (habitacion.getPrecio()*diasReserva)+(regimen.getIncrementoPrecio()*numeroPersonas*diasReserva);
    }


    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas){
        if(numeroPersonas<=0){
            throw new IllegalArgumentException("ERROR: El n�mero de personas de una reserva no puede ser menor o igual a 0.");
        }
        if(numeroPersonas>habitacion.getTipoHabitacion().getNumeroMaximoPersonas()){
            throw new IllegalArgumentException("ERROR: El n�mero de personas de una reserva no puede superar al m�ximo de personas establacidas para el tipo de habitaci�n reservada.");
        }
        this.numeroPersonas=numeroPersonas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(habitacion, reserva.habitacion) && Objects.equals(fechaInicioReserva, reserva.fechaInicioReserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitacion, fechaInicioReserva);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(FORMATO_FECHA_RESERVA);
        DateTimeFormatter formatoFechaHora = DateTimeFormatter.ofPattern(FORMATO_FECHA_HORA_RESERVA);
        String checkInString = getCheckIn() != null ? getCheckIn().format(formatoFechaHora) : "No registrado";
        String checkOutString = getCheckOut() != null ? getCheckOut().format(formatoFechaHora) : "No registrado";
        return String.format("Huesped: %s %s Habitaci�n:%s - %s Fecha Inicio Reserva: %s Fecha Fin Reserva: %s Checkin: %s Checkout: %s Precio: %.2f Personas: %d",
                getHuesped().getNombre(), getHuesped().getDni(), getHabitacion().getIdentificador(),
                getHabitacion().getTipoHabitacion(), getFechaInicioReserva().format(formatoFecha),
                getFechaFinReserva().format(formatoFecha), checkInString, checkOutString, getPrecio(), getNumeroPersonas());
    }
}