package com.udec.nomina.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del Empleado por Horas (tarifa, horas extras y fondo de ahorro).
 */
public class EmpleadoPorHorasTest {

    private static final double DELTA = 0.001;

    @Test
    public void testSinHorasExtrasPagaTarifaBase() {
        EmpleadoPorHoras e = new EmpleadoPorHoras("Ana", "1", 0, 10000, 30, false);
        assertEquals(300000.0, e.calcularSalarioBruto(), DELTA);
    }

    @Test
    public void testExactamente40HorasNoGeneraExtras() {
        EmpleadoPorHoras e = new EmpleadoPorHoras("Ana", "1", 0, 10000, 40, false);
        assertEquals(400000.0, e.calcularSalarioBruto(), DELTA);
    }

    @Test
    public void testHorasExtrasSePaganA1Punto5() {
        // 40 * 10000 + 10 * 10000 * 1.5 = 550000
        EmpleadoPorHoras e = new EmpleadoPorHoras("Ana", "1", 0, 10000, 50, false);
        assertEquals(550000.0, e.calcularSalarioBruto(), DELTA);
    }

    @Test
    public void testSalarioNetoDescuentaSeguroPensionYArl() {
        // bruto 400000 - 4% (16000) - ARL (2088) = 381912, sin bonos
        EmpleadoPorHoras e = new EmpleadoPorHoras("Ana", "1", 0, 10000, 40, false);
        assertEquals(381912.0, e.calcularSalarioNeto(), DELTA);
    }

    @Test
    public void testFondoAhorroConMasDeUnAnioYAceptacion() {
        // 381912 menos el fondo de 8000 = 373912
        EmpleadoPorHoras e = new EmpleadoPorHoras("Ana", "1", 2, 10000, 40, true);
        assertEquals(8000.0, e.calcularAporteFondoAhorro(), DELTA);
        assertEquals(373912.0, e.calcularSalarioNeto(), DELTA);
    }

    @Test
    public void testSinFondoSiNoAcepta() {
        EmpleadoPorHoras e = new EmpleadoPorHoras("Ana", "1", 5, 10000, 40, false);
        assertEquals(0.0, e.calcularAporteFondoAhorro(), DELTA);
    }

    @Test
    public void testSinFondoConUnAnioOMenos() {
        EmpleadoPorHoras e = new EmpleadoPorHoras("Ana", "1", 1, 10000, 40, true);
        assertEquals(0.0, e.calcularAporteFondoAhorro(), DELTA);
    }

    @Test
    public void testHorasNegativasLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmpleadoPorHoras("Ana", "1", 0, 10000, -1, false));
    }
}
