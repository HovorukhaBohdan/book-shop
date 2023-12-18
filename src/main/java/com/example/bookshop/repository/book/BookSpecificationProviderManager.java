package com.example.bookshop.repository.book;

import com.example.bookshop.exception.SpecificationException;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.specification.SpecificationProvider;
import com.example.bookshop.repository.specification.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationProviderManager implements SpecificationProviderManager<Book> {
    private final List<SpecificationProvider<Book>> specificationProviders;

    @Override
    public SpecificationProvider<Book> getSpecificationProvider(String fieldName) {
        return specificationProviders.stream()
                .filter(sp -> sp.getFieldName().equals(fieldName))
                .findFirst()
                .orElseThrow(() -> new SpecificationException(
                        "Can't find specification provider for field with name: " + fieldName)
                );
    }
}
