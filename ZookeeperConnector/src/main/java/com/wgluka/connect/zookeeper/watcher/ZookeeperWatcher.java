package com.wgluka.connect.zookeeper.watcher;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

public class ZookeeperWatcher implements Watcher {
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }
}
