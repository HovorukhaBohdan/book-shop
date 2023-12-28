package com.example.bookshop.repository.book.provider;

import com.example.bookshop.model.Book;
import com.example.bookshop.repository.specification.SpecificationProvider;
import java.util.Arrays;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategoriesSpecificationProvider implements SpecificationProvider<Book> {
    private static final String CATEGORY_KEY = "category";

    @Override
    public String getFieldName() {
        return CATEGORY_KEY;
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get(CATEGORY_KEY).in(Arrays.stream(params).toArray());
    }
}
