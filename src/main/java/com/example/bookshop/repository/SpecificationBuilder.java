package com.example.bookshop.repository;

import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface SpecificationBuilder<T> {
    Specification<T> build(Map<String, String[]> searchParameters);
}
