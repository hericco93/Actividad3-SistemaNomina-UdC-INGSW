package com.udec.nomina.modelo;

public abstract class Empleado {
    private String nombre;
    private String identificacion;
    private double salarioBase;
    private int aniosAntiguedad;

    public Empleado(String nombre, String identificacion, double salarioBase, int aniosAntiguedad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.salarioBase = salarioBase;
        this.aniosAntiguedad = aniosAntiguedad;
    }

    // Este es el metodo abstracto el cual obliga a cada tipo de empleado a calcular su propio salario
    public abstract double calcularSalarioNeto();

    // Tenemos un metodo comun en el cual encontramos deducciones obligatorias del Seguro Social y Pensión que es del 4%
    public double calcularDeducciones() {
        return this.salarioBase * 0.04;
    }

    // Acá tenmos los gGetters necesarios para que las clases hijas accedan a los datos
    public double getSalarioBase() { return salarioBase; }
    public int getAniosAntiguedad() { return aniosAntiguedad; }
    public String getNombre() { return nombre; }
}