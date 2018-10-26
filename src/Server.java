import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
public class Server {

    private static String ip;
    final static int[] PORT = {10111,10112,10113};
    final static String[] NOMBRES = {"Temperatura","Presion","Humedad"};
    private static int PUERTO = 8000;

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        ip = args[0];
        System.out.println("ip: " + ip);

        ServerSocket server = null;
        Socket sc;
        DataInputStream in;
        DataOutputStream out;


        //intanciar thread
        Thread thread = new ServerThread(ip,PORT,NOMBRES);
        thread.start();


        try {
            server = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado");

            while(true){
                sc = server.accept();

                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();

                System.out.println(mensaje);
                out.writeUTF("Hola mundo desde el servidor");

                sc.close();
                System.out.println("Cliente desconectado");
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
