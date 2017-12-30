package jcsokolow.tank.bo;

public class CommandArguments {

    String[] args;

    public CommandArguments(String[] args){
        this.args = args;
    }


    public String getCommandName() {
        try {
            return args[0];
        } catch (IndexOutOfBoundsException e){
            throw new CommandError(CommandErrorType.MISSING_COMMAND_NAME);
        }
    }

    public String getSourcePath(){
        try {
            return args[1];
        } catch (IndexOutOfBoundsException e){
            throw new CommandError(CommandErrorType.MISSING_SOURCE_PATH);
        }
    }

}
