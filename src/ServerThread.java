import java.io.IOException;
import java.net.*;
import java.util.Random;

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
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (DatagramSocket serverSocket = new DatagramSocket()) {
            Random rand = new Random();
            int n = 0;
            while(true){
                n = rand.nextInt(100);
                String msg = nombre+ ": " +"Puerto: " +PORT+" Valor: "+ n;

                DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                        msg.getBytes().length, address, PORT);
                serverSocket.send(msgPacket);
                
                System.out.println(msg);
                Thread.sleep(2000);
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}