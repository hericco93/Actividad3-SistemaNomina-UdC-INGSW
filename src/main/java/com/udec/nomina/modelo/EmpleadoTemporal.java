package com.udec.nomina.modelo;

// Empleado con contrato por tiempo definido. Sin bonos ni beneficios.
public class EmpleadoTemporal extends Empleado {

    private final int mesesContrato;

    public EmpleadoTemporal(String nombre, String identificacion, double salarioBase,
                            int aniosAntiguedad, int mesesContrato) {
        super(nombre, identificacion, salarioBase, aniosAntiguedad);
        if (mesesContrato <= 0) {
            throw new IllegalArgumentException("Error de validación: el contrato debe durar más de 0 meses.");
        }
        this.mesesContrato = mesesContrato;
    }

    public int getMesesContrato() {
        return mesesContrato;
    }

    // Salario fijo menos el 4% de deducciones
    @Override
    public double calcularSalarioNeto() {
        double neto = getSalarioBase() - calcularDeducciones();
        ValidadorNomina.validarSalarioNeto(neto);
        return neto;
    }
}
