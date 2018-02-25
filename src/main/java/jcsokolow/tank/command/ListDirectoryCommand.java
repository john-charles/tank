package jcsokolow.tank.command;

import jcsokolow.tank.bo.CommandArguments;
import jcsokolow.tank.bo.CommandErrorType;
import jcsokolow.tank.bo.Stats;
import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.filesystem.FileDoesNotExistException;
import jcsokolow.tank.filesystem.FileIsWrongTypeException;
import jcsokolow.tank.filesystem.FileSystem;

import java.util.*;

public class ListDirectoryCommand implements Command {

    private List<FileSystem> fileSystems;

    public ListDirectoryCommand(List<FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
    }

    public void execute(CommandArguments arguments) {

        String path = arguments.getSourcePath();

        List<String> fsNames = new LinkedList<>();
        Map<String, Map<String, Stats>> listings = new LinkedHashMap<>();

        for (FileSystem fs : fileSystems) {
            Map<String, Stats> results = null;

            try {

                results = fs.listDir(path);

                if(results != null) {

                    Set<String> keys = results.keySet();


                    for (String key : keys) {
                        Map<String, Stats> entry = listings.computeIfAbsent(key, k -> new LinkedHashMap<>());
                        entry.put(fs.getName(), results.get(key));
                    }

                }


            } catch (FileDoesNotExistException | FileIsWrongTypeException e) {
                e.printStackTrace();
            }


            fsNames.add(fs.getName());
        }

        System.out.print("File Name");
        for (String fsName : fsNames) {
            System.out.print(" | " + fsName);
        }
        System.out.println();

        for (Map.Entry<String, Map<String, Stats>> listing : listings.entrySet()) {

            String fileName = listing.getKey();
            System.out.print(fileName);

            Stats localStats = listing.getValue().remove(fsNames.get(0));

            System.out.print(" | " + localStats.getModifyTime());

            for (String fsName : fsNames) {
                Stats stats = listing.getValue().get(fsName);

                if (stats != null && stats != localStats) {

                    if (stats.getModifyTime() > localStats.getModifyTime()) {
                        System.out.print(" | newer");
                    } else if (stats.getModifyTime() < localStats.getModifyTime()) {
                        System.out.print(" | older");
                    } else if (stats.getModifyTime() == localStats.getModifyTime()) {
                        System.out.print(" | in sync");
                    }

                } else if (stats == null) {

                    System.out.print(" | missing");
                }
            }

            System.out.println();
        }
    }

    @Override
    public void printErrorMessage(CommandErrorType errorType) {
        switch (errorType) {
            case MISSING_SOURCE_PATH:
                System.err.println("Error, please specify a path to list!");
        }
    }

}
