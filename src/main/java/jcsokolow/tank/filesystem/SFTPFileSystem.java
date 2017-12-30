package jcsokolow.tank.filesystem;

import jcsokolow.tank.bo.Stats;

import java.util.HashMap;
import java.util.Map;

public class SFTPFileSystem extends FileSystem {

    String remoteHost;
    String remoteUser;
    String remoteRoot;

    public SFTPFileSystem(String remoteHost, String remoteUser, String remoteRoot) {
        super();
        this.remoteHost = remoteHost;
        this.remoteUser = remoteUser;
        this.remoteRoot = remoteRoot;
    }

    @Override
    Stats getStat(String path) {
        return null;
    }

    @Override
    public Map<String, Stats> listDir(String path) {
        return new HashMap<>();
    }
}
