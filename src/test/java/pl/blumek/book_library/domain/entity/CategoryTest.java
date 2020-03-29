package pl.blumek.book_library.domain.entity;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private static final String CATEGORY_ID = "CATEGORY_ID";
    private static final String CATEGORY_NAME = "CATEGORY_NAME";
    private static final String SECOND_CATEGORY_NAME = "SECOND_CATEGORY_NAME";
    private static final String THIRD_CATEGORY_NAME = "THIRD_CATEGORY_NAME";

    private Category category;
    private Category expectedCategory;

    @BeforeEach
    void setUp() {
        Category secondCategory = Category.builder()
                .name(SECOND_CATEGORY_NAME)
                .build();

        Category thirdCategory = Category.builder()
                .name(THIRD_CATEGORY_NAME)
                .build();

        category = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .subcategory(secondCategory)
                .subcategory(thirdCategory)
                .build();

        expectedCategory = Category.builder()
                .id(CATEGORY_ID)
                .name(CATEGORY_NAME)
                .subcategories(Lists.newArrayList(secondCategory, thirdCategory))
                .build();
    }

    @Test
    void equalsTest_equalCategories() {
        assertEquals(expectedCategory, category);
    }

    @Test
    void hashCodeTest_equalCategories() {
        assertEquals(expectedCategory.hashCode(), category.hashCode());
    }
}