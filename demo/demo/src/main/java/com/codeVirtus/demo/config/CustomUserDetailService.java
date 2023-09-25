package com.codeVirtus.demo.config;

import com.codeVirtus.demo.models.User;
import com.codeVirtus.demo.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Data
@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user =  userRepository.findByEmail(username);
        if(user.isEmpty()) {
            throw  new UsernameNotFoundException("User with "+ username + " not found");
        }
        return new CustomUserDetails(user.get());
    }
}
