package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

@Data
public class Ads {
    private Integer author;
    private String image;
    private Integer pk;
    private Integer price;
    private String title;
    private String description;

    public static Ads from(Ad ad) {
        Ads ads = new Ads();
        ads.setAuthor(ad.getAuthor().getId());
        ads.setImage(ad.getImage());
        ads.setPk(ad.getId());
        ads.setPrice(ad.getPrice());
        ads.setTitle(ad.getTitle());
        ads.setDescription(ad.getDescription());
        return ads;
    }
}
