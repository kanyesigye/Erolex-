package com.reposolutions.emmy.erolex;

/**
 * Created by emmy on 8/20/2017.
 */

class ViewReservationModal {
    private String cuustomerId,customerName,customerPhone,customerNote,customerDate,customerTime,customerQty;

    public ViewReservationModal() {


    }

    public ViewReservationModal(String cuustomerId, String customerName, String customerPhone, String customerNote, String customerDate, String customerTime, String customerQty) {
        this.cuustomerId = cuustomerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerNote = customerNote;
        this.customerDate = customerDate;
        this.customerTime = customerTime;
        this.customerQty = customerQty;
    }

    public String getCustomerId() {
        return cuustomerId;
    }

    public void setCustomerId(String customerId) {
        this.cuustomerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerNote() {
        return customerNote;
    }

    public void setCustomerNote(String customerNote) {
        this.customerNote = customerNote;
    }

    public String getCustomerDate() {
        return customerDate;
    }

    public void setCustomerDate(String customerDate) {
        this.customerDate = customerDate;
    }

    public String getCustomerTime() {
        return customerTime;
    }

    public void setCustomerTime(String customerTime) {
        this.customerTime = customerTime;
    }

    public String getCustomerQty() {
        return customerQty;
    }

    public void setCustomerQty(String customerQty) {
        this.customerQty = customerQty;
    }
}
