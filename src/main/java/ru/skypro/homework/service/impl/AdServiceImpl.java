package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateAds;
import ru.skypro.homework.dto.FullAds;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.exception.NotFoundException;
import ru.skypro.homework.exception.UnauthorizedException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.util.Collection;

@Service
@Transactional
public class AdServiceImpl implements AdService {
    private final AdRepository adRepository;
    private final AvatarServiceImpl avatarService;
    private final UserServiceImpl userService;

    public AdServiceImpl(AdRepository adRepository, AvatarServiceImpl avatarService, UserServiceImpl userService) {
        this.adRepository = adRepository;
        this.avatarService = avatarService;
        this.userService = userService;
    }

    @Override
    public ResponseWrapperAds getAllAds() {
        return ResponseWrapperAds.from(adRepository.findAll());
    }

    @Override
    public Ads addAd(MultipartFile avatar, CreateAds createAds, Authentication authentication) throws IOException {
        UserEntity userEntity = userService.getUserBy(authentication.getName());
        Ad ad = createAds.to();
        ad.setAuthor(userEntity);
        ad.setImage(avatarService.uploadAvatar(avatar));
        return Ads.from(adRepository.save(ad));
    }

    @Override
    public FullAds getAds(Integer id) {
        return FullAds.from(adRepository.findById(id).orElseThrow(NotFoundException::new));
    }

    @Override
    public Ads updateAds(Integer id, CreateAds createAds) {
        Ad ad = adRepository.findById(id).orElseThrow(NotFoundException::new);
        ad.setDescription(createAds.getDescription());
        ad.setTitle(createAds.getTitle());
        ad.setPrice(createAds.getPrice());
        return Ads.from(adRepository.save(ad));
    }

    @Override
    public ResponseWrapperAds getAdsMe(Authentication authentication) {
        Collection<Ad> adCollection = adRepository.findAllByAuthorId(userService.getUserBy(authentication.getName()).getId()).orElseThrow(NotFoundException::new);
        return ResponseWrapperAds.from(adCollection);
    }

    @Override
    public void updateAvatarImage(Integer id, MultipartFile avatar, Authentication authentication) throws IOException {
        if (authentication.isAuthenticated()){
           Ad ad = adRepository.findById(id).orElseThrow(NotFoundException::new);
           ad.setImage(avatarService.uploadAvatar(avatar));
           adRepository.save(ad);
    }throw new UnauthorizedException();
    }

    @Override
    public void deleteAdById(Integer id) {
        Ad ad = adRepository.findById(id).orElseThrow(NotFoundException::new);
        adRepository.delete(ad);
    }
}
