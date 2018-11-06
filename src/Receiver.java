import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Receiver extends Thread {
    private InetAddress myInetAddress;
    private InetAddress multycastInetAddress;
    private MulticastSocket socket;
    private int port;
    private boolean trigger = true;
    private DatagramPacket receievedDatagramPacket;
    private byte[] receivedBuf;
    private AppMemory appMemory;

    public Receiver(InetAddress myInetAddress, InetAddress multycastInetAddress, int port, MulticastSocket socket, AppMemory appMemory) {
        this.myInetAddress = myInetAddress;
        this.multycastInetAddress = multycastInetAddress;
        this.port = port;
        this.socket = socket;
        receivedBuf = new byte[200];
        receievedDatagramPacket = new DatagramPacket(receivedBuf, receivedBuf.length);
        this.appMemory = appMemory;
    }


    public void run() {

        while (trigger) {
            try {
                socket.receive(receievedDatagramPacket);
                System.out.println("received " + receievedDatagramPacket.getLength() + "bytes from " + receievedDatagramPacket.getAddress() + " at " + LocalDateTime.now());
                appMemory.addInIPMap(receievedDatagramPacket.getAddress(), LocalTime.now());
                receievedDatagramPacket.setLength(receivedBuf.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
