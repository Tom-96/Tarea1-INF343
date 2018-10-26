import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.io.BufferedReader;
import java.io.FileReader;
public class Servidor {

    private static String ip;
    final static int[] PORT = {10111,10112,10113};
    final static String[] NOMBRES = {"Temperatura","Presion","Humedad"};
    private static int PUERTO = 8000;

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        ServerSocket server = null;
        Socket sc;
        DataInputStream in;
        DataOutputStream out;
        int i = 1449*3;//Número de lineas de historial.txt

        try {
            server = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado");

            while(true){
                sc = server.accept();

                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();
                
                System.out.println("Enviando historial");
                
                String cadena;
                FileReader f = new FileReader("historial.txt");
                BufferedReader b = new BufferedReader(f);
                out.writeUTF(Integer.toString(i));
                while((cadena = b.readLine())!=null) {
                    out.writeUTF(cadena);
                }
                b.close();
                sc.close();
                System.out.println("Historial enviado");
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}