package com.mukunthan.nefra_connections.dto;

import com.mukunthan.nefra_connections.entity.User;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private User.Role role; // Changed from String to User.Role
    private String company;
    private String industry;
    private String imageUrl;
    private boolean verified;
}