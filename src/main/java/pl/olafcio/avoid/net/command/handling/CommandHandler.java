package pl.olafcio.avoid.net.command.handling;

@FunctionalInterface
public interface CommandHandler {
    void run(Usage usage);
}
