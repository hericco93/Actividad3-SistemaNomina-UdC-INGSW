package com.udec.nomina.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias del Empleado Temporal (salario fijo, sin bonos ni beneficios).
 */
public class EmpleadoTemporalTest {

    private static final double DELTA = 0.001;

    @Test
    public void testSalarioNetoEsFijoMenosDeducciones() {
        // 2.000.000 - 4% (80.000) = 1.920.000
        EmpleadoTemporal e = new EmpleadoTemporal("Luis", "2", 2000000, 0, 6);
        assertEquals(1920000.0, e.calcularSalarioNeto(), DELTA);
    }

    @Test
    public void testNoRecibeBonosAunqueTengaAntiguedad() {
        EmpleadoTemporal e = new EmpleadoTemporal("Luis", "2", 2000000, 10, 6);
        assertEquals(1920000.0, e.calcularSalarioNeto(), DELTA);
    }

    @Test
    public void testContratoDebeTenerDuracionPositiva() {
        assertThrows(IllegalArgumentException.class,
                () -> new EmpleadoTemporal("Luis", "2", 2000000, 0, 0));
    }

    @Test
    public void testGuardaDuracionDelContrato() {
        EmpleadoTemporal e = new EmpleadoTemporal("Luis", "2", 2000000, 0, 6);
        assertEquals(6, e.getMesesContrato());
    }
}
