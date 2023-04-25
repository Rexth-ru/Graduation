package ru.skypro.homework.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.MyUserDetails;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.en.Role;
import ru.skypro.homework.exception.BadRequestException;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public MyUserDetailsService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmailIgnoreCase(username);
        if (!user.isPresent()) throw new UsernameNotFoundException("Not Found");
        UserEntity userEntity = user.get();
        return new MyUserDetails(userEntity);
    }

    public void createUser(RegisterReq registerReq) {
        if (userRepository.findByEmailIgnoreCase(registerReq.getUsername()).isPresent()) {
            throw new BadRequestException();
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(registerReq.getUsername());
        userEntity.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        userEntity.setRole(Role.USER);
        userEntity.setFirstName(registerReq.getFirstName());
        userEntity.setLastName(registerReq.getLastName());
        userEntity.setPhone(registerReq.getPhone());
        userEntity.setEnabled(true);
        userRepository.save(userEntity);
    }
}