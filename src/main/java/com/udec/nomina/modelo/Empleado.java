package com.udec.nomina.modelo;

public abstract class Empleado {
    private String nombre;
    private String identificacion;
    private double salarioBase;
    private int aniosAntiguedad;

    // Constante centralizada para el bono de alimentación
    public static final double BONO_ALIMENTACION = 1000000.0;

    // Seguro social y pensión (4%) más ARL (0.522%)
    private static final double PORCENTAJE_SEGURO_PENSION = 0.04;
    private static final double PORCENTAJE_ARL = 0.00522;

    public Empleado(String nombre, String identificacion, double salarioBase, int aniosAntiguedad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.salarioBase = salarioBase;
        this.aniosAntiguedad = aniosAntiguedad;
    }

    // Método abstracto para que cada tipo de empleado calcule su salario neto
    public abstract double calcularSalarioNeto();

    // Deducciones obligatorias sobre el salario base
    public double calcularDeducciones() {
        return calcularDeducciones(this.salarioBase);
    }

    // Mismas deducciones pero sobre un monto dado, para los que cobran distinto al salario base
    protected double calcularDeducciones(double monto) {
        return monto * (PORCENTAJE_SEGURO_PENSION + PORCENTAJE_ARL);
    }

    // Getters necesarios
    public double getSalarioBase() { return salarioBase; }
    public int getAniosAntiguedad() { return aniosAntiguedad; }
    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
}
