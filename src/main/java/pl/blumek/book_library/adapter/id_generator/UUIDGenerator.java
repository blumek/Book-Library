package pl.blumek.book_library.adapter.id_generator;

import lombok.NoArgsConstructor;
import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.UUID;

@NoArgsConstructor(staticName = "create")
public class UUIDGenerator implements IdGenerator {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
