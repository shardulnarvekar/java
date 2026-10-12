package com.campus.lostandfound.DAO;

import com.campus.lostandfound.DTO.LostItemDTO;
import com.campus.lostandfound.Database.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

public class LostItemDAO {

    private Connection conn = null;
    private PreparedStatement prepStatement = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public LostItemDAO() {
        try {
            conn = new ConnectionFactory().getConn();
            statement = conn.createStatement();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addLostItem(LostItemDTO dto) {
        try {
            String sql = "INSERT INTO lost_items "
                    + "(item_name, category, pickup_location, pickup_time, pickup_person, contact, description, status, claimant, claim_contact, claim_time, submitter_username, claimant_username) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            prepStatement = conn.prepareStatement(sql);
            fillLostItemStatement(prepStatement, dto);
            prepStatement.setString(12, dto.getSubmitterUsername());
            prepStatement.setString(13, null);
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Lost item report submitted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Submission failed. Please check the information and try again.");
        }
    }

    public void updateLostItem(LostItemDTO dto) {
        try {
            String sql = "UPDATE lost_items SET item_name=?, category=?, pickup_location=?, pickup_time=?, "
                    + "pickup_person=?, contact=?, description=?, status=?, claimant=?, claim_contact=?, claim_time=? WHERE id=?";
            prepStatement = conn.prepareStatement(sql);
            fillLostItemStatement(prepStatement, dto);
            prepStatement.setInt(12, dto.getId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Lost item report updated.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Update failed. Please check the information and try again.");
        }
    }

    private void fillLostItemStatement(PreparedStatement ps, LostItemDTO dto) throws SQLException {
        ps.setString(1, dto.getItemName());
        ps.setString(2, dto.getCategory());
        ps.setString(3, dto.getPickupLocation());
        ps.setTimestamp(4, dto.getPickupTime());
        ps.setString(5, dto.getPickupPerson());
        ps.setString(6, dto.getContact());
        ps.setString(7, dto.getDescription());
        ps.setString(8, dto.getStatus());
        ps.setString(9, dto.getClaimant());
        ps.setString(10, dto.getClaimContact());
        ps.setTimestamp(11, dto.getClaimTime());
    }

    public void deleteLostItem(int id) {
        try {
            String sql = "DELETE FROM lost_items WHERE id=?";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, id);
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Lost item report deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Deletion failed. Please try again.");
        }
    }

    public ResultSet getBySubmitter(String submitter, String order) {
        try {
            prepStatement = conn.prepareStatement(baseSelectSql() + " WHERE submitter_username=? ORDER BY pickup_time " + safeOrder(order));
            prepStatement.setString(1, submitter);
            resultSet = prepStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean canUserEditOrDelete(int id, String username) {
        try {
            String sql = "SELECT id FROM lost_items WHERE id=? AND submitter_username=? AND status='Unclaimed' LIMIT 1";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setInt(1, id);
            prepStatement.setString(2, username);
            resultSet = prepStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet searchLostItems(String name, String category, String location, String status, String submitter,
                                     String claimantUsername, boolean onlyClaimed, int page, int pageSize, String order) {
        try {
            QueryParts parts = buildSearchSql(name, category, location, status, submitter, claimantUsername, onlyClaimed);
            parts.sql.append(" ORDER BY pickup_time ").append(safeOrder(order)).append(" LIMIT ? OFFSET ?");
            prepStatement = conn.prepareStatement(parts.sql.toString());
            int index = bindParams(prepStatement, parts.params);
            prepStatement.setInt(index++, pageSize);
            prepStatement.setInt(index, Math.max(0, page - 1) * pageSize);
            resultSet = prepStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int countSearch(String name, String category, String location, String status, String submitter,
                           String claimantUsername, boolean onlyClaimed) {
        try {
            QueryParts parts = buildSearchSql(name, category, location, status, submitter, claimantUsername, onlyClaimed);
            String countSql = parts.sql.toString().replace(baseSelectSql(), "SELECT COUNT(*) FROM lost_items");
            prepStatement = conn.prepareStatement(countSql);
            bindParams(prepStatement, parts.params);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet fuzzySearch(String keyword, String submitter, String claimantUsername, boolean onlyClaimed,
                                 int page, int pageSize, String order) {
        try {
            QueryParts parts = buildFuzzySql(keyword, submitter, claimantUsername, onlyClaimed);
            parts.sql.append(" ORDER BY pickup_time ").append(safeOrder(order)).append(" LIMIT ? OFFSET ?");
            prepStatement = conn.prepareStatement(parts.sql.toString());
            int index = bindParams(prepStatement, parts.params);
            prepStatement.setInt(index++, pageSize);
            prepStatement.setInt(index, Math.max(0, page - 1) * pageSize);
            resultSet = prepStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public int countFuzzy(String keyword, String submitter, String claimantUsername, boolean onlyClaimed) {
        try {
            QueryParts parts = buildFuzzySql(keyword, submitter, claimantUsername, onlyClaimed);
            String countSql = parts.sql.toString().replace(baseSelectSql(), "SELECT COUNT(*) FROM lost_items");
            prepStatement = conn.prepareStatement(countSql);
            bindParams(prepStatement, parts.params);
            resultSet = prepStatement.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void claimItem(int id, String claimantUsername, String claimant, String claimContact) {
        try {
            String sql = "UPDATE lost_items SET status='Claimed', claimant_username=?, claimant=?, claim_contact=?, claim_time=? "
                    + "WHERE id=? AND status='Unclaimed' AND (claimant IS NULL OR claimant='') AND claim_time IS NULL";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, claimantUsername);
            prepStatement.setString(2, claimant);
            prepStatement.setString(3, claimContact);
            prepStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            prepStatement.setInt(5, id);
            int changed = prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, changed > 0 ? "Claim recorded successfully."
                    : "This item is no longer available to claim.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not record the claim. Please check the information and try again.");
        }
    }

    public void transferItem(int id, String claimantUsername, String claimant, String claimContact) {
        try {
            String sql = "UPDATE lost_items SET status='Handed Over', claimant_username=?, claimant=?, claim_contact=?, claim_time=? "
                    + "WHERE id=? AND status='Unclaimed' AND (claimant IS NULL OR claimant='') AND claim_time IS NULL";
            prepStatement = conn.prepareStatement(sql);
            prepStatement.setString(1, claimantUsername);
            prepStatement.setString(2, claimant);
            prepStatement.setString(3, claimContact);
            prepStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            prepStatement.setInt(5, id);
            int changed = prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, changed > 0 ? "Handover recorded successfully."
                    : "This item is no longer available for handover.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Could not record the handover. Please check the information and try again.");
        }
    }

    private QueryParts buildSearchSql(String name, String category, String location, String status,
                                      String submitter, String claimantUsername, boolean onlyClaimed) {
        QueryParts parts = new QueryParts(baseSelectSql() + " WHERE 1=1");
        if (name != null && !name.trim().isEmpty()) {
            parts.sql.append(" AND item_name LIKE ?");
            parts.params.add("%" + name.trim() + "%");
        }
        if (category != null && !category.trim().isEmpty() && !"All".equals(category)) {
            parts.sql.append(" AND category = ?");
            parts.params.add(category.trim());
        }
        if (location != null && !location.trim().isEmpty()) {
            parts.sql.append(" AND pickup_location LIKE ?");
            parts.params.add("%" + location.trim() + "%");
        }
        if (status != null && !status.trim().isEmpty() && !"All".equals(status)) {
            parts.sql.append(" AND status = ?");
            parts.params.add(status.trim());
        }
        if (submitter != null && !submitter.trim().isEmpty()) {
            parts.sql.append(" AND submitter_username = ?");
            parts.params.add(submitter.trim());
        }
        if (claimantUsername != null && !claimantUsername.trim().isEmpty()) {
            parts.sql.append(" AND claimant_username = ?");
            parts.params.add(claimantUsername.trim());
        }
        if (onlyClaimed) {
            parts.sql.append(" AND claimant IS NOT NULL AND claimant <> ''");
        }
        return parts;
    }

    private QueryParts buildFuzzySql(String keyword, String submitter, String claimantUsername, boolean onlyClaimed) {
        QueryParts parts = new QueryParts(baseSelectSql()
                + " WHERE (item_name LIKE ? OR category LIKE ? OR pickup_location LIKE ? "
                + "OR pickup_person LIKE ? OR contact LIKE ? OR description LIKE ? OR claimant LIKE ?)");
        String pattern = "%" + keyword.trim() + "%";
        for (int i = 0; i < 7; i++) {
            parts.params.add(pattern);
        }
        if (submitter != null && !submitter.trim().isEmpty()) {
            parts.sql.append(" AND submitter_username = ?");
            parts.params.add(submitter.trim());
        }
        if (claimantUsername != null && !claimantUsername.trim().isEmpty()) {
            parts.sql.append(" AND claimant_username = ?");
            parts.params.add(claimantUsername.trim());
        }
        if (onlyClaimed) {
            parts.sql.append(" AND claimant IS NOT NULL AND claimant <> ''");
        }
        return parts;
    }

    private int bindParams(PreparedStatement ps, Vector<Object> params) throws SQLException {
        int index = 1;
        for (Object param : params) {
            ps.setObject(index++, param);
        }
        return index;
    }

    private String baseSelectSql() {
        return "SELECT id,item_name,category,pickup_location,pickup_time,pickup_person,contact,"
                + "description,status,claimant,claim_contact,claim_time,submitter_username,claimant_username FROM lost_items";
    }

    private String safeOrder(String order) {
        return "ASC".equalsIgnoreCase(order) ? "ASC" : "DESC";
    }

    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int col = 1; col <= colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

    private static class QueryParts {
        private final StringBuilder sql;
        private final Vector<Object> params = new Vector<>();

        private QueryParts(String sql) {
            this.sql = new StringBuilder(sql);
        }
    }
}
