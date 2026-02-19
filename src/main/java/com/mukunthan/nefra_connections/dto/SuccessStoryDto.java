package com.mukunthan.nefra_connections.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuccessStoryDto {
    private Long id;
    private String title;
    private String metric;
    private String outcome;
    private String author;
    private String company;
    private String imageUrl;
}