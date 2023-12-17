package com.example.bookshop.repository.book;

import com.example.bookshop.dto.book.BookSearchParametersDto;
import com.example.bookshop.model.Book;
import com.example.bookshop.repository.specification.SpecificationBuilder;
import com.example.bookshop.repository.specification.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private final SpecificationProviderManager<Book> specificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParameters) {
        Specification<Book> specification = Specification.where(null);

        if (searchParameters.title() != null && searchParameters.title().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("title")
                    .getSpecification(searchParameters.title())
            );
        }

        if (searchParameters.author() != null && searchParameters.author().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("author")
                    .getSpecification(searchParameters.author())
            );
        }

        if (searchParameters.isbn() != null && searchParameters.isbn().length > 0) {
            specification = specification.and(specificationProviderManager
                    .getSpecificationProvider("isbn")
                    .getSpecification(searchParameters.isbn())
            );
        }

        return specification;
    }
}
