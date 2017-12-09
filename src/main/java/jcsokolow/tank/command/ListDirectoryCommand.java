package jcsokolow.tank.command;

import jcsokolow.tank.bo.CommandArguments;
import jcsokolow.tank.bo.Stats;
import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.filesystem.FileSystem;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ListDirectoryCommand implements Command {

    Map<String, FileSystem> fileSystems;

    public ListDirectoryCommand(Configuration configuration, Map<String, FileSystem> fileSystems) {
        this.fileSystems = fileSystems;
    }

    public void execute(CommandArguments arguments) {

        String path = arguments.getSourcePath();

        List<String> fsNames = new LinkedList<>();
        Map<String, Map<String, Stats>> listings = new LinkedHashMap<>();

        for (Map.Entry<String, FileSystem> fs : fileSystems.entrySet()) {
            Map<String, Stats> results = fs.getValue().listDir(path);

            for (Map.Entry<String, Stats> result : results.entrySet()) {
                Map<String, Stats> entry = listings.computeIfAbsent(result.getKey(), k -> new LinkedHashMap<>());
                entry.put(fs.getKey(), result.getValue());
            }

            fsNames.add(fs.getKey());
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

}
