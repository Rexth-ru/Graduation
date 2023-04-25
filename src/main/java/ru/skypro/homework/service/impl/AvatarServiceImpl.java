package ru.skypro.homework.service.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.model.Avatar;
import ru.skypro.homework.repository.AvatarRepository;
import ru.skypro.homework.service.AvatarService;

import java.io.IOException;

@Service
@Transactional
public class AvatarServiceImpl implements AvatarService {
    private final AvatarRepository avatarRepository;

    public AvatarServiceImpl(AvatarRepository avatarRepository) {
        this.avatarRepository = avatarRepository;
    }


    @Override
    public String uploadAvatar(MultipartFile file) throws IOException {
        Avatar avatar = new Avatar();
        avatar.setView(file.getBytes());
        String image = "/avatar/" + RandomStringUtils.randomAlphabetic(8);
        avatar.setImage(image);
        return avatarRepository.save(avatar).getImage();
    }

    @Override
    public byte[] getAvatar(String id) {
        return avatarRepository.findById(id).orElseThrow(NotFoundException::new).getView();
    }

}

