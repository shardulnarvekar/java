package campus.lost.and.found.web;

import java.sql.Timestamp;

public class ItemDetails extends Item {
    public String pickupLocation;
    public Timestamp pickupTime;
    public String description;

    public ItemDetails(int id, String itemName, String category,
            String pickupLocation, Timestamp pickupTime, String description) {
        super(id, itemName, category);
        this.pickupLocation = pickupLocation;
        this.pickupTime = pickupTime;
        this.description = description;
    }
}
