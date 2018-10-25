import java.io.IOException;
import java.net.*;
public class Server {

    private static String ip;
    final static int[] PORT = {10111,10112,10113,10114};
    final static String[] NOMBRES = {"Temperatura","Presion","Humedad"};

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        //ip = args[0];
        ip = "224.0.0.3";
        System.out.println("ip: " + ip);

        for (int i = 0; i < 4; i++) {
            //intanciar thread
            Thread thread = new ServerThread(ip,PORT[i],NOMBRES[i]);
            thread.start();
        }
    }
}
