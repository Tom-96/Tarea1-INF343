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
        String mediciones = args[1];
        String anteriores = args[2];
        
        // Suscripción a variables 
        for (int i = 0; i < mediciones.length (); i++) { 
            char c = mediciones.charAt (i);
            if (c=='1'){
                System.out.println (c);
                //Crear thread
                Thread thread = new ClientHandler(ip,PORT[i]);
            }
        }
    }
}
