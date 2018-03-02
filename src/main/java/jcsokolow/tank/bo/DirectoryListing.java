package jcsokolow.tank.bo;

import java.util.*;

public class DirectoryListing {

    private static final String FILE_NAME = "File Names";

    private Set<String> fileNames;
    private Map<String, Integer> fsNamesSize;
    private Map<String, Map<String, Stats>> data;

    public DirectoryListing() {
        this.data = new HashMap<>();
        this.fileNames = new HashSet<>();
        this.fsNamesSize = new HashMap<>();
    }

    public void add(String fsName, String fileName, Stats stats){
        int size;

        this.data.computeIfAbsent(fileName, k -> new HashMap<>())
                .put(fsName, stats);

        size = this.fsNamesSize.getOrDefault(fsName, 0);
        if(size < fsName.length()){
            this.fsNamesSize.put(fsName, fsName.length());
        }

        fileNames.add(fileName);

    }

    private String pad(String value, int size){

        StringBuilder valueBuilder = new StringBuilder(value);

        while(valueBuilder.length() < size){
            valueBuilder.append(" ");
        }

        return valueBuilder.toString();
    }

    public int reduce(Collection<String> strings, int min){
        int size = min;

        for(String string: strings){
            if(size < string.length()){
                size = string.length();
            }
        }

        return size;

    }

    public void print() {

        int fileNameSize = reduce(fileNames, FILE_NAME.length());
        System.out.print(pad(FILE_NAME, fileNameSize));
        System.out.print(" | ");

        for(String fsName: data.keySet()){
            System.out.print(pad(fsName, fsNamesSize.get(fsName)));
            System.out.print(" | ");
        }

        System.out.println();

        for(String fileName: fileNames){

            System.out.print(pad(fileName, fileNameSize));
            System.out.print(" | ");


            Map<String, Stats> info = data.get(fileName);
            Stats localStats = info.get("Local");



        }


    }
}
