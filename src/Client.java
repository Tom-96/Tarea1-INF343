import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Client {

    final static String INET_ADDR = "224.0.0.3";
    final static int[] PORT = {10111,10112,10113,10114};

    public static void main(String[] args) throws UnknownHostException {
        String ip = args[0]; 
        String suscripciones = args[1];
        String historialBool = args[2];
        
        // Suscripción a variables 
        for (int i = 0; i < suscripciones.length (); i++) { 
            char c = suscripciones.charAt (i);
            if (c=='1'){
                //Crear thread
                Thread thread = new ClientThread(ip,PORT[i]);
                thread.start();
            }
        }
    }
}
