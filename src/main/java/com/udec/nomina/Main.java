package com.udec.nomina;

import com.udec.nomina.modelo.Empleado;
import com.udec.nomina.modelo.EmpleadoAsalariado;
import com.udec.nomina.modelo.EmpleadoPorComision;
import com.udec.nomina.modelo.EmpleadoPorHoras;
import com.udec.nomina.modelo.EmpleadoTemporal;
import com.udec.nomina.modelo.ValidadorNomina;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

// Menú por consola: registra empleados, muestra la liquidación y hace una demostración
public class Main {

    private static final NumberFormat COP = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO"));
    private static final List<Empleado> empleados = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int opcion;
        do {
            System.out.println("\n===== SISTEMA DE NÓMINA =====");
            System.out.println("1. Registrar empleado");
            System.out.println("2. Ver liquidación de los empleados");
            System.out.println("3. Ejecutar demostración automática");
            System.out.println("0. Salir");
            opcion = leerEntero(sc, "Opción: ");
            switch (opcion) {
                case 1 -> registrarEmpleado(sc);
                case 2 -> mostrarLiquidaciones();
                case 3 -> demostracion();
                case 0 -> System.out.println("Hasta luego.");
                default -> System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    // Registro de empleados por consola
    private static void registrarEmpleado(Scanner sc) {
        System.out.println("Tipo: 1) Asalariado  2) Por horas  3) Temporal  4) Por comisión");
        int tipo = leerEntero(sc, "Tipo: ");
        if (tipo < 1 || tipo > 4) {
            System.out.println("Tipo no válido.");
            return;
        }
        try {
            System.out.print("Nombre: ");
            String nombre = sc.nextLine();
            System.out.print("Identificación: ");
            String identificacion = sc.nextLine();
            int anios = leerEntero(sc, "Años de antigüedad: ");
            Empleado empleado = crearEmpleado(sc, tipo, nombre, identificacion, anios);
            empleado.calcularSalarioNeto(); // si el neto es negativo lanza la excepción antes de guardar
            empleados.add(empleado);
            System.out.println("Empleado registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            System.out.println("[VALIDACIÓN] " + ex.getMessage());
        }
    }

    private static Empleado crearEmpleado(Scanner sc, int tipo, String nombre, String identificacion, int anios) {
        switch (tipo) {
            case 1:
                return new EmpleadoAsalariado(nombre, identificacion,
                        leerDouble(sc, "Salario mensual: "), anios);
            case 2:
                double tarifa = leerDouble(sc, "Tarifa por hora: ");
                double horas = leerDouble(sc, "Horas trabajadas: ");
                boolean aceptaFondo = leerSiNo(sc, "¿Acepta el fondo de ahorro? (s/n): ");
                return new EmpleadoPorHoras(nombre, identificacion, anios, tarifa, horas, aceptaFondo);
            case 3:
                double salario = leerDouble(sc, "Salario mensual: ");
                int meses = leerEntero(sc, "Duración del contrato en meses: ");
                return new EmpleadoTemporal(nombre, identificacion, salario, anios, meses);
            default:
                double base = leerDouble(sc, "Salario base: ");
                double ventas = leerDouble(sc, "Ventas del mes: ");
                double porcentaje = leerDouble(sc, "Porcentaje de comisión (ej. 0.05): ");
                return new EmpleadoPorComision(nombre, identificacion, base, anios, ventas, porcentaje);
        }
    }

    private static void mostrarLiquidaciones() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        empleados.forEach(Main::imprimirLiquidacion);
    }

    // Funciona con cualquier tipo de empleado porque solo usa lo que tiene Empleado (polimorfismo)
    static void imprimirLiquidacion(Empleado e) {
        System.out.println("\n--- " + e.getNombre() + " (" + e.getClass().getSimpleName()
                + ", " + e.getAniosAntiguedad() + " años) ---");
        System.out.println("Deducciones:   -" + COP.format(e.calcularDeducciones()));
        System.out.println("SALARIO NETO:   " + COP.format(e.calcularSalarioNeto()));
    }

    // Casos válidos de cada tipo y casos inválidos que el sistema debe bloquear
    private static void demostracion() {
        System.out.println("\n##### CASOS VÁLIDOS #####");
        List<Empleado> demo = List.of(
                new EmpleadoAsalariado("Ana Pérez", "1001", 3_000_000, 7),
                new EmpleadoPorHoras("Luis Gómez", "1002", 2, 10_000, 45, true),
                new EmpleadoTemporal("María Ruiz", "1003", 1_800_000, 1, 6),
                new EmpleadoPorComision("Carlos Díaz", "1004", 2_000_000, 3, 25_000_000, 0.05));
        demo.forEach(Main::imprimirLiquidacion);

        System.out.println("\n##### CASOS INVÁLIDOS (deben ser bloqueados) #####");
        intentar("Horas negativas", () -> new EmpleadoPorHoras("Test", "1", 1, 10_000, -5, false));
        intentar("Ventas menores a cero", () -> new EmpleadoPorComision("Test", "2", 1_000_000, 1, -100, 0.05));
        intentar("Salario neto negativo", () -> new EmpleadoTemporal("Test", "3", -1_000_000, 1, 6).calcularSalarioNeto());
        intentar("Validación directa de neto negativo", () -> ValidadorNomina.validarSalarioNeto(-1));
    }

    private static void intentar(String caso, Runnable accion) {
        try {
            accion.run();
            System.out.println("[FALLO] " + caso + ": no fue bloqueado.");
        } catch (IllegalArgumentException ex) {
            System.out.println("[OK] " + caso + " -> bloqueado: " + ex.getMessage());
        }
    }

    // Lectura de consola que vuelve a pedir el dato si no es un número
    private static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un número entero.");
            }
        }
    }

    private static double leerDouble(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException ex) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    private static boolean leerSiNo(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine().trim().equalsIgnoreCase("s");
    }
}
