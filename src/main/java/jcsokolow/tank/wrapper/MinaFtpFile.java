package jcsokolow.tank.wrapper;

import jcsokolow.tank.bo.Stats;
import jcsokolow.tank.filesystem.FileSystem;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class MinaFtpFile implements FtpFile {

    private final User user;
    private final String path;
    private final Stats stats;
    private final FileSystem fileSystem;

    MinaFtpFile(String path, FileSystem fileSystem, User user) {
        this.path = path;
        this.user = user;
        this.fileSystem = fileSystem;
        this.stats = fileSystem.stat(path);
    }

    @Override
    public String getAbsolutePath() {
        return path;
    }

    @Override
    public String getName() {
        String[] parts = path.split("/");

        if(parts.length > 0){
            return parts[parts.length - 1];
        }

        return "/";
    }

    @Override
    public boolean isHidden() {
        return getName().startsWith(".");
    }

    @Override
    public boolean isDirectory() {
        return stats.isDirectory();
    }

    @Override
    public boolean isFile() {
        return stats.isFile();
    }

    @Override
    public boolean doesExist() {
        return stats != null;
    }

    @Override
    public boolean isReadable() {
        return stats.isReadable();
    }

    @Override
    public boolean isWritable() {
        return stats.isWritable();
    }

    @Override
    public boolean isRemovable() {
        return !"/".equals(path) && stats.isWritable();
    }

    @Override
    public String getOwnerName() {
        return stats.getOwner();
    }

    @Override
    public String getGroupName() {
        return stats.getGroup();
    }

    @Override
    public int getLinkCount() {
        return stats.getNLinks();
    }

    @Override
    public long getLastModified() {
        return stats.getModifyTime();
    }

    @Override
    public boolean setLastModified(long time) {
        return fileSystem.setModifyTime(path, time);
    }

    @Override
    public long getSize() {
        return stats.getSize();
    }

    @Override
    public Object getPhysicalFile() {
        return null;
    }

    @Override
    public boolean mkdir() {
        try {
            fileSystem.mkdir(path);
            return true;
        } catch (IOException e){
            return false;
        }
    }

    @Override
    public boolean delete() {
        try {
            fileSystem.unlink(path);
            return true;
        } catch(IOException e){
            return false;
        }
    }

    @Override
    public boolean move(FtpFile ftpFile) {
        try {
            fileSystem.rename(path, ftpFile.getAbsolutePath());
            return true;
        } catch(IOException e){
            return false;
        }
    }

    @Override
    public List<? extends FtpFile> listFiles() {

        List<FtpFile> children = new LinkedList<>();

        for(String name: fileSystem.list()){
            children.add(new MinaFtpFile(path + "/" + name, fileSystem, user));
        }

        return children;
    }

    @Override
    public OutputStream createOutputStream(long l) throws IOException {
        return fileSystem.writeFile(path);
    }

    @Override
    public InputStream createInputStream(long l) throws IOException {
        return fileSystem.readFile(path);
    }
}
