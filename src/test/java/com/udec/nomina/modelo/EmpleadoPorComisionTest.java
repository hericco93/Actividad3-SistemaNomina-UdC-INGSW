package com.udec.nomina.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoPorComisionTest {

    @Test
    public void testCalcularSalarioBrutoSinBonoExtra() {
        // Ventas <= 20M: Base 1.500.000 + (10.000.000 * 5%) = 2.000.000
        EmpleadoPorComision emp = new EmpleadoPorComision("Carlos Gomez", "1010", 1500000.0, 2, 10000000.0, 0.05);
        assertEquals(2000000.0, emp.calcularSalarioBruto(), 0.01);
    }

    @Test
    public void testCalcularSalarioBrutoConBonoExtra3PorCiento() {
        // Ventas > 20M (30.000.000): 
        // Base: 1.500.000 + Comisión (5%): 1.500.000 + Bono Extra (3%): 900.000 = 3.900.000
        EmpleadoPorComision emp = new EmpleadoPorComision("Ana Martinez", "2020", 1500000.0, 5, 30000000.0, 0.05);
        assertEquals(3900000.0, emp.calcularSalarioBruto(), 0.01);
    }

    @Test
    public void testVentasNegativasLanzaExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> {
            new EmpleadoPorComision("Pedro Perez", "3030", 1500000.0, 1, -500.0, 0.05);
        });
    }
}
