package com.fashion;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "fashion-collection")
public class Fashion {

    @Id
    private String id;

    private String customer;
    private String couturier;
    private String fittingDate;
    private String itemModel;
    private String sizes;
    private String fabrics;
    private String customerLevel;
    private int couturierExperience;
    private String atelierAddress;
    private long atelierPhone;

    public Fashion() {
    }

    public Fashion(String customer, String couturier, String fittingDate, String itemModel,
                   String sizes, String fabrics, String customerLevel, int couturierExperience,
                   String atelierAddress, long atelierPhone) {
        this.customer = customer;
        this.couturier = couturier;
        this.fittingDate = fittingDate;
        this.itemModel = itemModel;
        this.sizes = sizes;
        this.fabrics = fabrics;
        this.customerLevel = customerLevel;
        this.couturierExperience = couturierExperience;
        this.atelierAddress = atelierAddress;
        this.atelierPhone = atelierPhone;
    }

    public String getId() {
        return id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCouturier() {
        return couturier;
    }

    public void setCouturier(String couturier) {
        this.couturier = couturier;
    }

    public String getFittingDate() {
        return fittingDate;
    }

    public void setFittingDate(String fittingDate) {
        this.fittingDate = fittingDate;
    }

    public String getItemModel() {
        return itemModel;
    }

    public void setItemModel(String itemModel) {
        this.itemModel = itemModel;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getFabrics() {
        return fabrics;
    }

    public void setFabrics(String fabrics) {
        this.fabrics = fabrics;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }

    public int getCouturierExperience() {
        return couturierExperience;
    }

    public void setCouturierExperience(int couturierExperience) {
        this.couturierExperience = couturierExperience;
    }

    public String getAtelierAddress() {
        return atelierAddress;
    }

    public void setAtelierAddress(String atelierAddress) {
        this.atelierAddress = atelierAddress;
    }

    public long getAtelierPhone() {
        return atelierPhone;
    }

    public void setAtelierPhone(long atelierPhone) {
        this.atelierPhone = atelierPhone;
    }

    @Override
    public String toString() {
        return "Fashion{"
                + "id='" + id + '\''
                + ", customer='" + customer + '\''
                + ", couturier='" + couturier + '\''
                + ", fittingDate='" + fittingDate + '\''
                + ", itemModel='" + itemModel + '\''
                + ", sizes='" + sizes + '\''
                + ", fabrics='" + fabrics + '\''
                + ", customerLevel='" + customerLevel + '\''
                + ", couturierExperience=" + couturierExperience
                + ", atelierAddress='" + atelierAddress + '\''
                + ", atelierPhone=" + atelierPhone
                + '}';
    }
}
