package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

@Data
public class FullAds {
    private Integer pk; //id
    private String authorFirstName;
    private String authorLastName;
    private String description;
    private String email; //логин автора
    private String image;//картинка объявления
    private String phone; //телефон автора
    private Integer price;
    private String title;

    public static FullAds from(Ad ad) {
        FullAds fullAds = new FullAds();
        fullAds.setPk(ad.getId());
        fullAds.setAuthorFirstName(ad.getAuthor().getFirstName());
        fullAds.setAuthorLastName(ad.getAuthor().getLastName());
        fullAds.setDescription(ad.getDescription());
        fullAds.setEmail(ad.getAuthor().getEmail());
        fullAds.setImage(ad.getImage());
        fullAds.setPhone(ad.getAuthor().getPhone());
        fullAds.setPrice(ad.getPrice());
        fullAds.setTitle(ad.getTitle());
        return fullAds;
    }
}
