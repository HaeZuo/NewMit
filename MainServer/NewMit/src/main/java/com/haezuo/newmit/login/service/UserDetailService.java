package com.haezuo.newmit.login.service;

import com.haezuo.newmit.config.jwt.TokenProvider;
import com.haezuo.newmit.login.domain.User;
import com.haezuo.newmit.login.repository.UserRepository;
import com.haezuo.newmit.login.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                 .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
