package com.wgluka.connect.zookeeper;

import com.wgluka.connect.zookeeper.container.DataContainer;
import com.wgluka.connect.zookeeper.exception.AddressNotFoundException;
import com.wgluka.connect.zookeeper.watcher.ChildrenWatcher;
import com.wgluka.connect.zookeeper.watcher.ZookeeperWatcher;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;

public class ZookeeperConnector {
    private static String address;

    private static String monitorPath;

    public static void watch() throws InterruptedException, IOException, KeeperException {
        ZooKeeper zooKeeper = connect();
        DataContainer container = new DataContainer();
        List<String> children = zooKeeper.getChildren(monitorPath, new ChildrenWatcher(monitorPath, zooKeeper,
                container));
        container.modifyAddressList(children);
    }

    private static ZooKeeper connect() throws IOException, KeeperException, InterruptedException {
        init();
        return new ZooKeeper(address, 15000, new ZookeeperWatcher());
    }

    public static void publish() throws IOException, KeeperException, InterruptedException {
        init();
        Optional<String> ipOptional = getLocalIp();
        ipOptional.orElseThrow(() -> new AddressNotFoundException("can not get ip of local"));

        String path = String.join("/", monitorPath, ipOptional.get());
        ZooKeeper zooKeeper = new ZooKeeper(address, 15000, null);
        zooKeeper.create(path, ipOptional.get().getBytes(), OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }

    private static void init() {
        try (InputStream inputStream = ZookeeperConnector.class.getResourceAsStream("/zookeeper.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            address = properties.getProperty("zookeeper.address", "localhost:2181");
            monitorPath = properties.getProperty("zookeeper.monitor.path", "/wgluka");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Optional<String> getLocalIp() throws SocketException {
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            NetworkInterface networkInterface = enumeration.nextElement();
            if (networkInterface.isVirtual() || !networkInterface.isUp() || networkInterface.isLoopback()) {
                continue;
            }
            Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
            while (addressEnumeration.hasMoreElements()) {
                InetAddress address = addressEnumeration.nextElement();
                if ((address instanceof Inet4Address)) {
                    return Optional.ofNullable(address.getHostAddress());
                }
            }
        }
        return Optional.empty();
    }
}
