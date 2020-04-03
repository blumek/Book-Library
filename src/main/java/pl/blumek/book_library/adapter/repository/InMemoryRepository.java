package pl.blumek.book_library.adapter.repository;

import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

abstract class InMemoryRepository<T> {
    final Map<String, T> inMemoryDb;
    final IdGenerator idGenerator;

    @SafeVarargs
    public InMemoryRepository(IdGenerator idGenerator, T... t) {
        this(idGenerator, Arrays.stream(t));
    }

    public InMemoryRepository(IdGenerator idGenerator, List<T> t) {
        this(idGenerator, t.stream());
    }

    public InMemoryRepository(IdGenerator idGenerator, Stream<T> tStream) {
        this.idGenerator = idGenerator;
        this.inMemoryDb = tStream
                .map(this::entityWithAssignedId)
                .collect(toMap(this::getEntityId, t -> t));
    }

    abstract T entityWithAssignedId(T t);

    abstract String getEntityId(T t);

    String generateId() {
        return idGenerator.generate();
    }
}
