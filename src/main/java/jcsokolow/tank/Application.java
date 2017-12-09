package jcsokolow.tank;

import jcsokolow.tank.backend.Backend;
import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.service.BackendService;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        Configuration configuration = new Configuration();
        BackendService backendService = new BackendService(configuration);





    }
}
