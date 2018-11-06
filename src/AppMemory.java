import java.net.InetAddress;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AppMemory implements Runnable {
    private Map<InetAddress, LocalTime> IPMap = new HashMap<>();
    private InetAddress[] addresses = new InetAddress[100];
    private int i = 0;

    public void addInIPMap(InetAddress address, LocalTime time) {
        addresses[i++] = address;
        if (IPMap.containsKey(address)) {
            IPMap.remove(address);
            addresses[--i] = null;
        }
        IPMap.put(address, time);
        System.out.println("address was added  " + address + " at " + LocalTime.now());
    }

    @Override
    public void run() {
        for(int j = 0, k = 0; j < i; j++) {
            if(IPMap.get(addresses[j]).getMinute() - LocalTime.now().getMinute() > 1) {
                IPMap.remove(addresses[j]);
            } else {
                System.out.println("Address " + addresses[j] + " is alive ");
            }
        }
        System.out.println("Check addresses ended at " + LocalTime.now());
    }
}
