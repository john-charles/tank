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
    private final String SYNCED = "synched";
    private final String OLDER = "older";
    private final String NEWER = "newer";


    public ListDirectoryCommand(List<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
    }

    private void printPadded(String words, int pad){
        StringBuilder builder = new StringBuilder(words);

        while(builder.length() < pad){
            builder.append(" ");
        }

        System.out.print(builder.toString());
    }

    public void execute(CommandArguments arguments) {

        Set<String> children = new HashSet<>();
        String path = arguments.getSourcePath();

        for(FileSystem fs: fileSystems) {
            children.addAll(fs.list(path));
        }

        int maxFileNameLength = 0;

        for(String fileName: children){
            if(fileName.length() > maxFileNameLength){
                maxFileNameLength = fileName.length();
            }
        }

        printPadded("File Name", maxFileNameLength);
        printColumnSep();

        for(int i = 0; i < fileSystems.size(); i++){

            String fsName = fileSystems.get(i).getName();
            printPadded(fsName, Math.max(fsName.length(), SYNCED.length()));
            if(i < fileSystems.size()){
                printColumnSep();
            }

        }

        printRowEnd();





    }

    private void printRowEnd() {
        System.out.println();
    }

    private void printColumnSep() {
        System.out.println(" | ");
    }

    @Override
    public void printErrorMessage(CommandErrorType errorType) {
        switch (errorType) {
            case MISSING_SOURCE_PATH:
                System.err.println("Error, please specify a path to list!");
        }
    }

}
