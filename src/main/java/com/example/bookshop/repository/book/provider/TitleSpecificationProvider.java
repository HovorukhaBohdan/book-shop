package com.example.bookshop.repository.book.provider;

import com.example.bookshop.model.Book;
import com.example.bookshop.repository.specification.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    private static final String TITLE_KEY = "title";

    @Override
    public String getFieldName() {
        return TITLE_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get(TITLE_KEY).in(Arrays.stream(params).toArray());
    }
}
