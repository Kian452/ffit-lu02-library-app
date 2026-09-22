package ch.bzz;

public class QuitCommand implements Command {

    @Override
    public String getDescription() {
        return "Exits the application";
    }

    @Override
    public void execute() {
        System.out.println("Goodbye!");
    }
}
