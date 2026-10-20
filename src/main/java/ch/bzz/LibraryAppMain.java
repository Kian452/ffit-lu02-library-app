package ch.bzz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class LibraryAppMain {

    private static final Logger log = LoggerFactory.getLogger(LibraryAppMain.class);

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();

        Map<String, Command> commands = new LinkedHashMap<>();
        commands.put("help", new HelpCommand(commands));
        commands.put("listBooks", new ListBooksCommand(bookRepository));
        commands.put("importBooks", new ImportBooksCommand(new BookTsvReader(), bookRepository));
        commands.put("quit", new QuitCommand());

        log.info("Library app started");

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
                log.warn("Unrecognized command: '{}'", input);
                System.out.println("Command '" + input + "' was not recognized. Type 'help' to see all available commands.");
                continue;
            }

            log.debug("Executing command '{}' with argument '{}'", commandName, argument);

            try {
                command.execute(argument);
            } catch (RuntimeException e) {
                log.error("Command '{}' failed", commandName, e);
                System.out.println("Command '" + commandName + "' failed. See the log for details.");
            }

            running = !(command instanceof QuitCommand);
        }

        log.info("Library app stopped");
    }
}
