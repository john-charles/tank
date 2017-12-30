package jcsokolow.tank.command;

import jcsokolow.tank.bo.CommandArguments;
import jcsokolow.tank.bo.CommandErrorType;

public interface Command {

    public void execute(CommandArguments arguments);

    void printErrorMessage(CommandErrorType errorType);
}
