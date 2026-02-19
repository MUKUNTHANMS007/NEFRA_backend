package com.mukunthan.nefra_connections.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String role;
    private String company;
    private String industry;
    private String imageUrl; // Changed from imageUrl to image
    private boolean verified;
}