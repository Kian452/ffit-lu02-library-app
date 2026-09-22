package ch.bzz;

public interface Command {

    String getDescription();

    void execute();
}
