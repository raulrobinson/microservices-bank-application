package com.devsu.hackerearth.backend.client.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDomain {

    private Long id;
    private String dni;
    private String name;
    private String password;
    private String gender;
    private int age;
    private String address;
    private String phone;
    private boolean isActive;
}
