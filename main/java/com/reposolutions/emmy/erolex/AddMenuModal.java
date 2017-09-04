package com.reposolutions.emmy.erolex;

/**
 * Created by emmy on 8/20/2017.
 */

public class AddMenuModal {

   private String menuId,menuTitle,menuDscription,menuPrice,name,Phone,latitude,longitude;

    public AddMenuModal() {
    }


    public AddMenuModal(String menuId, String menuTitle, String menuDscription, String menuPrice,String name,String Phone,String latitude,String longitude) {
    this.menuId = menuId;
    this.menuTitle = menuTitle;
    this.menuDscription = menuDscription;
    this.menuPrice = menuPrice;
        this.name = name;
        this.Phone = Phone;
        this.latitude=latitude;
        this.longitude=longitude;

}

    public String getMenuId() {
        return menuId;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getMenuDscription() {
        return menuDscription;
    }

    public String getMenuPrice() {
        return menuPrice;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return Phone;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}

