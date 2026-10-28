package main.java.com.udec.nomina.modelo;

public class EmpleadoAsalariado extends Empleado {

    // Tenemos la constante para el bono de alimentación de empleados permanentes
    private static final double BONO_ALIMENTACION = 1000000.0;

    public EmpleadoAsalariado(String nombre, String identificacion, double salarioBase, int aniosAntiguedad) {
        super(nombre, identificacion, salarioBase, aniosAntiguedad);
    }

    @Override
    public double calcularSalarioNeto() {
        double salario = getSalarioBase();

        // Relga de negocio en la que se calcula el bono mensual del 10% si lleva más de 5 años
        if (getAniosAntiguedad() > 5) {
            salario += (getSalarioBase() * 0.10);
        }

        // Se restan las deducciones (4% de pensión/salud calculado en la clase padre)
        salario -= calcularDeducciones();

        // En esta regla de negocio se suma el bono de alimentación
        salario += BONO_ALIMENTACION;

        // Validación de negocio la cual garantuza que ningún empleado pueda tener un salario neto negativo
        if (salario < 0) {
            throw new IllegalArgumentException("Error: El salario neto no puede ser negativo.");
        }

        return salario;
    }
}