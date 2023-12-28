package com.example.bookshop.repository;

import com.example.bookshop.model.Book;
import com.example.bookshop.model.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    List<Book> getBooksByCategoriesContaining(Category categories);
}
