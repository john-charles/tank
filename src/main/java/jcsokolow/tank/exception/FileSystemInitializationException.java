package jcsokolow.tank.exception;

public class FileSystemInitializationException extends Exception {

    public FileSystemInitializationException(String message, Throwable inner){
        super(message, inner);
    }
}
