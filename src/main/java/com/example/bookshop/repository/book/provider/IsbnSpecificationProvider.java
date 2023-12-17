package com.example.bookshop.repository.book.provider;

import com.example.bookshop.model.Book;
import com.example.bookshop.repository.specification.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String ISBN_KEY = "isbn";

    @Override
    public String getFieldName() {
        return ISBN_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get(ISBN_KEY).in(Arrays.stream(params).toArray());
    }
}
