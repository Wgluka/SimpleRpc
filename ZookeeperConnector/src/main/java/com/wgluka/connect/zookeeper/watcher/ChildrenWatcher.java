package com.wgluka.connect.zookeeper.watcher;

import com.wgluka.connect.zookeeper.container.DataContainer;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

public class ChildrenWatcher implements Watcher {
    private String watchedPath;

    private ZooKeeper zooKeeper;

    private DataContainer container;

    public ChildrenWatcher(String watchedPath, ZooKeeper zooKeeper, DataContainer container) {
        this.watchedPath = watchedPath;
        this.zooKeeper = zooKeeper;
        this.container = container;
    }

    public void process(WatchedEvent watchedEvent) {
        try {
            List<String> datas = zooKeeper.getChildren(watchedPath, this);
            container.modifyAddressList(datas);
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(watchedEvent);
    }
}
