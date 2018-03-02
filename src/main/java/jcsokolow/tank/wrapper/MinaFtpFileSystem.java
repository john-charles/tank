package jcsokolow.tank.wrapper;

import jcsokolow.tank.filesystem.FileSystem;
import org.apache.ftpserver.ftplet.FileSystemView;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpFile;
import org.apache.ftpserver.ftplet.User;

public class MinaFtpFileSystem implements FileSystemView {

    private String cwd;
    private final User user;
    private final MinaFtpFile root;
    private final FileSystem fileSystem;

    public MinaFtpFileSystem(User user, FileSystem fileSystem) {
        this.cwd = "/";
        this.user = user;
        this.fileSystem = fileSystem;
        this.root = new MinaFtpFile("/", fileSystem, user);
    }


    @Override
    public FtpFile getHomeDirectory() throws FtpException {
        return root;
    }

    @Override
    public FtpFile getWorkingDirectory() throws FtpException {
        return new MinaFtpFile(cwd, fileSystem, user);
    }

    @Override
    public boolean changeWorkingDirectory(String path) throws FtpException {
        if(fileSystem.stat(path).isDirectory()){
            cwd = path;
        }

        return path.equals(cwd);
    }

    @Override
    public FtpFile getFile(String path) throws FtpException {
        return new MinaFtpFile(cwd + path, fileSystem, user);
    }

    @Override
    public boolean isRandomAccessible() throws FtpException {
        return false;
    }

    @Override
    public void dispose() {
        fileSystem.shutdown();
    }
}
