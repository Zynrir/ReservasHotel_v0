package org.iesalandalus.programacion.reservashotel.vista;

public enum Opcion {
        SALIR("Salir"),
        INSERTAR_HUESPED("Insertar huesped"),
        BUSCAR_HUESPED("Buscar huesped"),
        BORRAR_HUESPED("Borrar huesped"),
        MOSTRAR_HUESPEDES("Mostrar huespedes"),
        INSERTAR_HABITACION("Insertar habitacion"),
        BUSCAR_HABITACION("Buscar habitacion"),
        BORRAR_HABITACION("Borrar habitacion"),
        MOSTRAR_HABITACIONES("Mostrar habitaciones"),
        INSERTAR_RESERVA("Insertar reserva"),
        ANULAR_RESERVA("Anular reserva"),
        MOSTRAR_RESERVAS("Mostrar reservas"),
        CONSULTAR_DISPONIBILIDAD("Consultar disponibilidad");

        private final String mensajeAMostrar;

        Opcion(String mensaje) {
            this.mensajeAMostrar = mensaje;
        }

        public String getMensaje() {
            return mensajeAMostrar;
        }

        public String toString() {
            return ordinal() + " .- " + mensajeAMostrar;
        }
    }
