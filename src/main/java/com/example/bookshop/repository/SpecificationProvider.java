package com.example.bookshop.repository;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getFieldName();

    Specification<T> getSpecification(String[] params);
}
