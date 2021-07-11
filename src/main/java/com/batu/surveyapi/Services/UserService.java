package com.batu.surveyapi.Services;

import com.batu.surveyapi.Core.Entities.Role;
import com.batu.surveyapi.Core.Entities.User;
import com.batu.surveyapi.Data.Models.AdminCreateUserDto;
import com.batu.surveyapi.Data.Models.UserDto;
import com.batu.surveyapi.Data.Models.UserResponse;
import com.batu.surveyapi.Data.Repositories.RoleRepository;
import com.batu.surveyapi.Data.Repositories.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    private JavaMailSender mailSender;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, JavaMailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
        this.mailSender = mailSender;
    }

    public void save(User user) {
        //Encrypt the input password.
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public long userCount(){
        return userRepository.count();
    }

    public void register(UserDto userDto, String siteUrl) throws MessagingException, UnsupportedEncodingException {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userDto, User.class);
        //Set initial role as 'USER'
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName("USER"));
        user.setRoles(roles);

        String randomCode = RandomString.make(64);
        user.setVerificationCode(randomCode);
        user.setEnabled(false);

        save(user);

        sendVerificationEmail(user, siteUrl);
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

    public void sendVerificationEmail(User user, String siteUrl) throws MessagingException, UnsupportedEncodingException {

        String toAddress = user.getEmail();
        String fromAddress = "surveyapplayermark@gmail.com";
        String senderName = "Survey App Layermark";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Survey App Layermark";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getUsername());
        String verifyURL = siteUrl + "/api/auth/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
    }

    public boolean verifyAccount(String verificationCode){
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return true;
        }
    }
}
