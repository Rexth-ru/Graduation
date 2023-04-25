package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

@Data
public class CreateAds {
    String description;
    Integer price;
    String title;

    public static CreateAds from(Ad ad) {
        CreateAds createAds = new CreateAds();
        createAds.setDescription(ad.getDescription());
        createAds.setTitle(ad.getTitle());
        createAds.setPrice(ad.getPrice());
        return createAds;
    }

    public Ad to() {
        Ad ad = new Ad();
        ad.setDescription(this.getDescription());
        ad.setTitle(this.getTitle());
        ad.setPrice(this.getPrice());
        return ad;
    }
}
