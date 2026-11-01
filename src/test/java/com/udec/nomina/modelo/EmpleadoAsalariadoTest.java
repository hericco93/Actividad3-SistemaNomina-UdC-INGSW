package com.udec.nomina.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Pruebas del Empleado Asalariado: bono del 10%, alimentación y neto
public class EmpleadoAsalariadoTest {

    private static final double DELTA = 0.01;

    @Test
    public void testConMasDeCincoAniosSuNetoIncluyeElBono() {
        // 3.000.000 + bono 300.000 + alimentación 1.000.000 - deducciones 135.660
        EmpleadoAsalariado e = new EmpleadoAsalariado("Ana", "1", 3_000_000, 6);
        assertEquals(4_164_340.0, e.calcularSalarioNeto(), DELTA);
    }

    @Test
    public void testConExactamenteCincoAniosNoRecibeBono() {
        // 3.000.000 + alimentación 1.000.000 - deducciones 135.660
        EmpleadoAsalariado e = new EmpleadoAsalariado("Ana", "1", 3_000_000, 5);
        assertEquals(3_864_340.0, e.calcularSalarioNeto(), DELTA);
    }

    @Test
    public void testDeduccionesSonCuatroPorCientoMasArl() {
        // 1.000.000 * 4,522% = 45.220
        EmpleadoAsalariado e = new EmpleadoAsalariado("Ana", "1", 1_000_000, 0);
        assertEquals(45_220.0, e.calcularDeducciones(), DELTA);
    }
}
