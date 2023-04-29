package com.example.weatherjava.model;

public class ItemFav {

    private String id;
    private String name;
    private String img;
    private String date;
    private String maxTemp;
    private String minTemp;
    private String description;

    public ItemFav(String id, String name, String img, String date, String maxTemp, String minTemp, String description) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.description = description;
    }

    public ItemFav(String name, String img, String date, String maxTemp, String minTemp, String description) {
        this.name = name;
        this.img = img;
        this.date = date;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
        this.description = description;
    }
    public ItemFav() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
