package ru.skypro.homework.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@Data
public class Avatar {
    @Id
    private String image;
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] view;

}
