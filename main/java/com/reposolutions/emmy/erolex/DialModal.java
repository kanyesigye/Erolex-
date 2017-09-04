package com.reposolutions.emmy.erolex;

/**
 * Created by emmy on 8/22/2017.
 */

public class DialModal {

   private String name,Phone;

    public DialModal() {
    }

    public DialModal(String name, String Phone) {
        this.name = name;
        this.Phone = Phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }
}
