package com.udec.nomina.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Pruebas de integración: validaciones estrictas y todos los tipos de empleado juntos
public class IntegracionNominaTest {

    @Test
    public void testHorasNegativasSonBloqueadas() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmpleadoPorHoras("Luis", "2", 1, 10_000, -1, false));
    }

    @Test
    public void testVentasNegativasSonBloqueadas() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmpleadoPorComision("Carlos", "3", 1_000_000, 1, -1, 0.05));
    }

    @Test
    public void testSalarioNetoNegativoEsBloqueado() {
        EmpleadoTemporal temporal = new EmpleadoTemporal("Maria", "4", -1_000_000, 1, 6);
        assertThrows(IllegalArgumentException.class, temporal::calcularSalarioNeto);
    }

    @Test
    public void testTodosLosTiposCalculanNetoPositivo() {
        Empleado[] empleados = {
                new EmpleadoAsalariado("Ana", "1", 3_000_000, 6),
                new EmpleadoPorHoras("Luis", "2", 2, 10_000, 45, true),
                new EmpleadoTemporal("Maria", "3", 1_800_000, 1, 6),
                new EmpleadoPorComision("Carlos", "4", 2_000_000, 3, 25_000_000, 0.05)
        };
        for (Empleado e : empleados) {
            assertTrue(e.calcularSalarioNeto() > 0, e.getClass().getSimpleName());
        }
    }

    @Test
    public void testSoloLosPermanentesRecibenBonoDeAlimentacion() {
        // Mismo salario: el asalariado cobra 1.000.000 más que el temporal
        double asalariado = new EmpleadoAsalariado("Ana", "1", 2_000_000, 1).calcularSalarioNeto();
        double temporal = new EmpleadoTemporal("Maria", "2", 2_000_000, 1, 6).calcularSalarioNeto();
        assertEquals(1_000_000.0, asalariado - temporal, 0.01);
    }
}
