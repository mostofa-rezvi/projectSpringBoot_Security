package com.hms.projectSpringBoot.security.service;

import com.hms.projectSpringBoot.security.entity.User;
import com.hms.projectSpringBoot.security.repository.UserRepository;
import com.hms.projectSpringBoot.util.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ApiResponse save(User user) {
        ApiResponse response = new ApiResponse();
        try {
            User emailDuplicate = userRepository.findByEmail(user.getEmail()).orElse(null);
            if (emailDuplicate != null) {
                return response.error("Email already exists");
            }

            user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
            userRepository.save(user);

            response.setData("user", user);
            response.success("Saved Successfully");
            return response;
        } catch (Exception e) {
            return response.error(e.getMessage());
        }
    }

    @Transactional
    public ApiResponse update(User user) {
        ApiResponse response = new ApiResponse();
        try {
            User oldUser = userRepository.findById(user.getId()).orElse(null);

            if (oldUser == null) {
                return response.error("User not found");
            }

            User emailDuplicate = userRepository.findByEmail(user.getEmail()).orElse(null);
            if (emailDuplicate != null && !emailDuplicate.getId().equals(user.getId())) {
                return response.error("Email already exists");
            }

            user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
            userRepository.save(user);

            response.setData("user", user);
            response.success("Updated Successfully");
            return response;
        } catch (Exception e) {
            return response.error(e.getMessage());
        }
    }

    public ApiResponse deleteById(Long id) {
        ApiResponse response = new ApiResponse();
        try {
            User user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return response.error("User not found");
            }
            userRepository.deleteById(id);
            response.success("Deleted Successfully");
            return response;
        } catch (Exception e) {
            return response.error(e.getMessage());
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        } else {
            return user;
        }
    }

}
