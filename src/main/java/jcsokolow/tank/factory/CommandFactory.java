package jcsokolow.tank.factory;

import jcsokolow.tank.command.ListDirectoryCommand;
import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.filesystem.FileSystem;

import java.util.List;
import java.util.Map;

public class CommandFactory {

    private final List<FileSystem> fileSystems;


    public CommandFactory(List<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
    }

    public ListDirectoryCommand getCommand(String commandName){

        if(commandName.equals("list")){
            return new ListDirectoryCommand(fileSystems);
        }

        return null;
    }
}
