package ch.bzz;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class LibraryAppMain {

    public static void main(String[] args) {
        Map<String, Command> commands = new LinkedHashMap<>();
        commands.put("help", new HelpCommand(commands));
        commands.put("quit", new QuitCommand());

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            Command command = commands.get(input);

            if (command == null) {
                System.out.println("Command '" + input + "' was not recognized. Type 'help' to see all available commands.");
                continue;
            }

            command.execute();
            running = !(command instanceof QuitCommand);
        }
    }
}
