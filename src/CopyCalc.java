import javax.naming.Binding;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CopyCalc {
    private final static int multyCastport = 49670;
    private final static int port = 49666;
    private static AppMemory appMemory = new AppMemory();
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {

        try {
            InetAddress multycastInetAddress = InetAddress.getByName(args[0]);
            InetAddress myAddress = InetAddress.getByName(args[1]);
            if (!multycastInetAddress.isMulticastAddress()) {
                throw new IllegalArgumentException("input address is non multycast");
            }

            if(!multycastInetAddress.isReachable(1000)) {
                System.out.println("multy is reachable");
            }

            MulticastSocket socket = new MulticastSocket(multyCastport);
            socket.joinGroup(InetAddress.getByName(args[0]));
            DatagramSocket datagramSocket = new DatagramSocket(port, InetAddress.getByName(args[1]));

            Sender sender = new Sender(myAddress, InetAddress.getByName(args[0]), datagramSocket, multyCastport);
            Receiver receiver = new Receiver(myAddress, InetAddress.getByName(args[0]), port, socket, appMemory);

            Thread receive = new Thread(receiver);
            receive.start();
            Thread send = new Thread(sender);
            send.start();
            scheduler.scheduleAtFixedRate(appMemory, 5, 60, TimeUnit.SECONDS);

        } catch (IllegalArgumentException e) {
            e.getMessage();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
