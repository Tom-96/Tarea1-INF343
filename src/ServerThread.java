import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;

class ServerThread extends Thread {
    final String ip;
    final int PORT;
    final String nombre;

    // Constructor
    public ServerThread(String ip, int PORT,String nombre) {
        this.ip = ip;
        this.PORT = PORT;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        InetAddress address = null;
        String filename = "historial.txt";
        BufferedWriter bw = null;
        FileWriter fw = null;
        int id = 0;

        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (DatagramSocket serverSocket = new DatagramSocket()) {
            Random rand = new Random();
            int n = 0;
            while(true){
                id = id + 1;
                n = rand.nextInt(100);
                String msg = id + ". " + nombre+ ": " +"Puerto: " +PORT+" Valor: "+ n;

                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, address, PORT);
                serverSocket.send(msgPacket);
                System.out.println(msg);

                //escribir historial en archivo
                fw = new FileWriter(filename, true);
                bw = new BufferedWriter(fw);
                bw.write(msg + "\n");
                bw.close();
                fw.close();

                Thread.sleep(2000);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }
}