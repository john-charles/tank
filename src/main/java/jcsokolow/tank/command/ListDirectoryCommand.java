package jcsokolow.tank.command;

import jcsokolow.tank.bo.CommandArguments;
import jcsokolow.tank.bo.CommandErrorType;
import jcsokolow.tank.bo.Stats;
import jcsokolow.tank.filesystem.FileDoesNotExistException;
import jcsokolow.tank.filesystem.FileIsWrongTypeException;
import jcsokolow.tank.filesystem.FileSystem;
import jcsokolow.tank.bo.DirectoryListing;

import java.util.*;

public class ListDirectoryCommand implements Command {

    private List<FileSystem> fileSystems;

    public ListDirectoryCommand(List<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
    }

    public void execute(CommandArguments arguments) {

        String path = arguments.getSourcePath();
        DirectoryListing directoryListing = new DirectoryListing();

        for(FileSystem fs: fileSystems){

            try {

                Map<String, Stats> rawListing = fs.listDir(path);

                if(rawListing != null) {

                    for (String key : rawListing.keySet()) {

                        directoryListing.add(fs.getName(), key, rawListing.get(key));

                    }
                }

            } catch (FileDoesNotExistException | FileIsWrongTypeException e) {
                e.printStackTrace();
            }
        }


        directoryListing.print();

    }

    @Override
    public void printErrorMessage(CommandErrorType errorType) {
        switch (errorType) {
            case MISSING_SOURCE_PATH:
                System.err.println("Error, please specify a path to list!");
        }
    }

}
