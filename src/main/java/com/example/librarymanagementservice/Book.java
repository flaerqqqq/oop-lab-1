package com.example.librarymanagementservice;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    private String isbn;

    private String title;

    private String authorName;

    private String publishedDate;
}
