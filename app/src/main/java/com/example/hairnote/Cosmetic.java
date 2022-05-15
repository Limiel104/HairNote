package com.example.hairnote;

import java.io.Serializable;
import java.util.List;

public class Cosmetic implements Serializable {

    private int id;
    private String name;
    private String brand;
    private String pehType;
    private String cosmeticType;
    private String description;
    //private boolean cgCompatible;
    private String imgPath;
    private List<Integer> inciList;
    private List<String> shopList;

    public Cosmetic(int id, String name, String brand, String pehType, String cosmeticType, String description, String imgPath, List<Integer> inciList, List<String> shopList) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.pehType = pehType;
        this.cosmeticType = cosmeticType;
        this.description = description;
        //this.cgCompatible = cgCompatible;
        this.imgPath = imgPath;
        this.inciList = inciList;
        this.shopList = shopList;
    }

    public Cosmetic(int id, String name, String brand, String pehType, String cosmeticType, String description, String imgPath) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.pehType = pehType;
        this.cosmeticType = cosmeticType;
        this.description = description;
        this.imgPath = imgPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPehType() {
        return pehType;
    }

    public void setPehType(String pehType) {
        this.pehType = pehType;
    }

    public String getCosmeticType() {
        return cosmeticType;
    }

    public void setCosmeticType(String cosmeticType) {
        this.cosmeticType = cosmeticType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public List<Integer> getInciList() {
        return inciList;
    }

    public void setInciList(List<Integer> inciList) {
        this.inciList = inciList;
    }

    public List<String> getShopList() {
        return shopList;
    }

    public void setShopList(List<String> shopList) {
        this.shopList = shopList;
    }

    @Override
    public String toString() {
        return "Cosmetic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", pehType='" + pehType + '\'' +
                ", cosmeticType='" + cosmeticType + '\'' +
                ", description='" + description + '\'' +
                ", imgPath='" + imgPath + '\'' +
                ", inciList=" + inciList +
                ", shopList=" + shopList +
                '}';
    }
}
