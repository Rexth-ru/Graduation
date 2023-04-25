package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final AvatarServiceImpl avatarServiceImpl;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(AvatarServiceImpl avatarServiceImpl, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.avatarServiceImpl = avatarServiceImpl;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void setPassword(NewPassword newPassword, Authentication authentication) {
        UserEntity userEntity = userRepository
                .findByEmailIgnoreCase(authentication.getName()).orElseThrow(NotFoundException::new);
        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), userEntity.getPassword()))
            throw new ForbiddenException();
        userEntity.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(userEntity);
    }

    @Override
    public User getUser(Authentication authentication) {
        UserEntity userEntity = userRepository.findByEmailIgnoreCase(authentication.getName()).orElseThrow(NotFoundException::new);
        return User.from(userEntity);
    }

    @Override
    public User updateUser(User user, Authentication authentication) {
        UserEntity userEntity = getUserBy(authentication.getName());
        ;
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPhone(user.getPhone());
        return User.from(userRepository.save(userEntity));
    }

    @Override
    public void updateUserImage(MultipartFile avatar, Authentication authentication) throws IOException {
        UserEntity userEntity = getUserBy(authentication.getName());
        userEntity.setImage(avatarServiceImpl.uploadAvatar(avatar));
        userRepository.save(userEntity);
    }

    public UserEntity getUserBy(String email) {
        return userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(NotFoundException::new);
    }
}

