# Caso Práctico 4: Banco de pruebas con acceso limitado a pistas

Programación multihilo en Java. Simulación de un banco de pruebas de vehículos donde **cuatro vehículos** (hilos) compiten por **dos pistas** de prueba, coordinándose con `synchronized`, `wait()` y `notifyAll()`.

## Descripción del problema

- Hay 4 vehículos (V1, V2, V3 y V4) que necesitan realizar una prueba en pista.
- El centro solo dispone de **2 pistas**, y cada pista solo puede ser ocupada por **un vehículo a la vez**.
- Si hay una pista libre, el vehículo la ocupa y realiza la prueba durante un **tiempo aleatorio entre 2 y 5 segundos**.
- Si ambas pistas están ocupadas, el vehículo **espera** hasta que se libere alguna.
- Al terminar, el vehículo **libera la pista** y avisa a los que esperan.
- Como máximo puede haber **dos vehículos probando simultáneamente**.

## Estructura del proyecto

```
src/
├── Main.java       # Crea las pistas y los vehículos, los lanza y espera a que terminen
├── Pistas.java     # Recurso compartido: array de booleanos con la disponibilidad de cada pista
└── Vehiculo.java   # Hilo (extends Thread) que ocupa una pista, prueba y la libera
```

### `Pistas`
Recurso compartido protegido con monitores.

| Método | Descripción |
|---|---|
| `ocuparPista(Vehiculo v)` | `synchronized`. Busca una pista libre y la marca como ocupada de forma **atómica**. Si no hay ninguna, hace `wait()` dentro de un `while`. Devuelve el índice de la pista. |
| `liberarPista(int pista, Vehiculo v)` | `synchronized`. Marca la pista como libre y llama a `notifyAll()` para despertar a los vehículos en espera. |
| `buscarLibre()` | Método privado auxiliar, solo se invoca desde métodos ya sincronizados. |

### `Vehiculo`
Extiende `Thread`. En su método `run()`:

1. Solicita una pista con `ocuparPista` (espera si no hay).
2. Simula la prueba con `Thread.sleep()` de 2 a 5 segundos **fuera** del bloque sincronizado.
3. Calcula y muestra el tiempo empleado.
4. Libera la pista con `liberarPista`.

### `Main`
Crea un objeto `Pistas` con 2 pistas, lanza los 4 hilos con `start()` y espera a que acaben con `join()`.

## Sincronización: decisiones clave

- **Buscar y ocupar es una sola operación atómica.** Si fueran dos métodos `synchronized` separados, otro hilo podría colarse entre medias y ocupar la misma pista (condición de carrera).
- **`wait()` dentro de un `while`**, no de un `if`. Al despertar, el hilo vuelve a comprobar si realmente hay una pista libre, porque otro hilo pudo habérsela llevado antes.
- **`notifyAll()` al liberar** una pista, para despertar a todos los vehículos en espera.
- **El `sleep` se hace fuera de `synchronized`.** Si se hiciera dentro, el monitor quedaría bloqueado durante toda la prueba y no habría concurrencia real.
- **Todos los `start()` primero y los `join()` después** en el `main`, para que los hilos se ejecuten en paralelo.

## Requisitos

- JDK 17 o superior (probado con OpenJDK).

## Compilación y ejecución

Desde la carpeta `src`:

```bash
javac *.java
java Main
```

O desde el IDE (IntelliJ IDEA): ejecutar la clase `Main`.

## Ejemplo de salida

La salida varía en cada ejecución porque los tiempos son aleatorios y el orden de los hilos no está garantizado:

```
Vehículo 1 ha entrado en la pista 1.
Vehículo 2 ha entrado en la pista 2.
Vehículo 3 espera una pista libre...
Vehículo 4 espera una pista libre...
Vehículo 2 completó la prueba en 3.012 segundos.
Vehículo 2 ha salido de la pista 2.
Vehículo 3 ha entrado en la pista 2.
Vehículo 1 completó la prueba en 3.940 segundos.
Vehículo 1 ha salido de la pista 1.
Vehículo 4 ha entrado en la pista 1.
Vehículo 3 completó la prueba en 4.973 segundos.
Vehículo 3 ha salido de la pista 2.
Vehículo 4 completó la prueba en 2.481 segundos.
Vehículo 4 ha salido de la pista 1.
```

Se observa cómo dos vehículos entran a la vez, los otros dos esperan, y van accediendo dinámicamente según se liberan las pistas.

## Conceptos aplicados

- Creación de hilos con `Thread`
- Exclusión mutua con `synchronized`
- Comunicación entre hilos con `wait()` / `notifyAll()`
- Espera de finalización con `join()`
- Medición de tiempos con `System.currentTimeMillis()`