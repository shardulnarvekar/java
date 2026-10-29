package campus.lost.and.found.system;

import java.sql.*;
import java.util.ArrayList;

/** Constructors and inheritance follow EmployeeOperations in the classroom project. */
public class LostItemOperations extends ItemContact {
    public static final String[] CATEGORIES = {"Electronics", "Identification",
        "Clothing & Accessories", "Study Supplies", "Everyday Items", "Valuables", "Other"};
    public static final String[] STATUSES = {"Unclaimed", "Claimed", "Handed Over"};

    public LostItemOperations(int id, String itemName, String category,
            String pickupLocation, Timestamp pickupTime, String description,
            String pickupPerson, String contact, String submitterUsername) {
        super(id, itemName, category, pickupLocation, pickupTime, description,
                pickupPerson, contact, submitterUsername);
    }

    private void validateItem() throws SQLException {
        if (itemName.isEmpty() || pickupLocation.isEmpty() || pickupPerson.isEmpty()
                || contact.isEmpty() || pickupTime == null) {
            throw new SQLException("Complete all required fields.");
        }
    }

    public void addItem() throws SQLException {
        validateItem();
        String sql = "INSERT INTO lost_items (item_name,category,pickup_location,pickup_time,"
                + "description,pickup_person,contact,submitter_username,status) VALUES (?,?,?,?,?,?,?,?,'Unclaimed')";
        try (Connection con = DBConnect.dbCon();
                PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setDetails(pst);
            pst.setString(8, submitterUsername);
            pst.executeUpdate();
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) id = rs.getInt(1);
            }
        }
    }

    public boolean updateItem(String actingUsername) throws SQLException {
        validateItem();
        // Claim details are deliberately preserved when editing report information.
        String sql = "UPDATE lost_items SET item_name=?,category=?,pickup_location=?,pickup_time=?,"
                + "description=?,pickup_person=?,contact=?,"
                + "status=CASE WHEN EXISTS (SELECT 1 FROM users WHERE username=? AND usertype='ADMIN') "
                + "THEN ? ELSE status END WHERE id=? AND "
                + "((submitter_username=? AND status='Unclaimed') OR "
                + "EXISTS (SELECT 1 FROM users WHERE username=? AND usertype='ADMIN'))";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            setDetails(pst);
            pst.setString(8, actingUsername);
            pst.setString(9, status);
            pst.setInt(10, id);
            pst.setString(11, actingUsername);
            pst.setString(12, actingUsername);
            return pst.executeUpdate() > 0;
        }
    }

    private void setDetails(PreparedStatement pst) throws SQLException {
        pst.setString(1, itemName);
        pst.setString(2, category);
        pst.setString(3, pickupLocation);
        pst.setTimestamp(4, pickupTime);
        pst.setString(5, description);
        pst.setString(6, pickupPerson);
        pst.setString(7, contact);
    }

    public static boolean deleteItem(int id, String actingUsername) throws SQLException {
        String sql = "DELETE FROM lost_items WHERE id=? AND "
                + "((submitter_username=? AND status='Unclaimed') OR "
                + "EXISTS (SELECT 1 FROM users WHERE username=? AND usertype='ADMIN'))";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.setString(2, actingUsername);
            pst.setString(3, actingUsername);
            return pst.executeUpdate() > 0;
        }
    }

    public static LostItemOperations searchItem(int id) throws SQLException {
        String sql = "SELECT * FROM lost_items WHERE id=?";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return readItem(rs);
            }
        }
        return null;
    }

    public boolean canEdit(User user) {
        return "ADMIN".equals(user.userType)
                || (user.username.equals(submitterUsername) && "Unclaimed".equals(status));
    }

    public boolean canClaim() {
        return "Unclaimed".equals(status) && (claimant == null || claimant.isEmpty()) && claimTime == null;
    }

    public static boolean claimItem(int id, String username, String claimant, String contact) throws SQLException {
        if (claimant.isEmpty() || contact.isEmpty()) throw new SQLException("Enter claimant name and contact.");
        String sql = "UPDATE lost_items SET status='Claimed',claimant_username=?,claimant=?,claim_contact=?,claim_time=NOW() "
                + "WHERE id=? AND status='Unclaimed' AND (claimant IS NULL OR claimant='') AND claim_time IS NULL";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, claimant);
            pst.setString(3, contact);
            pst.setInt(4, id);
            return pst.executeUpdate() == 1;
        }
    }

    public static boolean handoverItem(int id, String adminUsername, String recipient) throws SQLException {
        if (recipient.isEmpty()) throw new SQLException("Enter the recipient's student ID.");
        UserOperations user = UserOperations.searchUser(recipient);
        String sql = "UPDATE lost_items SET status='Handed Over',claimant_username=?,claimant=?,claim_contact=?,claim_time=NOW() "
                + "WHERE id=? AND status='Unclaimed' AND (claimant IS NULL OR claimant='') AND claim_time IS NULL "
                + "AND EXISTS (SELECT 1 FROM users WHERE username=? AND usertype='ADMIN')";
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user == null ? null : user.username);
            pst.setString(2, user == null ? recipient : user.name);
            pst.setString(3, user == null ? "" : user.phone);
            pst.setInt(4, id);
            pst.setString(5, adminUsername);
            return pst.executeUpdate() == 1;
        }
    }

    // Fixed placeholders keep the search readable and keep typed text out of SQL code.
    private static final String FILTER = " WHERE item_name LIKE ? AND (?='All' OR category=?) "
            + "AND pickup_location LIKE ? AND (?='All' OR status=?) "
            + "AND (?='' OR submitter_username=?) AND (?='' OR claimant_username=?) "
            + "AND (?=0 OR status IN ('Claimed','Handed Over') OR (claimant IS NOT NULL AND claimant<>'')) "
            + "AND (item_name LIKE ? OR category LIKE ? OR pickup_location LIKE ? OR pickup_person LIKE ? "
            + "OR contact LIKE ? OR description LIKE ? OR claimant LIKE ?)";

    private static void setFilters(PreparedStatement pst, String name, String category,
            String location, String status, String submitter, String claimant,
            boolean history, String keyword) throws SQLException {
        pst.setString(1, "%" + name + "%");
        pst.setString(2, category);
        pst.setString(3, category);
        pst.setString(4, "%" + location + "%");
        pst.setString(5, status);
        pst.setString(6, status);
        pst.setString(7, submitter);
        pst.setString(8, submitter);
        pst.setString(9, claimant);
        pst.setString(10, claimant);
        pst.setInt(11, history ? 1 : 0);
        for (int i = 12; i <= 18; i++) pst.setString(i, "%" + keyword + "%");
    }

    public static ArrayList<LostItemOperations> searchItems(String name, String category,
            String location, String status, String submitter, String claimant,
            boolean history, String keyword, int page, int pageSize, boolean oldestFirst) throws SQLException {
        String order = oldestFirst ? "ASC" : "DESC";
        String sql = "SELECT * FROM lost_items" + FILTER + " ORDER BY pickup_time "
                + order + ",id " + order + " LIMIT ? OFFSET ?";
        ArrayList<LostItemOperations> items = new ArrayList<>();
        try (Connection con = DBConnect.dbCon(); PreparedStatement pst = con.prepareStatement(sql)) {
            setFilters(pst, name, category, location, status, submitter, claimant, history, keyword);
            pst.setInt(19, pageSize);
            pst.setInt(20, (Math.max(1, page) - 1) * pageSize);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) items.add(readItem(rs));
            }
        }
        return items;
    }

    public static int countItems(String name, String category, String location, String status,
            String submitter, String claimant, boolean history, String keyword) throws SQLException {
        try (Connection con = DBConnect.dbCon();
                PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM lost_items" + FILTER)) {
            setFilters(pst, name, category, location, status, submitter, claimant, history, keyword);
            try (ResultSet rs = pst.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private static LostItemOperations readItem(ResultSet rs) throws SQLException {
        LostItemOperations item = new LostItemOperations(rs.getInt("id"), rs.getString("item_name"),
                rs.getString("category"), rs.getString("pickup_location"), rs.getTimestamp("pickup_time"),
                rs.getString("description"), rs.getString("pickup_person"), rs.getString("contact"),
                rs.getString("submitter_username"));
        item.status = rs.getString("status");
        item.claimant = rs.getString("claimant");
        item.claimContact = rs.getString("claim_contact");
        item.claimTime = rs.getTimestamp("claim_time");
        item.claimantUsername = rs.getString("claimant_username");
        return item;
    }

    public String details() {
        return "Item Name: " + itemName + "\nCategory: " + category
                + "\nFound Location: " + pickupLocation + "\nDate Found: " + pickupTime
                + "\nFound By: " + pickupPerson + "\nContact: " + contact + "\nStatus: " + status
                + "\nDescription: " + text(description) + "\nClaimant: " + text(claimant)
                + "\nClaimant Contact: " + text(claimContact) + "\nClaim Date: " + text(claimTime);
    }

    private String text(Object value) { return value == null ? "" : value.toString(); }
}
