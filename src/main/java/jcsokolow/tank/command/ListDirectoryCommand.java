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

    private final String OLDER = "older";
    private final String NEWER = "newer";
    private final String SYNCED = "synched";
    private final String MISSING = "missing";

    private final FileSystem local;
    private final List<FileSystem> fileSystems;



    public ListDirectoryCommand(FileSystem local, List<FileSystem> fileSystems) {
        this.local = local;
        this.fileSystems = fileSystems;
    }

    private String join(String root, String name){
        return root + '/' + name;
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
            printPadded(fsName, Math.max(fsName.length(), MISSING.length()));
            if(i < fileSystems.size()){
                printColumnSep();
            }

        }

        printRowEnd();


        for(String fileName: children){

            printPadded(fileName, maxFileNameLength);

            Stats localStats = local.stat(join(path, fileName));
            // TODO: Print mtime for local

            for(int i = 0; i < fileSystems.size(); i++){

                String message= "";
                FileSystem fs = fileSystems.get(i);
                Stats stats = fs.stat(join(path, fileName));

                if(stats == null){
                    message = MISSING;
                } else if(stats.getModifyTime() > localStats.getModifyTime()){
                    message = NEWER;
                } else if(stats.getModifyTime() < localStats.getModifyTime()){
                    message = OLDER;
                } else {
                    message = SYNCED;
                }

                printPadded(message, Math.max(fs.getName().length(), MISSING.length()));
                if(i < fileSystems.size()){
                    printColumnSep();
                }

            }

            printRowEnd();

        }

    }

    private void printPadded(String words, int pad){
        StringBuilder builder = new StringBuilder(words);

        while(builder.length() < pad){
            builder.append(" ");
        }

        System.out.print(builder.toString());
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
