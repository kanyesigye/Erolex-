package com.reposolutions.emmy.erolex;

/**
 * Created by emmy on 8/20/2017.
 */

public class ViewMenuModal {


    private String name,menuId,menuTitle,menuDscription,menuPrice,latitude,longitude;

    public ViewMenuModal() {
    }

    public ViewMenuModal(String name,String menuId, String menuTitle, String menuDscription, String menuPrice,String latitude,String longitude) {
        this.name = name;
        this.menuId = menuId;
        this.menuTitle = menuTitle;
        this.menuDscription = menuDscription;
        this.menuPrice = menuPrice;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuDscription() {
        return menuDscription;
    }

    public void setMenuDscription(String menuDscription) {
        this.menuDscription = menuDscription;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public void setMenuPrice(String menuPrice) {
        this.menuPrice = menuPrice;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
