package com.example.bookshop.repository.book.provider;

import com.example.bookshop.model.Book;
import com.example.bookshop.repository.SpecificationProvider;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;

public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getFieldName() {
        return "title";
    }

    @Override
    public Specification<Book> getSpecification(String[] params) {
        return (root, query, criteriaBuilder)
                -> root.get("title").in(Arrays.stream(params).toArray());
    }
}
