package com.udec.nomina.modelo;

/**
 * Clase que representa a un empleado que cobra salario base más comisión por ventas.
 */
public class EmpleadoPorComision extends Empleado {

    private double ventasRealizadas;
    private double porcentajeComision; // Ejemplo: 0.05 para 5%

    public EmpleadoPorComision(String nombre, String identificacion, double salarioBase, int aniosAntiguedad, double ventasRealizadas, double porcentajeComision) {
        super(nombre, identificacion, salarioBase, aniosAntiguedad);
        
        ValidadorNomina.validarMontoNoNegativo(salarioBase, "Salario base");
        ValidadorNomina.validarMontoNoNegativo(ventasRealizadas, "Ventas realizadas");
        ValidadorNomina.validarMontoNoNegativo(porcentajeComision, "Porcentaje de comisión");

        this.ventasRealizadas = ventasRealizadas;
        this.porcentajeComision = porcentajeComision;
    }

    public double calcularSalarioBruto() {
        double comisionBase = ventasRealizadas * porcentajeComision;
        double bonoExtra = 0.0;

        // Regla: 3% extra sobre las ventas si pasan de 20 millones
        if (ventasRealizadas > 20000000.0) {
            bonoExtra = ventasRealizadas * 0.03;
        }

        return getSalarioBase() + comisionBase + bonoExtra;
    }

    @Override
    public double calcularSalarioNeto() {
        double bruto = calcularSalarioBruto();
        double deducciones = calcularDeducciones();
        return bruto - deducciones + BONO_ALIMENTACION;
    }

    public double getVentasRealizadas() {
        return ventasRealizadas;
    }

    public double getPorcentajeComision() {
        return porcentajeComision;
    }
}
