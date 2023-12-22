package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> getByCategoriesContains(Set<Category> categories);
}
