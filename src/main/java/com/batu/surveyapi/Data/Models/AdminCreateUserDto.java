package com.batu.surveyapi.Data.Models;

import lombok.Data;

import java.util.List;

@Data
public class AdminCreateUserDto {

    public String username;
    public String email;
    public String password;
    public List<Integer> roleIds;
}
