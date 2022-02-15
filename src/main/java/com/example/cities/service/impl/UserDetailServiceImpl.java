package com.example.cities.service.impl;

import com.example.cities.domain.Role;
import com.example.cities.domain.User;
import com.example.cities.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        user.ifPresentOrElse(e -> {
            log.info("user found:{}", e.getEmail());
        }, () -> new UsernameNotFoundException("User not found"));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach(role-> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), authorities);
    }


    public void initUsers() throws IOException {
        User user = new User();
        user.setFirstName("user");
        user.setEmail("user@example.com");
        user.setPassword(bCryptPasswordEncoder.encode("user"));

        Role role = new Role();
        role.setName("USER_ROLE");

        Role role2 = new Role();
        role2.setName("ROLE_ALLOW_EDIT");

        user.setRoles(List.of(role, role2));
        userRepository.save(user);


        Role role3 = new Role();
        role3.setName("GUEST_ROLE");

        User user2 = new User();
        user2.setFirstName("user2");
        user2.setEmail("user2@example.com");
        user2.setPassword(bCryptPasswordEncoder.encode("user2"));

        user2.setRoles(List.of(role3));
        userRepository.save(user2);
    }


    @Bean
    CommandLineRunner usersInitRunner(){
        return (arg)->{
            initUsers();
        };
    }
}
