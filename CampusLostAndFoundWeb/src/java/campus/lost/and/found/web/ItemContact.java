package campus.lost.and.found.web;

import java.sql.Timestamp;

public class ItemContact extends ItemDetails {
    public String pickupPerson;
    public String contact;
    public String status = "Unclaimed";
    public String submitterUsername;
    public String claimant;
    public String claimContact;
    public Timestamp claimTime;
    public String claimantUsername;

    public ItemContact(int id, String itemName, String category,
            String pickupLocation, Timestamp pickupTime, String description,
            String pickupPerson, String contact, String submitterUsername) {
        super(id, itemName, category, pickupLocation, pickupTime, description);
        this.pickupPerson = pickupPerson;
        this.contact = contact;
        this.submitterUsername = submitterUsername;
    }
}
