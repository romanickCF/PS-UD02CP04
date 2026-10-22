public class Main {
    public static void main(String[] args) throws InterruptedException {
        Pistas pistas = new Pistas(2);

        Vehiculo[] vehiculos = {
                new Vehiculo("1", pistas),
                new Vehiculo("2", pistas),
                new Vehiculo("3", pistas),
                new Vehiculo("4", pistas)
        };

        for (Vehiculo v : vehiculos) {
            v.start();
        }
        for (Vehiculo v : vehiculos) {
            v.join();
        }
    }
}

