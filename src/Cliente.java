import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

    public static int[] PORT = {10111,10112,10113,10114};

    public static void main(String[] args) throws UnknownHostException {
        String historialBool = args[0];
        if (historialBool.equals("1")) {
            final String HOST = "127.0.0.1";
            final int PUERTO = 8000;
            DataInputStream in;
            DataOutputStream out;
            try {
                Socket sc = new Socket(HOST,PUERTO);
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF("Hola mundo desde el cliente");
                
                String mensaje  = in.readUTF();
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
    }
}