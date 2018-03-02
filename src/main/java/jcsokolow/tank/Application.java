package jcsokolow.tank;

import jcsokolow.tank.bo.CommandArguments;
import jcsokolow.tank.bo.CommandError;
import jcsokolow.tank.command.Command;
import jcsokolow.tank.config.Configuration;
import jcsokolow.tank.factory.CommandFactory;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Application {

    public static void main(String[] args) throws Exception {


        Configuration configuration = new Configuration();
        CommandFactory commandFactory = new CommandFactory(configuration.getFileSystems());


        CommandArguments arguments = new CommandArguments(args);
        Command command = commandFactory.getCommand(arguments.getCommandName());

        try {
            command.execute(arguments);

        } catch (CommandError error) {

            switch (error.getErrorType()) {

                case MISSING_COMMAND_NAME:
                    System.err.println("Please specify a command as the second argument");

                default:
                    command.printErrorMessage(error.getErrorType());

            }

            System.exit(1);
        }


    }
}
