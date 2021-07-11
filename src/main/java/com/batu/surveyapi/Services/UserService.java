package com.batu.surveyapi.Services;

import com.batu.surveyapi.Core.Entities.Role;
import com.batu.surveyapi.Core.Entities.User;
import com.batu.surveyapi.Data.Models.AdminCreateUserDto;
import com.batu.surveyapi.Data.Models.UserDto;
import com.batu.surveyapi.Data.Models.UserResponse;
import com.batu.surveyapi.Data.Repositories.RoleRepository;
import com.batu.surveyapi.Data.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
    }

    public void save(User user) {
        //Encrypt the input password.
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public long userCount(){
        return userRepository.count();
    }

    public void register(UserDto userDto){
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        //Set initial role as 'USER'
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        user.setRoles(roles);
        save(user);
    }

    public void createUser(AdminCreateUserDto userDto){
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        //Set roles
        Set<Role> roles = new HashSet<>();

        for (int i = 0; i < userDto.roleIds.size(); i++) {
            var role = roleRepository.findById(userDto.roleIds.get(i));
            if (role != null){
            roles.add(role.get());
        }}
        user.setRoles(roles);
        save(user);
    }

    public UserResponse getUserById(long userId) {
        ModelMapper modelMapper = new ModelMapper();
        Optional<User> user = userRepository.findById(userId);
        if (user != null){
            UserResponse userResponse = modelMapper.map(user.get(), UserResponse.class);
            return userResponse;
        }
        else return null;
    }
}
