import java.io.IOException;
import java.net.*;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.concurrent.locks.*;

class ServerThread extends Thread {
    final String ip;
    final int[] PORT;
    final String[] nombre;

    // Constructor
    public ServerThread(String ip, int[] PORT,String[] nombre) {
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
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        Lock writeLock = rwLock.writeLock();

        try {
            address = InetAddress.getByName(ip);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try (DatagramSocket serverSocket = new DatagramSocket()) {
            Random rand = new Random();
            int n1 = 0;
            int n2 = 0;
            int n3 = 0;

            fw = new FileWriter(filename);
            fw.close();
            while(true){
                id = id + 1;
                n1 = rand.nextInt(100);
                n2 = rand.nextInt(100);
                n3 = rand.nextInt(100);

                String msg1 = id + ". " + nombre[0]+ ": " +"Puerto: " +PORT[0]+" Valor: "+ n1;
                String msg2 = id + ". " + nombre[1]+ ": " +"Puerto: " +PORT[1]+" Valor: "+ n2;
                String msg3 = id + ". " + nombre[2]+ ": " +"Puerto: " +PORT[2]+" Valor: "+ n3;

                writeLock.lock();
                try {
                    //escribir historial en archivo
                    fw = new FileWriter(filename, true);
                    bw = new BufferedWriter(fw);
                    bw.write(msg1 + "\n");
                    bw.write(msg2 + "\n");
                    bw.write(msg3 + "\n");
                    bw.close();
                    fw.close();
                } finally {

                    writeLock.unlock();
                }


                DatagramPacket msgPacket1 = new DatagramPacket(msg1.getBytes(),
                        msg1.getBytes().length, address, PORT[0]);
                serverSocket.send(msgPacket1);
                DatagramPacket msgPacket2 = new DatagramPacket(msg2.getBytes(),
                        msg2.getBytes().length, address, PORT[1]);
                serverSocket.send(msgPacket2);
                DatagramPacket msgPacket3 = new DatagramPacket(msg3.getBytes(),
                        msg3.getBytes().length, address, PORT[2]);
                serverSocket.send(msgPacket3);

                System.out.println(msg1);
                System.out.println(msg2);
                System.out.println(msg3);

                Thread.sleep(4000);
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