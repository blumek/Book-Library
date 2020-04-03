package pl.blumek.book_library.application.spring_app.command;

import com.google.common.io.Files;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Command(
        name = "datasource",
        description = "Select data source for application"
)
public class DataSourceCommand {
    private File jsonFile;

    @Option(names = { "-gb", "--googleBooks" })
    private boolean useGoogleBooks;

    @Option(names = { "-jf", "--jsonFile" })
    void setJsonFile(File file) throws IOException {
        if (!Files.getFileExtension(file.getName()).equals("json"))
            throw new IOException("Provided file is not in JSON format");

        this.jsonFile = file;
    }

    public Optional<File> getJsonFile() {
        return Optional.ofNullable(jsonFile);
    }

    public boolean useGoogleBooks() {
        return useGoogleBooks;
    }
}
