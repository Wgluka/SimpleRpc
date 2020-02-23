package com.wgluka.connect.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import java.io.IOException;

public class ZookeeperTest {
    @Test
    public void testPublish() throws IOException, InterruptedException, KeeperException {
        ZookeeperConnector.publish();
        Thread.sleep(3000);
    }

    @Test
    public void testWatcher() throws InterruptedException, IOException, KeeperException {
        ZookeeperConnector.watch();
        Thread.sleep(600000);
    }
}
