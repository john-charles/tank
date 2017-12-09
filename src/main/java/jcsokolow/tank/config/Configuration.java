package jcsokolow.tank.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class Configuration {

    private Map<String, Object> conf;

    @SuppressWarnings("unchecked")
    public Configuration() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        String home = System.getProperty("user.home");
        File cfgFile = new File(home, ".tank-config.json");
        conf = mapper.readValue(cfgFile, Map.class);
    }


}
