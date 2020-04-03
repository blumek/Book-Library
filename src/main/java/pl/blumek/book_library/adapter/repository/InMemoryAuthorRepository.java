package pl.blumek.book_library.adapter.repository;

import com.google.common.collect.Lists;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.AuthorRepository;
import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.List;
import java.util.stream.Stream;

public final class InMemoryAuthorRepository extends InMemoryRepository<Person> implements AuthorRepository {
    public InMemoryAuthorRepository(IdGenerator idGenerator, Person... t) {
        super(idGenerator, t);
    }

    public InMemoryAuthorRepository(IdGenerator idGenerator, List<Person> t) {
        super(idGenerator, t);
    }

    public InMemoryAuthorRepository(IdGenerator idGenerator, Stream<Person> personStream) {
        super(idGenerator, personStream);
    }

    @Override
    Person entityWithAssignedId(Person person) {
        return person.toBuilder()
                .id(getId(person))
                .build();
    }

    private String getId(Person person) {
        return hasId(person) ? person.getId() : generateId();
    }

    private boolean hasId(Person person) {
        return person.getId() != null && !person.getId().isEmpty();
    }

    @Override
    protected String getEntityId(Person person) {
        return person.getId();
    }

    @Override
    public List<Person> findAll() {
        return Lists.newArrayList(inMemoryDb.values());
    }
}
