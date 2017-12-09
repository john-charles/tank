package jcsokolow.tank.factory;

import jcsokolow.tank.command.ListDirectoryCommand;
import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.filesystem.FileSystem;

import java.util.Map;

public class CommandFactory {

    Configuration configuration;
    Map<String, FileSystem> fileSystems;

    public CommandFactory(Configuration configuration, FileSystemFactory fileSystemFactory) {

        this.configuration = configuration;
        fileSystems = fileSystemFactory.getAllFileSystems();
    }

    public ListDirectoryCommand getCommand(String commandName){

        if(commandName.equals("list")){
            return new ListDirectoryCommand(configuration, fileSystems);
        }

        return null;
    }
}
