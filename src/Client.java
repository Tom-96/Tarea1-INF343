import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Client {

    final static String INET_ADDR = "224.0.0.3";
    final static int[] PORT = {8888,8889,8890,8891};

    public static void main(String[] args) throws UnknownHostException {
        String ip = args[0]; 
        String mediciones = args[1];
        String anteriores = args[2];
        
        // Obtención de la dirección a la que nos queremos conectar
        InetAddress address = InetAddress.getByName(INET_ADDR);
        byte[] buf = new byte[256];
        // Suscripción a variables 
        for (int i = 0; i < mediciones.length (); i++) { 
            char c = mediciones.charAt (i);
            if (c=='1'){
                //Crear thread
                System.out.println (c);
            }
        }

        // Create a new Multicast socket (that will allow other sockets/programs
        // to join it as well.
        try (MulticastSocket clientSocket = new MulticastSocket(PORT[0])){
            //Joint the Multicast group.
            clientSocket.joinGroup(address);

            while (true) {
                // Receive the information and print it.
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);
                String msg = new String(buf, 0, buf.length);
                System.out.println("Socket 1 received msg: " + msg);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
