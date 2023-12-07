package com.example.bookshop.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BookSearchParametersDto {
    private String[] title;
    private String[] author;
    private String[] isbn;
}
