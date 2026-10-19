public class Pistas {

    private final boolean[] arrayPistas;
    final int NUMERO_PISTAS = 2;


    public Pistas(){
        arrayPistas = new boolean[NUMERO_PISTAS];
        for (int i = 0; i <= arrayPistas.length; i++) {
            arrayPistas[i] = true;
        }
    }

    public synchronized int ocuparPista() throws InterruptedException {

        int pista =  buscarPistaLibre();
        while(pista == -1){
            wait();
        }
        arrayPistas[pista] = false;
        return pista;

    }

    public synchronized void liberarPista(int pista){
        arrayPistas[pista] = true;
        notifyAll();
    }

    private int buscarPistaLibre(){

        for (int i = 0; i < arrayPistas.length; i++) {
            if(arrayPistas[i]){
                return i;
        }
        }
        return -1;
    }

}
