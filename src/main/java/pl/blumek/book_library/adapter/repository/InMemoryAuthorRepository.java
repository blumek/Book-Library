package pl.blumek.book_library.adapter.repository;

import com.google.common.collect.Lists;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.AuthorRepository;
import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public final class InMemoryAuthorRepository implements AuthorRepository {
    private final Map<String, Person> inMemoryDb;
    private final IdGenerator idGenerator;

    public InMemoryAuthorRepository(IdGenerator idGenerator, Person... people) {
        this(idGenerator, Arrays.stream(people));
    }

    public InMemoryAuthorRepository(IdGenerator idGenerator, List<Person> people) {
        this(idGenerator, people.stream());
    }

    public InMemoryAuthorRepository(IdGenerator idGenerator, Stream<Person> peopleStream) {
        this.idGenerator = idGenerator;
        this.inMemoryDb = peopleStream
                .map(this::authorWithAssignedId)
                .collect(toMap(Person::getId, author -> author));
    }

    private Person authorWithAssignedId(Person author) {
        return author.toBuilder()
                .id(getId(author))
                .build();
    }

    private String getId(Person person) {
        return hasId(person) ? person.getId() : idGenerator.generate();
    }

    private boolean hasId(Person person) {
        return person.getId() != null && !person.getId().isEmpty();
    }

    @Override
    public List<Person> findAll() {
        return Lists.newArrayList(inMemoryDb.values());
    }
}
