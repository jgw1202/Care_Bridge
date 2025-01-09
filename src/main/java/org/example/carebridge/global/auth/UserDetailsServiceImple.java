package org.example.carebridge.global.auth;

import lombok.RequiredArgsConstructor;
import org.example.carebridge.domain.user.entity.User;
import org.example.carebridge.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImple implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = this.userRepository.findByIdOrElseThrow(Long.valueOf(userId));

        return new UserDetailsImple(user);
    }
}
