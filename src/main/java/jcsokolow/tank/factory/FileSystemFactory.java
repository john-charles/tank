package jcsokolow.tank.factory;

import jcsokolow.tank.filesystem.FileSystem;

import java.util.Map;

public class FileSystemFactory {

    BackendFactory backendFactory;

    public FileSystemFactory(BackendFactory backendFactory){
        this.backendFactory = backendFactory;
    }

    public FileSystem buildFileSystem(Map<String, Object> config){
        return null;
    }
}
