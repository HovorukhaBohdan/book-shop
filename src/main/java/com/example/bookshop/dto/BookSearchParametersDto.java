package com.example.bookshop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookSearchParametersDto {
    private String[] title;
    private String[] author;
    private String[] isbn;
    private BigDecimal[] price;
}
