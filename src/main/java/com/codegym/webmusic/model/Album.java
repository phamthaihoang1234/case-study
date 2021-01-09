package com.codegym.webmusic.model;


import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String imgSrc;
    @Transient
    MultipartFile image;


    @ManyToOne
    @JoinColumn(name="singer_id",referencedColumnName = "id")
    Singer singer;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public MultipartFile getImage() {
        return image;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }
}
