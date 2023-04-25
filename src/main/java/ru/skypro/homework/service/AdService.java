package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;

import java.io.IOException;

public interface AdService {
    ResponseWrapperAds getAllAds();

    Ads addAd(MultipartFile avatar, CreateAds createAds, Authentication authentication) throws IOException;

    FullAds getAds(Integer id);

    Ads updateAds(Integer id, CreateAds createAds);

    ResponseWrapperAds getAdsMe(Authentication authentication);

    void updateAvatarImage(Integer id, MultipartFile avatar) throws IOException;

    void deleteAdById(Integer id);

}
