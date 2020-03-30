package pl.blumek.book_library.adapter.repository;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.blumek.book_library.domain.entity.Person;
import pl.blumek.book_library.domain.port.IdGenerator;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InMemoryAuthorRepositoryTest {
    private static final String GENERATED_ID = "GENERATED_ID";
    private static final String SECOND_AUTHOR_ID = "SECOND_AUTHOR_ID";
    private static final String FIRST_AUTHOR_ID = "FIRST_AUTHOR_ID";

    private IdGenerator idGenerator;
    private InMemoryAuthorRepository repository;

    private Person firstAuthor;
    private Person secondAuthor;
    private Person thirdAuthor;

    @BeforeEach
    void setUp() {
        firstAuthor = Person.builder()
                .id(FIRST_AUTHOR_ID)
                .build();

        secondAuthor = Person.builder()
                .id(SECOND_AUTHOR_ID)
                .build();

        thirdAuthor = Person.builder()
                .build();

        idGenerator = mock(IdGenerator.class);
        repository = new InMemoryAuthorRepository(idGenerator, firstAuthor, secondAuthor);
    }

    @Test
    void varargsConstructorTest_TwoAuthors() {
        repository = new InMemoryAuthorRepository(idGenerator, firstAuthor, secondAuthor);

        List<Person> authors = repository.findAll();

        assertEquals(Sets.newHashSet(firstAuthor, secondAuthor), Sets.newHashSet(authors));
    }

    @Test
    void listConstructorTest_TwoAuthors() {
        repository = new InMemoryAuthorRepository(idGenerator, Lists.newArrayList(firstAuthor, secondAuthor));

        List<Person> authors = repository.findAll();

        assertEquals(Sets.newHashSet(firstAuthor, secondAuthor), Sets.newHashSet(authors));
    }

    @Test
    void streamConstructorTest_TwoAuthors() {
        repository = new InMemoryAuthorRepository(idGenerator, Stream.of(firstAuthor, secondAuthor));

        List<Person> authors = repository.findAll();

        assertEquals(Sets.newHashSet(firstAuthor, secondAuthor), Sets.newHashSet(authors));
    }

    @Test
    void constructorTest_AuthorWithNoId_ShouldGenerateOne() {
        when(idGenerator.generate())
                .thenReturn(GENERATED_ID);

        repository = new InMemoryAuthorRepository(idGenerator, firstAuthor, secondAuthor, thirdAuthor);

        List<Person> authors = repository.findAll();

        Person expectedAuthor = thirdAuthor.toBuilder()
                .id(GENERATED_ID)
                .build();

        assertTrue(authors.contains(expectedAuthor));
    }

    @Test
    void findAllTest_EmptyRepository() {
        repository = new InMemoryAuthorRepository(idGenerator);
        assertEquals(Lists.newArrayList(), repository.findAll());
    }

    @Test
    void findAllTest_TwoAuthorsInRepository() {
        List<Person> authors = repository.findAll();
        assertEquals(Sets.newHashSet(firstAuthor, secondAuthor), Sets.newHashSet(authors));
    }
}