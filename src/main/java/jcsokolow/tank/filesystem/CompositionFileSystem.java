package jcsokolow.tank.filesystem;

import jcsokolow.tank.backend.Backend;
import jcsokolow.tank.bo.Stats;

import java.util.Map;

public class CompositionFileSystem extends FileSystem {

    Backend backend;

    public CompositionFileSystem(Backend backend) {
        super();
        this.backend = backend;
    }

    @Override
    Stats getStat(String path) {
        return null;
    }

    @Override
    public Map<String, Stats> listDir(String path) {
        return null;
    }
}
