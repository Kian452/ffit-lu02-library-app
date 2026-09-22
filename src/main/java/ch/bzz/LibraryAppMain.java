package ch.bzz;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class LibraryAppMain {

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();

        Map<String, Command> commands = new LinkedHashMap<>();
        commands.put("help", new HelpCommand(commands));
        commands.put("listBooks", new ListBooksCommand(bookRepository));
        commands.put("importBooks", new ImportBooksCommand(bookRepository));
        commands.put("quit", new QuitCommand());

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("\\s+", 2);
            String commandName = parts[0];
            String argument = parts.length > 1 ? parts[1] : null;

            Command command = commands.get(commandName);

            if (command == null) {
                System.out.println("Command '" + input + "' was not recognized. Type 'help' to see all available commands.");
                continue;
            }

            command.execute(argument);
            running = !(command instanceof QuitCommand);
        }
    }
}
