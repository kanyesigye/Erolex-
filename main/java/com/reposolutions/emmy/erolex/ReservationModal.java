package com.reposolutions.emmy.erolex;

/**
 * Created by emmy on 8/20/2017.
 */

public class ReservationModal {

    String  customerId,customerName,customerPhone,customerNote,customerDate,customerTime,customerQty;

    public ReservationModal() {

    }

    public ReservationModal(String customerId, String customerName, String customerPhone, String customerNote, String customerDate, String customerTime, String customerQty) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerNote = customerNote;
        this.customerDate = customerDate;
        this.customerTime = customerTime;
        this.customerQty = customerQty;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public String getCustomerDate() {return customerDate;
    }

    public String getCustomerTime() {
        return customerTime;
    }

    public String getCustomerQty() {
        return customerQty;
    }
}
