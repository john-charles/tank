package jcsokolow.tank.filesystem;

import jcsokolow.tank.bo.Stats;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

abstract public class FileSystem {

    String name;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Map<String, Stats> listDir(String path) throws FileDoesNotExistException, FileIsWrongTypeException;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract void shutdown();

    public abstract Stats stat(String path);

    public abstract boolean setModifyTime(String path, long time);

    public abstract void mkdir(String path) throws IOException;

    public abstract void unlink(String path) throws IOException;

    public abstract void rename(String src, String dest) throws IOException;

    public abstract List<String> list();

    public abstract OutputStream writeFile(String path);

    public abstract InputStream readFile(String path);
}
