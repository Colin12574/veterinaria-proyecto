package com.veterinaria.gestion_veterinaria.util;

/**
 * Clase de constantes reutilizables del sistema.
 * Centraliza mensajes y códigos para evitar duplicación.
 */
public class UtilConstants {

    // ============================================
    // MENSAJES DE ÉXITO
    // ============================================
    public static final String MSG_CONSULTA_EXITOSA = "Consulta exitosa";
    public static final String MSG_ADOPTANTE_CREADO = "Adoptante registrado correctamente";
    public static final String MSG_ADOPTANTE_ACTUALIZADO = "Adoptante actualizado correctamente";
    public static final String MSG_ADOPTANTE_ELIMINADO = "Adoptante eliminado correctamente";
    public static final String MSG_MASCOTA_CREADA = "Mascota registrada correctamente";
    public static final String MSG_MASCOTA_ACTUALIZADA = "Mascota actualizada correctamente";
    public static final String MSG_MASCOTA_ELIMINADA = "Mascota eliminada correctamente";
    public static final String MSG_VACUNA_CREADA = "Vacuna registrada correctamente";
    public static final String MSG_VACUNA_ELIMINADA = "Vacuna eliminada correctamente";
    public static final String MSG_CHIP_CREADO = "Chip registrado correctamente";
    public static final String MSG_CHIP_ELIMINADO = "Chip eliminado correctamente";
    public static final String MSG_CITA_CREADA = "Cita programada correctamente";
    public static final String MSG_CITA_CANCELADA = "Cita cancelada correctamente";

    // ============================================
    // MENSAJES DE ERROR
    // ============================================
    public static final String MSG_ADOPTANTE_NO_ENCONTRADO = "Adoptante no encontrado";
    public static final String MSG_MASCOTA_NO_ENCONTRADA = "Mascota no encontrada";
    public static final String MSG_VACUNA_NO_ENCONTRADA = "Vacuna no encontrada";
    public static final String MSG_CHIP_NO_ENCONTRADO = "Chip no encontrado";
    public static final String MSG_CITA_NO_ENCONTRADA = "Cita no encontrada";
    public static final String MSG_USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String MSG_ADOPTANTE_INACTIVO = "No se puede asociar a un adoptante inactivo";
    public static final String MSG_MASCOTA_INACTIVA = "La mascota está inactiva";
    public static final String MSG_SIN_VACUNAS = "La mascota no tiene esquema de vacunación registrado";
    public static final String MSG_CORREO_DUPLICADO = "Ya existe un usuario con ese correo";

    // ============================================
    // CÓDIGOS Y VALORES
    // ============================================
    public static final Integer ESTADO_ACTIVO = 1;
    public static final Integer ESTADO_INACTIVO = 0;

    public static final String ROL_ADMIN = "ADMIN";
    public static final String ROL_CLIENTE = "CLIENTE";

    public static final String ESTATUS_PENDIENTE = "PENDIENTE";
    public static final String ESTATUS_COMPLETADA = "COMPLETADA";
    public static final String ESTATUS_CANCELADA = "CANCELADA";

    public static final String ESPECIE_PERRO = "Perro";
    public static final String ESPECIE_GATO = "Gato";

    // Constructor privado para evitar instanciación
    private UtilConstants() {
        throw new UnsupportedOperationException("Esta clase no puede ser instanciada");
    }
}