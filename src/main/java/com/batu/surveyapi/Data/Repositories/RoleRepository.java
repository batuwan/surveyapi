package com.batu.surveyapi.Data.Repositories;

import com.batu.surveyapi.Core.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findByName(String roleName);
}
