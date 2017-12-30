package jcsokolow.tank.bo;

public class CommandError extends Error {

    CommandErrorType errorType;

    public CommandError(CommandErrorType errorType) {
        this.errorType = errorType;
    }

    public CommandErrorType getErrorType() {
        return errorType;
    }
}
