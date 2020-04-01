package pl.blumek.book_library.application.spring_app.command;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.Optional;

@Command(
        name = "datasource",
        description = "Select data source for application"
)
public class DataSourceCommand {
    @Option(names = { "-jf", "--jsonFile" })
    private File jsonFile;

    public Optional<File> getJsonFile() {
        return Optional.ofNullable(jsonFile);
    }
}
