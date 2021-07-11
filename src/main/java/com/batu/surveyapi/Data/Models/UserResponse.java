package com.batu.surveyapi.Data.Models;

import com.batu.surveyapi.Core.Entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    public Long id;
    public String username;
    public String email;
    public Set<Role> roles;
}
