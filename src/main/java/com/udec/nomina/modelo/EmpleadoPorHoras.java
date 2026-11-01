package com.udec.nomina.modelo;

// Empleado que cobra por horas trabajadas. No recibe bonos.
public class EmpleadoPorHoras extends Empleado {

    private static final double HORAS_NORMALES_MAXIMAS = 40.0;
    private static final double FACTOR_HORA_EXTRA = 1.5;

    private final double tarifaPorHora;
    private final double horasTrabajadas;
    private final boolean aceptaFondoAhorro;

    public EmpleadoPorHoras(String nombre, String identificacion, int aniosAntiguedad,
                            double tarifaPorHora, double horasTrabajadas, boolean aceptaFondoAhorro) {
        // No tiene salario base fijo, por eso se manda 0
        super(nombre, identificacion, 0.0, aniosAntiguedad);
        ValidadorNomina.validarMontoNoNegativo(tarifaPorHora, "tarifa por hora");
        ValidadorNomina.validarMontoNoNegativo(horasTrabajadas, "horas trabajadas");
        this.tarifaPorHora = tarifaPorHora;
        this.horasTrabajadas = horasTrabajadas;
        this.aceptaFondoAhorro = aceptaFondoAhorro;
    }

    // Las horas después de 40 se pagan a 1.5 veces la tarifa
    public double calcularSalarioBruto() {
        double horasNormales = Math.min(horasTrabajadas, HORAS_NORMALES_MAXIMAS);
        double horasExtras = Math.max(0.0, horasTrabajadas - HORAS_NORMALES_MAXIMAS);
        return (horasNormales * tarifaPorHora) + (horasExtras * tarifaPorHora * FACTOR_HORA_EXTRA);
    }

    // Las deducciones se sacan del salario bruto
    @Override
    public double calcularDeducciones() {
        return calcularDeducciones(calcularSalarioBruto());
    }

    // 2% del bruto, solo si tiene más de 1 año y aceptó el fondo
    public double calcularAporteFondoAhorro() {
        return ValidadorNomina.calcularFondoAhorro(calcularSalarioBruto(), getAniosAntiguedad(), aceptaFondoAhorro);
    }

    @Override
    public double calcularSalarioNeto() {
        double neto = calcularSalarioBruto() - calcularDeducciones() - calcularAporteFondoAhorro();
        ValidadorNomina.validarSalarioNeto(neto);
        return neto;
    }
}
