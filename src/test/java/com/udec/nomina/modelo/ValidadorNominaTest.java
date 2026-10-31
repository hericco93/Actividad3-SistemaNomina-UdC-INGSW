package com.udec.nomina.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para las reglas de negocio del Estudiante 3.
 */
public class ValidadorNominaTest {

    @Test
    public void testValidarMontoNoNegativoCorrecto() {
        assertDoesNotThrow(() -> ValidadorNomina.validarMontoNoNegativo(100.0, "Horas trabajadas"));
    }

    @Test
    public void testValidarMontoNoNegativoError() {
        assertThrows(IllegalArgumentException.class, () -> {
            ValidadorNomina.validarMontoNoNegativo(-10.0, "Horas trabajadas");
        });
    }

    @Test
    public void testCalcularFondoAhorroAplica() {
        double fondo = ValidadorNomina.calcularFondoAhorro(1000000.0, 2, true);
        assertEquals(20000.0, fondo, 0.01);
    }

    @Test
    public void testCalcularFondoAhorroNoAplicaPorAntiguedad() {
        double fondo = ValidadorNomina.calcularFondoAhorro(1000000.0, 1, true);
        assertEquals(0.0, fondo, 0.01);
    }

    @Test
    public void testCalcularFondoAhorroNoAplicaPorRechazo() {
        double fondo = ValidadorNomina.calcularFondoAhorro(1000000.0, 3, false);
        assertEquals(0.0, fondo, 0.01);
    }
}
