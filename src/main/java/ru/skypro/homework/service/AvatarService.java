package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AvatarService {
    String uploadAvatar(MultipartFile file) throws IOException;

    byte[] getAvatar(String id);
}
