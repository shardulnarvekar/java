package com.campus.lostandfound.DTO;

import java.sql.Timestamp;

// Lost item DTO
public class LostItemDTO {

    public static final String[] DEFAULT_CATEGORIES = {
            "Electronics",
            "Identification",
            "Clothing & Accessories",
            "Study Supplies",
            "Everyday Items",
            "Valuables",
            "Other"
    };

    public static final String[] DEFAULT_STATUSES = {
            "Unclaimed",
            "Claimed",
            "Handed Over"
    };

    private int id;
    private String itemName;
    private String category;
    private String pickupLocation;
    private Timestamp pickupTime;
    private String pickupPerson;
    private String contact;
    private String description;
    private String status;
    private String claimant;
    private String claimContact;
    private Timestamp claimTime;
    private String submitterUsername;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Timestamp getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Timestamp pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickupPerson() {
        return pickupPerson;
    }

    public void setPickupPerson(String pickupPerson) {
        this.pickupPerson = pickupPerson;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClaimant() {
        return claimant;
    }

    public void setClaimant(String claimant) {
        this.claimant = claimant;
    }

    public String getClaimContact() {
        return claimContact;
    }

    public void setClaimContact(String claimContact) {
        this.claimContact = claimContact;
    }

    public Timestamp getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(Timestamp claimTime) {
        this.claimTime = claimTime;
    }

    public String getSubmitterUsername() {
        return submitterUsername;
    }

    public void setSubmitterUsername(String submitterUsername) {
        this.submitterUsername = submitterUsername;
    }
}
