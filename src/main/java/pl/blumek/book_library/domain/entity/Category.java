package pl.blumek.book_library.domain.entity;


import lombok.*;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Value
public class Category {
    String id;
    String name;
    @Singular List<Category> subcategories;
}
