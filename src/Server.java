import java.io.IOException;
import java.net.*;
public class Server {

    private static String ip;
    final static int PORT = 8888;

    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        //ip = args[0];
        ip = "224.0.0.3";
        System.out.println("ip: " + ip);
        // Get the address that we are going to connect to.
        InetAddress addr = InetAddress.getByName(ip);

        try (DatagramSocket serverSocket = new DatagramSocket()) {

            String msg = "ESTOY ENVIANDO UN MENSAJE :V";

            // Create a packet that will contain the data
            // (in the form of bytes) and send it.
            DatagramPacket msgPacket = new DatagramPacket(msg.getBytes(),
                    msg.getBytes().length, addr, PORT);
            DatagramPacket msgPacket2 = new DatagramPacket(msg.getBytes(),
                    msg.getBytes().length, addr, 8889);
            serverSocket.send(msgPacket);
            serverSocket.send(msgPacket2);


            System.out.println("Server sent packet with msg: " + msg);
            Thread.sleep(2000);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


