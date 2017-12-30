package jcsokolow.tank.filesystem;

import jcsokolow.tank.bo.Stats;

import java.util.Map;

abstract public class FileSystem {

    String name;
    String type;

    abstract Stats getStat(String path);
    public abstract Map<String, Stats> listDir(String path) throws FileDoesNotExistException, FileIsWrongTypeException;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
