package com.udec.nomina.modelo;

/**
 * Clase que representa a un empleado que cobra salario base más comisión por ventas.
 */
public class EmpleadoPorComision extends Empleado {

    private double salarioBase;
    private double ventasRealizadas;
    private double porcentajeComision; // Ejemplo: 0.05 para 5%

    public EmpleadoPorComision(String nombre, String identificacion, double salarioBase, double ventasRealizadas, double porcentajeComision) {
        super(nombre, identificacion);
        
        ValidadorNomina.validarMontoNoNegativo(salarioBase, "Salario base");
        ValidadorNomina.validarMontoNoNegativo(ventasRealizadas, "Ventas realizadas");
        ValidadorNomina.validarMontoNoNegativo(porcentajeComision, "Porcentaje de comisión");

        this.salarioBase = salarioBase;
        this.ventasRealizadas = ventasRealizadas;
        this.porcentajeComision = porcentajeComision;
    }

    @Override
    public double calcularSalarioBruto() {
        double comisionBase = ventasRealizadas * porcentajeComision;
        double bonoExtra = 0.0;

        // Regla: 3% extra sobre las ventas si pasan de 20 millones
        if (ventasRealizadas > 20000000.0) {
            bonoExtra = ventasRealizadas * 0.03;
        }

        return salarioBase + comisionBase + bonoExtra;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getVentasRealizadas() {
        return ventasRealizadas;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }
}
