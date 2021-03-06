import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    public static int[] PORT = {10111,10112,10113,10114};

    public static void main(String[] args) throws UnknownHostException {
        String ip = args[0]; 
        String suscripciones = args[1];
        String historialBool = args[2];
        
        // Suscripción a variables 

        if (historialBool.equals("1")) {
            final String HOST = "127.0.0.1";
            final int PUERTO = 9000;
            DataInputStream in;
            DataOutputStream out;
            try {
                Socket sc = new Socket(HOST,PUERTO);
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF(suscripciones);

                String mensaje  = in.readUTF();
                System.out.println(mensaje);
                int largoHistorial = Integer.parseInt(mensaje);

                for(int i = 0; i < largoHistorial; i++){
                    mensaje = in.readUTF();
                    System.out.println(mensaje);
                }

                sc.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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