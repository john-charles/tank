package jcsokolow.tank.filesystem;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntrySelector;
import jcsokolow.tank.bo.Stats;
import jcsokolow.tank.exception.FileSystemInitializationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class SFTPFileSystem extends FileSystem {

    private JSch jsch;
    private Session session;
    private ChannelSftp channel;
    private String remoteHost;
    private String remoteUser;
    private String remoteRoot;

    public SFTPFileSystem(String remoteHost, String remoteUser, String remoteRoot) throws FileSystemInitializationException {
        super();
        this.jsch = new JSch();
        //TODO: Make this configurable
        try {
            this.jsch.addIdentity("~/.ssh/id_rsa");
        } catch (JSchException e) {
            throw new FileSystemInitializationException("Failed to add private key", e);
        }
        this.remoteHost = remoteHost;
        this.remoteUser = remoteUser;
        this.remoteRoot = remoteRoot;
        this.openConnection();
    }

    private void openConnection() throws FileSystemInitializationException {
        try {

            this.session = jsch.getSession(remoteUser, remoteHost, 22);
            this.session.setConfig("StrictHostKeyChecking", "no");
            this.session.connect();
            this.channel = (ChannelSftp) this.session.openChannel("sftp");
            this.channel.connect();
            this.channel.cd(remoteRoot);

        } catch (JSchException e) {
            throw new FileSystemInitializationException("Failed to create ssh session", e);
        } catch (SftpException e) {
            throw new FileSystemInitializationException("Failed to CD into remote root", e);
        }

    }

    @Override
    Stats getStat(String path) {
        return null;
    }

    @Override
    public Map<String, Stats> listDir(String path) {

        Map<String, Stats> results = new HashMap<>();

        try {

            channel.ls("./" + path, entry -> {

                String name = entry.getFilename();
                SftpATTRS attrs = entry.getAttrs();

                Stats stats = new Stats();

                stats.setMode(attrs.getFlags());
                stats.setSize(attrs.getSize());
                stats.setModifyTime(attrs.getMTime());
                stats.setChangeTime(attrs.getMTime());
                stats.setCreateTime(attrs.getMTime());
                stats.setAccessTime(attrs.getATime());

                results.put(name, stats);

                return LsEntrySelector.CONTINUE;
            });

        } catch (SftpException e) {
            e.printStackTrace();
        }

        return results;
    }
}
