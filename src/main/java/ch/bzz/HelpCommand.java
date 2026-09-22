package ch.bzz;

import java.util.Map;

public class HelpCommand implements Command {

    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String getDescription() {
        return "Lists all available commands";
    }

    @Override
    public void execute() {
        System.out.println("Available commands:");
        commands.forEach((name, command) ->
                System.out.println("  " + name + " - " + command.getDescription()));
    }
}
