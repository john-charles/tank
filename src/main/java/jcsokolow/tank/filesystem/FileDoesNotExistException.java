package jcsokolow.tank.filesystem;

public class FileDoesNotExistException extends Throwable {
    public FileDoesNotExistException(String s) {
        super(s);
    }
}
