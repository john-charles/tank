package jcsokolow.tank;

import jcsokolow.tank.backend.Backend;
import jcsokolow.tank.bo.CommandArguments;
import jcsokolow.tank.command.Command;
import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.factory.BackendFactory;
import jcsokolow.tank.factory.CommandFactory;
import jcsokolow.tank.factory.FileSystemFactory;
import jcsokolow.tank.service.BackendService;

import java.io.IOException;

public class Application {

    public static void main(String[] args) throws IOException {

        Configuration configuration = new Configuration();
        BackendFactory backendFactory = new BackendFactory(configuration);
        FileSystemFactory fileSystemFactory = new FileSystemFactory(configuration, backendFactory);
        CommandFactory commandFactory = new CommandFactory(configuration, fileSystemFactory);


        CommandArguments arguments = new CommandArguments(args);
        Command command = commandFactory.getCommand(arguments.getCommandName());

        command.execute(arguments);



    }
}
