package ru.skypro.homework.dto;

import lombok.Data;
import ru.skypro.homework.model.Ad;

import java.util.Collection;
import java.util.stream.Collectors;

@Data
public class ResponseWrapperAds {
    private Integer count;
    private Collection<Ads> results;

    public static ResponseWrapperAds from(Collection<Ad> ad) {
        ResponseWrapperAds responseWrapperAds = new ResponseWrapperAds();
        responseWrapperAds.setCount(ad.size());
        responseWrapperAds.setResults(ad.stream().map(Ads::from).collect(Collectors.toList()));
        return responseWrapperAds;
    }

}
