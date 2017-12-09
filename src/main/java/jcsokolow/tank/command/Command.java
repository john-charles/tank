package jcsokolow.tank.command;

import jcsokolow.tank.bo.CommandArguments;

public interface Command {

    public void execute(CommandArguments arguments);
}
