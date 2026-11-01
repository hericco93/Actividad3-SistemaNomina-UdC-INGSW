package com.udec.nomina.modelo;

public abstract class Empleado {
    private String nombre;
    private String identificacion;
    private double salarioBase;
    private int aniosAntiguedad;

    // Constante centralizada para el bono de alimentación
    public static final double BONO_ALIMENTACION = 80000.0;

    public Empleado(String nombre, String identificacion, double salarioBase, int aniosAntiguedad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.salarioBase = salarioBase;
        this.aniosAntiguedad = aniosAntiguedad;
    }

    // Método abstracto para que cada tipo de empleado calcule su salario neto
    public abstract double calcularSalarioNeto();

    /**
     * Deducciones obligatorias de ley:
     * Salud (4%) + Pensión (4%) + ARL (0.522%) = 8.522% (0.08522)
     */
    public double calcularDeducciones() {
        return this.salarioBase * (0.04 + 0.04 + 0.00522);
    }

    // Getters necesarios
    public double getSalarioBase() { return salarioBase; }
    public int getAniosAntiguedad() { return aniosAntiguedad; }
    public String getNombre() { return nombre; }
    public String getIdentificacion() { return identificacion; }
}
