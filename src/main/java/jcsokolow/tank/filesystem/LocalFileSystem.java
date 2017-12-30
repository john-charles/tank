package jcsokolow.tank.filesystem;

import jcsokolow.tank.bo.Stats;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LocalFileSystem extends FileSystem {

    File root;

    public LocalFileSystem(File root) {
        this.root = root;
    }


    public LocalFileSystem(String root) {
        this(new File(root));
    }

    @Override
    Stats getStat(String path) {
        return null;
    }

    @Override
    public Map<String, Stats> listDir(String path) throws FileDoesNotExistException, FileIsWrongTypeException {


        File actual = new File(root, path);

        if (!actual.exists()) {
            throw new FileDoesNotExistException("Error, '" + actual + "' does not exist!");
        }

        if (!actual.isDirectory()) {
            throw new FileIsWrongTypeException("Error, '" + actual + "' is not a directory!");
        }

        File[] children = actual.listFiles();
        Map<String, Stats> result = new HashMap<>();

        if (children != null) {

            for (File child : children) {

                Stats stats = new Stats();

                stats.setAccessTime(child.lastModified());
                stats.setChangeTime(child.lastModified());
                stats.setCreateTime(child.lastModified());
                stats.setModifyTime(child.lastModified());

                int mode = 0;

                if (child.isDirectory()) {
                    mode = 16384;
                } else if (child.isFile()) {
                    mode = 32768;
                }

                if (child.canRead()) {
                    mode = mode & 128;
                }

                stats.setMode(mode);
                stats.setSize(child.length());

                result.put(child.getName(), stats);

            }
        }

        return result;
    }
}
