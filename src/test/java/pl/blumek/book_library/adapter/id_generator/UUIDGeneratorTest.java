package pl.blumek.book_library.adapter.id_generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UUIDGeneratorTest {
    private UUIDGenerator generator;

    @BeforeEach
    void setUp() {
        generator = UUIDGenerator.create();
    }

    @Test
    void generateTest_ShouldReturnNotEmptyString() {
        String generatedId = generator.generate();
        assertNotNull(generatedId);
        assertFalse(generatedId.isEmpty());
    }
}