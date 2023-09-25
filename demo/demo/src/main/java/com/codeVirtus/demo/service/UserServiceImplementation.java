package com.codeVirtus.demo.service;

import com.codeVirtus.demo.Dto.LoginDto;
import com.codeVirtus.demo.Dto.RegisterDto;
import com.codeVirtus.demo.config.JWTGenerator;
import com.codeVirtus.demo.models.Roles;
import com.codeVirtus.demo.models.User;
import com.codeVirtus.demo.repository.RoleRepository;
import com.codeVirtus.demo.repository.UserRepository;
import com.codeVirtus.demo.requests.AuthResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

@Data
@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private JavaMailSender mailSender;
    private AuthenticationManager authenticationManager;
    private JWTGenerator jwtGenerator;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository,  PasswordEncoder passwordEncoder,RoleRepository roleRepository,JavaMailSender mailSender,AuthenticationManager authenticationManager,JWTGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.mailSender = mailSender;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public ResponseEntity<String> register(RegisterDto registerDto){
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("User Already exists!", HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        newUser.setEnabled(false);

        Roles role = roleRepository.findByName("ADMIN");
        newUser.getRoles().add(role);
        userRepository.save(newUser);
        return new ResponseEntity<>("Check your email to verify account", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> processRegister(RegisterDto registerDto, String siteURL) throws MessagingException, UnsupportedEncodingException {
        if(userRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("User Already exists!", HttpStatus.BAD_REQUEST);
        }
        User newUser = new User();
        newUser.setFirstName(registerDto.getFirstName());
        newUser.setLastName(registerDto.getLastName());
        newUser.setEmail(registerDto.getEmail());

        String encodedPassword = passwordEncoder.encode(registerDto.getPassword());
        newUser.setPassword(encodedPassword);

        newUser.setEnabled(false);

        Roles role = roleRepository.findByName("USER");
        newUser.getRoles().add(role);


        String randomCode = generateRandomString(64);
        newUser.setVerificationCode(randomCode);
        newUser.setEnabled(false);

        userRepository.save(newUser);
        sendVerificationEmail(newUser, siteURL);
        return new ResponseEntity<>("Please check your email to verify", HttpStatus.OK);
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "saidiagibu100@outlook.com ";
        String senderName = "Code Virtus";
        String subject = "Please verify your registration";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Code Virtus.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/api/v1/auth/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);
        mailSender.send(message);

    }

    @Override
    public ResponseEntity<String> verify(String verificationCode) {
        User user = userRepository.findByVerificationCode(verificationCode);

        if (user == null || user.isEnabled()) {
            return new ResponseEntity<>("Invalid verification code or user is already enabled",HttpStatus.BAD_REQUEST);
        } else {
            user.setVerificationCode(null);
            user.setEnabled(true);
            userRepository.save(user);

            return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
        }

    }

    @Override
    public ResponseEntity<AuthResponse> login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication);
        return new ResponseEntity<>(new AuthResponse(token), HttpStatus.OK);
    }

    public static String generateRandomString(int length) {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        final SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
