package jcsokolow.tank.filesystem;

import jcsokolow.tank.bo.Stats;

import java.util.Map;

public interface FileSystem {

    Stats getStat(String path);
    Map<String, Stats> listDir(String path);

}
