package com.example.bookshop;

import com.example.bookshop.model.Book;
import com.example.bookshop.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BookShopApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(BookShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book rage = new Book();
            rage.setTitle("Rage");
            rage.setAuthor("Stephen King");
            rage.setIsbn("978-0-451-07645-8");
            rage.setPrice(BigDecimal.valueOf(10.99));

            Book it = new Book();
            it.setTitle("It");
            it.setAuthor("Stephen King");
            it.setIsbn("978-0-670-81302-5");
            it.setPrice(BigDecimal.valueOf(8.99));

            bookService.save(rage);
            bookService.save(it);

            System.out.println(bookService.findAll());
        };
    }
}
