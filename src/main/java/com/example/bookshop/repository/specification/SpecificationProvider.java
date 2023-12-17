package com.example.bookshop.repository.specification;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationProvider<T> {
    String getFieldName();

    Specification<T> getSpecification(String[] params);
}
