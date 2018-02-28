package jcsokolow.tank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jcsokolow.tank.backend.AESEncryptionBackend;
import jcsokolow.tank.backend.Backend;
import jcsokolow.tank.backend.S3Backend;
import jcsokolow.tank.exception.FileSystemInitializationException;
import jcsokolow.tank.filesystem.CompositionFileSystem;
import jcsokolow.tank.filesystem.FileSystem;
import jcsokolow.tank.filesystem.LocalFileSystem;
import jcsokolow.tank.filesystem.SFTPFileSystem;
import jcsokolow.tank.type.EasyMap;

import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class Configuration {

    private EasyMap conf;

    public Configuration() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String home = System.getProperty("user.home");
        File cfgFile = new File(home, ".tank-config.json");
        conf = mapper.readValue(cfgFile, EasyMap.class);
    }

    public String getOverlay() {
        return (String) conf.get("overlay");
    }

    public List<FileSystem> getFileSystems() throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IOException, FileSystemInitializationException {

        List<FileSystem> fileSystems = new LinkedList<>();
        List<EasyMap> fsList = conf.getListOfMaps("filesystems");

        for (EasyMap fsDesc : fsList) {
            fileSystems.add(getFileSystem(fsDesc));
        }


        return fileSystems;

    }

    private FileSystem getFileSystem(EasyMap fsDesc) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException, FileSystemInitializationException {

        FileSystem fileSystem = null;
        String type = (String) fsDesc.get("type");
        String name = (String) fsDesc.get("name");

        switch (type) {

            case "local":
                String root;
                Boolean useOverlay = fsDesc.getBool("overlayRoot");

                if (useOverlay) {
                    root = getOverlay();
                } else {
                    root = fsDesc.getString("root");
                }

                fileSystem = new LocalFileSystem(root);

                break;

            case "composition":

                Backend backend = getBackend(fsDesc.getMap("backend"));
                fileSystem = new CompositionFileSystem(backend);
                break;

            case "sftp":

                String remoteHost = fsDesc.getString("remoteHost");
                String remoteUser = fsDesc.getString("remoteUser");
                String remoteRoot = fsDesc.getString("remoteRoot");

                fileSystem = new SFTPFileSystem(remoteHost, remoteUser, remoteRoot);

        }

        if (fileSystem != null) {
            fileSystem.setName(name);
        }

        return fileSystem;
    }


    private Backend getBackend(EasyMap bkndDesc) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {

        Backend backend = null;
        String type = bkndDesc.getString("type");

        switch (type) {

            case "encryption":

                String password = bkndDesc.getString("password");
                Backend innerBackend = getBackend(bkndDesc.getMap("backend"));
                backend = new AESEncryptionBackend(innerBackend, password);

                break;

            case "s3":

                String s3BucketName = bkndDesc.getString("s3bucketName");
                backend = new S3Backend(s3BucketName);

                break;
        }

        return backend;
    }

}
