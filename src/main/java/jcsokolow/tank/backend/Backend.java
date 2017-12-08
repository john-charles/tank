package jcsokolow.tank.backend;

import java.io.IOException;
import java.io.InputStream;

public interface Backend {

    InputStream getStream(String id) throws IOException;
    void putStream(String id, InputStream input) throws IOException;

}
