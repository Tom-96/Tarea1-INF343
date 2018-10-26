import java.io.*;
import java.net.*;
import java.util.concurrent.locks.*;

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

        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        Lock readLock = rwLock.readLock();
        Lock writeLock = rwLock.writeLock();

        try {
            server = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado");

            while(true){
                sc = server.accept();

                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());

                String mensaje = in.readUTF();
                System.out.println("Enviando historial a "+ mensaje);

                readLock.lock();
                try {
                    String cadena;
                    FileReader f = new FileReader("historial.txt");
                    BufferedReader b = new BufferedReader(f);

                    int num_lines = 0;
                    while((b.readLine())!=null) {
                        num_lines ++;
                    }
                    b.close();

                    f = new FileReader("historial.txt");
                    b = new BufferedReader(f);

                    out.writeUTF(Integer.toString(num_lines));
                    while((cadena = b.readLine())!=null) {
                        out.writeUTF(cadena);
                    }
                    b.close();
                    sc.close();
                    System.out.println("Historial enviado");
                } finally {

                    readLock.unlock();
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
