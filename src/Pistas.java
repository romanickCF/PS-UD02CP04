public class Pistas {

    private final boolean[] disponibles;

    public Pistas(int numeroPistas) {
        disponibles = new boolean[numeroPistas];
        for (int i = 0; i < disponibles.length; i++) {
            disponibles[i] = true;
        }
    }


    public synchronized int ocuparPista(Vehiculo v) throws InterruptedException {
        int pista;
        while ((pista = buscarLibre()) == -1) {
            wait();
        }
        disponibles[pista] = false;
        System.out.println("Vehículo " + v.getNombre() + " ha entrado en la pista " + (pista + 1) + ".");
        return pista;
    }

    public synchronized void liberarPista(int pista, Vehiculo v) {
        disponibles[pista] = true;
        System.out.println("Vehículo " + v.getNombre() + " ha salido de la pista " + (pista + 1) + ".");
        notifyAll();
    }


    private int buscarLibre() {
        for (int i = 0; i < disponibles.length; i++) {
            if (disponibles[i]) {
                return i;
            }
        }
        return -1;
    }
}