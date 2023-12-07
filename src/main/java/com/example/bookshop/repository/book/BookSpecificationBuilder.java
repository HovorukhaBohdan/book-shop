package com.example.bookshop.repository.book;

import com.example.bookshop.model.Book;
import com.example.bookshop.repository.SpecificationBuilder;
import com.example.bookshop.repository.SpecificationProviderManager;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(Map<String, String> searchParameters) {
        Specification<Book> specification = Specification.where(null);

        for (Map.Entry<String, String> entry : searchParameters.entrySet()) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider(entry.getKey())
                    .getSpecification(entry.getValue().split(","))
            );
        }

        return specification;
    }
}
