package com.example.demo.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class WordDto {
    private UUID uuid;
    private Integer id;
    private String meaning;
    private String vocabulary;
    private Boolean star;
}
