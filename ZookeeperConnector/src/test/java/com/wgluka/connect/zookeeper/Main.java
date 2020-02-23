package com.wgluka.connect.zookeeper;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperConnector.connect();
        Thread.sleep(600000);
    }
}
