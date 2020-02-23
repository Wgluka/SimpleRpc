package com.wgluka.connect.zookeeper;

import com.wgluka.connect.zookeeper.container.DataContainer;
import com.wgluka.connect.zookeeper.watcher.ChildrenWatcher;
import com.wgluka.connect.zookeeper.watcher.ZookeeperWatcher;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class ZookeeperConnector {
    private static String address;

    private static String monitorPath;

    public static void connect() throws IOException, KeeperException, InterruptedException {
        init();
        ZooKeeper zooKeeper = new ZooKeeper(address, 15000, new ZookeeperWatcher());
        DataContainer container = new DataContainer();
        List<String> children = zooKeeper.getChildren(monitorPath, new ChildrenWatcher(monitorPath, zooKeeper,
                container));
        System.out.println(String.join(",", children));
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
}
