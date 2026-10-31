package com.udec.nomina.modelo;

/**
 * Módulo del Estudiante 3: Encargado de las reglas de negocio de validaciones
 * y cálculo del beneficio de Fondo de Ahorro.
 */
public class ValidadorNomina {

    private static final double PORCENTAJE_FONDO_AHORRO = 0.02;

    /**
     * Regla de Negocio: Validar que horas trabajadas o ventas no sean negativas.
     */
    public static void validarMontoNoNegativo(double valor, String nombreCampo) {
        if (valor < 0) {
            throw new IllegalArgumentException("Error de validación: El campo " + nombreCampo + " no puede ser negativo.");
        }
    }

    /**
     * Regla de Negocio: Validar que el salario neto final jamás sea menor a 0.
     */
    public static void validarSalarioNeto(double salarioNeto) {
        if (salarioNeto < 0) {
            throw new IllegalArgumentException("Error de Nómina: El salario neto no puede ser negativo.");
        }
    }

    /**
     * Regla de Negocio (Punto 3): Beneficio de Fondo de Ahorro (2%)
     * Aplica para Empleados por Horas con más de 1 año de antigüedad si aceptan el fondo.
     */
    public static double calcularFondoAhorro(double salarioBruto, int aniosAntiguedad, boolean aceptaFondo) {
        if (aniosAntiguedad > 1 && aceptaFondo) {
            return salarioBruto * PORCENTAJE_FONDO_AHORRO;
        }
        return 0.0;
    }
}
