package ru.skypro.homework.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.AvatarService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/avatar")
@AllArgsConstructor
public class AvatarController {
    private final AvatarService avatarService;

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_GIF_VALUE})
    public byte[] getAvatar(@PathVariable String id) {
        return avatarService.getAvatar("/avatar/" + id);
    }
}
