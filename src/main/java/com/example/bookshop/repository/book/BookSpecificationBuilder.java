package com.example.bookshop.repository.book;

import com.example.bookshop.dto.BookSearchParametersDto;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.SpecificationBuilder;
import com.example.bookshop.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> specification = Specification.where(null);

        if (searchParameters.getTitle() != null && searchParameters.getTitle().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParameters.getTitle())
            );
        }

        if (searchParameters.getAuthor() != null && searchParameters.getAuthor().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParameters.getAuthor())
            );
        }

        if (searchParameters.getIsbn() != null && searchParameters.getIsbn().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.getIsbn())
            );
        }

        return specification;
    }
}
