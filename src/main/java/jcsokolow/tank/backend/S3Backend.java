package jcsokolow.tank.backend;

import java.io.IOException;
import java.io.InputStream;

public class S3Backend implements Backend {


    private final String s3BucketName;

    public S3Backend(String s3BucketName) {
        this.s3BucketName = s3BucketName;
    }

    @Override
    public boolean hasStream(String id) throws IOException {
        return false;
    }

    public InputStream getStream(String id) {
        return null;
    }

    public void putStream(String id, InputStream input) {

    }
}
