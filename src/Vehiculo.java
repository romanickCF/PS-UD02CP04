import java.util.Random;

public class Vehiculo extends Thread {

    private final String nombre;
    private final Pistas pistas;
    private final Random random = new Random();

    public Vehiculo(String nombre, Pistas pistas) {
        this.nombre = nombre;
        this.pistas = pistas;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        try {
            int pista = pistas.ocuparPista(this);

            long inicio = System.currentTimeMillis();
            Thread.sleep(2000 + random.nextInt(3001));
            double segundos = (System.currentTimeMillis() - inicio) / 1000.0;

            System.out.println("Vehículo " + nombre + " completó la prueba en " + segundos + " segundos.");
            pistas.liberarPista(pista, this);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}