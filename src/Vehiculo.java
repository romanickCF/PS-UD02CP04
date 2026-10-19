import java.util.Random;

public class Vehiculo extends Thread {

    private final String nombre;
    private final Pistas pistas;
    private final Random random = new Random();

    public Vehiculo(String nombre, Pistas pistas) {
        this.nombre = nombre;
        this.pistas = pistas;
    }
}
