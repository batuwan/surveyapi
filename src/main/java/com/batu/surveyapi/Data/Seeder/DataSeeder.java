package com.batu.surveyapi.Data.Seeder;

import com.batu.surveyapi.Core.Entities.Role;
import com.batu.surveyapi.Core.Entities.User;
import com.batu.surveyapi.Data.Repositories.RoleRepository;
import com.batu.surveyapi.Data.Repositories.UserRepository;
import com.batu.surveyapi.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        insertUserData();
    }

    private void insertUserData(){
        if (roleRepository.count() == 0){
            Role admin = new Role();
            admin.setName("ADMIN");
            Role user = new Role();
            user.setName("USER");
            roleRepository.save(admin);
            roleRepository.save(user);
        }

        if(userService.userCount() == 0){
            User user1 = new User();
            user1.setUsername("admin");
            user1.setPassword("admin12345");
            user1.setEmail("admin@admin.com");
            user1.setEnabled(true);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName("ADMIN"));
            user1.setRoles(roles);
            userService.save(user1);

            User user2 = new User();
            user2.setUsername("enduser");
            user2.setPassword("enduser12345");
            user2.setEmail("end@user.com");
            user2.setEnabled(true);
            roles = new HashSet<>();
            roles.add(roleRepository.findByName("USER"));
            user2.setRoles(roles);
            userService.save(user2);
        }
    }
}
