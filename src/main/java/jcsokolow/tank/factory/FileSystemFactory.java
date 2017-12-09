package jcsokolow.tank.factory;

import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.filesystem.FileSystem;

import java.util.Map;

public class FileSystemFactory {

    BackendFactory backendFactory;

    public FileSystemFactory(Configuration configuration, BackendFactory backendFactory){
        this.backendFactory = backendFactory;
    }

    public FileSystem buildFileSystem(Map<String, Object> config){
        return null;
    }

    public Map<String, FileSystem> getAllFileSystems() {
        return null;
    }
}
