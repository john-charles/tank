package jcsokolow.tank.bo;

public class CommandArguments {

    String[] args;

    public CommandArguments(String[] args){
        this.args = args;
    }


    public String getCommandName(){
        try {
            return args[0];
        } catch (IndexOutOfBoundsException e){
            return null;
        }
    }

    public String getSourcePath(){
        return args[1];
    }

}
