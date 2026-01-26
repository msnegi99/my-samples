package com.msnegi.slidebanner.banner.pojo;

public class OptionItem {
    private int id;
    private String name;
    private int imgResource = -1;
    private String imgUrl;

    public int getId() {
        return id;
    }

    public OptionItem setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public OptionItem setName(String name) {
        this.name = name;
        return this;
    }

    public int getImgResource() {
        return imgResource;
    }

    public OptionItem setImgResource(int imgResource) {
        this.imgResource = imgResource;
        return this;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public OptionItem setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }
}
