import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

class ClientThread extends Thread {
    final String ip;
    final int PORT;


    // Constructor
    public ClientThread(String ip, int PORT) {
        this.ip = ip;
        this.PORT = PORT;
    }

    @Override
    public void run() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        byte[] buf = new byte[256];

        try (MulticastSocket clientSocket = new MulticastSocket(PORT)) {
            //Joint the Multicast group.
            clientSocket.joinGroup(address);
            while (true) {
                //System.out.println(PORT);
                DatagramPacket msgPacket = new DatagramPacket(buf, buf.length);
                clientSocket.receive(msgPacket);

                String msg = new String(buf, 0, buf.length);
                System.out.println(msg);

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}