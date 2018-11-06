import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.time.LocalDateTime;

public class Sender extends Thread {
    private DatagramPacket datagramPacket;
    private byte[] datagramMessage;
    private String stringDatagramMessage;
    private InetAddress myInetAddress;
    private InetAddress multycastInetAddress;
    private DatagramSocket socket;
    private int port;

    public Sender(InetAddress myInetAddress, InetAddress multycastInetAddress, DatagramSocket socket, int port) {
        this.port = port;
        this.myInetAddress = myInetAddress;
        this.socket = socket;
        this.multycastInetAddress = multycastInetAddress;
    }

    public void run() {
        try {
            while (true) {
                //this.socket = new DatagramSocket(port);
                //socket = new MulticastSocket(port);
                stringDatagramMessage = myInetAddress.toString() + LocalDateTime.now().toString();
                datagramMessage = stringDatagramMessage.getBytes();
                datagramPacket = new DatagramPacket(datagramMessage, datagramMessage.length, new InetSocketAddress(multycastInetAddress, port));
                socket.send(datagramPacket);
                System.out.println("I " + myInetAddress + " sent at " + LocalDateTime.now() + "   to " + multycastInetAddress);
                System.out.println();
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
