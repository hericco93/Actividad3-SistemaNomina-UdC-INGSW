# Actividad 3 - Sistema de Nómina

Actividad de la Unidad 3 de Ingeniería de Software, Universidad de Cartagena.
Aplicación de consola en Java que liquida el salario neto de cuatro tipos de empleado, con programación orientada a objetos y principios SOLID.

## Requisitos

- Java 17
- Maven (o IntelliJ IDEA)

## Cómo ejecutar

```bash
mvn test
```

Para usar el sistema, ejecutar la clase `com.udec.nomina.Main` desde IntelliJ. La opción 3 del menú corre una demostración con casos válidos e inválidos.

## Estructura

```
src/main/java/com/udec/nomina
├── Main.java                      menú por consola y demostración
└── modelo
    ├── Empleado.java              clase abstracta base
    ├── EmpleadoAsalariado.java
    ├── EmpleadoPorHoras.java
    ├── EmpleadoTemporal.java
    ├── EmpleadoPorComision.java
    └── ValidadorNomina.java       validaciones y fondo de ahorro
src/test/java/com/udec/nomina/modelo   pruebas unitarias
```

## Reglas de negocio

| Tipo | Regla |
|------|-------|
| Asalariado | Salario fijo mensual. Bono del 10% si lleva más de 5 años. |
| Por horas | Tarifa por hora. Las horas después de 40 se pagan a 1.5x. Sin bonos. Fondo de ahorro del 2% si tiene más de 1 año y lo acepta. |
| Temporal | Salario fijo, contrato por tiempo definido. Sin bonos ni beneficios. |
| Por comisión | Salario base más porcentaje de ventas. Bono adicional del 3% sobre las ventas si superan $20.000.000. |
| Deducciones | 4% de seguro social y pensión, más ARL (0.522%). |
| Bono de alimentación | $1.000.000 para empleados permanentes (Asalariado y Por comisión). |

## Validaciones

Todas lanzan `IllegalArgumentException` y bloquean la operación:

- Salario neto negativo.
- Horas trabajadas negativas.
- Ventas menores a $0.
- Además: tarifa por hora o porcentaje de comisión negativos, y contrato temporal de 0 meses o menos.

## Pruebas unitarias

JUnit 5. Cubren los valores límite de las reglas críticas: 5 y 6 años para el bono, 40 y 45 horas, 1 año para el fondo de ahorro, ventas de $20.000.000 y mayores, deducciones y bono de alimentación. Las pruebas de integración revisan las validaciones y que todos los tipos calculen su neto con la misma clase base.

## Principios SOLID

- **S:** cada clase tiene una sola tarea. `ValidadorNomina` valida y cada empleado calcula su pago.
- **O:** para agregar un tipo de empleado se crea una clase que hereda de `Empleado`, sin modificar las otras.
- **L:** cualquier subclase se puede usar donde se espera un `Empleado`. `Main` imprime la liquidación de todos con el mismo método.
- **I:** las clases hijas solo implementan lo que les aplica.
- **D:** la liquidación en `Main` depende de la clase abstracta `Empleado` y no de los tipos concretos.

## Metodología de desarrollo

Trabajamos en equipo de 4 personas con un enfoque ágil ligero:

1. **Análisis:** se dividió el enunciado en partes por rol: arquitectura y asalariado, empleados operativos, comisión y deducciones, y validaciones, pruebas e integración.
2. **Diseño:** una clase abstracta `Empleado` y una clase por tipo de empleado, cada una con su forma de calcular el salario neto (herencia y polimorfismo).
3. **Control de versiones con Git y GitHub:** cada integrante trabajó en su propia rama (`feature/...` o `fix/...`), con commits pequeños y mensajes que indican el cambio (`feat`, `fix`, `test`, `docs`).
4. **Pull Requests:** los cambios se unieron a `main` con Pull Requests que el Estudiante 1 revisó y aprobó.
5. **Pruebas:** cada regla de negocio y sus valores límite tiene su prueba unitaria.
6. **Integración:** la clase `Main` instancia todos los tipos de empleado y comprueba el funcionamiento del sistema completo.
7. **Refactorización:** se corrigieron valores y cálculos al integrar el trabajo (bono de alimentación y deducciones) y se ajustaron las pruebas.

## Supuestos

- La tasa de ARL es 0.522% (riesgo I) porque el enunciado no la indica. Está en `Empleado`.
- Para Asalariado y Temporal las deducciones se calculan sobre el salario base. Para Por horas y Por comisión, sobre el salario bruto.
- El 2% del fondo de ahorro se descuenta del salario neto del empleado por horas, porque se deposita en su fondo.

## Integrantes

- Estudiante 1: arquitectura (`Empleado`) y `EmpleadoAsalariado`
- Estudiante 2: `EmpleadoPorHoras`, `EmpleadoTemporal` y fondo de ahorro
- Estudiante 3: `EmpleadoPorComision`, deducciones y bono de alimentación
- Estudiante 4: validaciones, pruebas, `Main` y documentación
