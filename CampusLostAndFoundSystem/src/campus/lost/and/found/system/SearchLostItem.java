package campus.lost.and.found.system;

import java.sql.*;
import javax.swing.*;

public class SearchLostItem extends javax.swing.JInternalFrame {
    private UserOperations user;
    private String mode;
    private java.util.ArrayList<LostItemOperations> items = new java.util.ArrayList<>();
    private int page = 1;
    private int total;
    private static final int PAGE_SIZE = 18;
    private String nameFilter = "", locationFilter = "", keywordFilter = "";
    private String categoryFilter = "All", statusFilter = "All";
    private boolean searched;

    public SearchLostItem() { initComponents(); }

    public SearchLostItem(UserOperations user, String mode) {
        this();
        this.user = user;
        this.mode = mode;
        boolean admin = "ADMIN".equals(user.userType);
        String title = "Search Lost Items";
        if ("MINE".equals(mode)) title = "My Reports";
        else if ("HISTORY".equals(mode)) title = admin ? "Claim & Handover History" : "My Claim History";
        else if (admin) title = "Admin Management";
        setTitle(title);
        lblTitle.setText(title);
        btnEdit.setVisible(!"HISTORY".equals(mode) && (admin || "MINE".equals(mode)));
        btnDelete.setVisible(btnEdit.isVisible());
        btnClaim.setVisible("SEARCH".equals(mode));
        btnClaim.setText(admin ? "Record Handover" : "Record Claim");
        btnClaim.setEnabled(false);
        txtDetails.setEditable(false);
        tblItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblItems.setRowHeight(25);
        tblItems.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblItems.getSelectionModel().addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                if (evt.getValueIsAdjusting()) return;
                int row = tblItems.getSelectedRow();
                if (row >= 0 && row < items.size()) {
                    LostItemOperations item = items.get(row);
                    txtDetails.setText(item.details());
                    txtDetails.setCaretPosition(0);
                    btnClaim.setEnabled(item.canClaim());
                } else btnClaim.setEnabled(false);
            }
        });
        searched = !"SEARCH".equals(mode);
        reloadItems();
    }

    public void reloadItems() {
        try {
            String submitter = "MINE".equals(mode) ? user.username : "";
            String claimant = "HISTORY".equals(mode) && !"ADMIN".equals(user.userType) ? user.username : "";
            boolean history = "HISTORY".equals(mode);
            items.clear();
            total = 0;
            if (searched) {
                total = LostItemOperations.countItems(nameFilter, categoryFilter, locationFilter, statusFilter,
                        submitter, claimant, history, keywordFilter);
                page = Math.min(page, Math.max(1, (total + PAGE_SIZE - 1) / PAGE_SIZE));
                items = LostItemOperations.searchItems(nameFilter, categoryFilter, locationFilter, statusFilter,
                        submitter, claimant, history, keywordFilter, page, PAGE_SIZE, cmbSort.getSelectedIndex() == 1);
            }
            String[] columns = {"ID", "Item Name", "Category", "Found Location", "Date Found", "Found By", "Status", "Claimant", "Claim Date"};
            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(columns, 0) {
                public boolean isCellEditable(int row, int column) { return false; }
            };
            for (LostItemOperations item : items) {
                model.addRow(new Object[]{item.id, item.itemName, item.category, item.pickupLocation,
                    item.pickupTime, item.pickupPerson, item.status, item.claimant, item.claimTime});
            }
            tblItems.setModel(model);
            int[] widths = {50, 170, 180, 200, 170, 120, 110, 120, 170};
            for (int i=0; i<widths.length; i++) tblItems.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
            lblPage.setText("Page " + page + " of " + Math.max(1, (total + PAGE_SIZE - 1) / PAGE_SIZE) + " (" + total + " items)");
            btnPrevious.setEnabled(searched && page > 1);
            btnNext.setEnabled(searched && page * PAGE_SIZE < total);
            btnClaim.setEnabled(false);
            txtDetails.setText(searched ? "Select a record to view its details." : "Enter search criteria and click Search, or use keyword search.");
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
    }

    private LostItemOperations selectedItem() throws SQLException {
        int row = tblItems.getSelectedRow();
        if (row < 0 || row >= items.size()) {
            JOptionPane.showMessageDialog(this, "Select a record first.");
            return null;
        }
        LostItemOperations item = LostItemOperations.searchItem(items.get(row).id);
        if (item == null) JOptionPane.showMessageDialog(this, "This report was deleted. Refresh the results.");
        return item;
    }

    private void openForm(JInternalFrame form) {
        getDesktopPane().add(form);
        form.setLocation(25, 25);
        form.setVisible(true);
        form.toFront();
        try { form.setSelected(true); } catch (java.beans.PropertyVetoException e) { System.out.println(e); }
    }

    /** NetBeans GUI Builder layout; event code is below this method. */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblLocation = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        lblCategory = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        cmbSort = new javax.swing.JComboBox<>();
        lblKeyword = new javax.swing.JLabel();
        txtKeyword = new javax.swing.JTextField();
        btnKeyword = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        scrollItems = new javax.swing.JScrollPane();
        tblItems = new javax.swing.JTable();
        Details = new javax.swing.JScrollPane();
        txtDetails = new javax.swing.JTextArea();
        btnPrevious = new javax.swing.JButton();
        lblPage = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnClaim = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setTitle("Search Lost Items");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        lblTitle.setText("Search Lost Items");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblName.setText("Name:");
        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblLocation.setText("Location:");
        lblLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnSearch.setText("Search");
        btnSearch.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        lblCategory.setText("Category:");
        lblCategory.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbCategory.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"All", "Electronics", "Identification", "Clothing & Accessories", "Study Supplies", "Everyday Items", "Valuables", "Other"}));
        lblStatus.setText("Status:");
        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"All", "Unclaimed", "Claimed", "Handed Over"}));
        cmbSort.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbSort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Newest first", "Oldest first"}));
        lblKeyword.setText("Keyword:");
        lblKeyword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtKeyword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnKeyword.setText("Keyword Search");
        btnKeyword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnKeyword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKeywordActionPerformed(evt);
            }
        });
        btnClear.setText("Clear Results");
        btnClear.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        tblItems.setFont(new java.awt.Font("Segoe UI", 0, 14));
        scrollItems.setViewportView(tblItems);
        txtDetails.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        Details.setViewportView(txtDetails);
        btnPrevious.setText("Previous");
        btnPrevious.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });
        lblPage.setText("Page 1");
        lblPage.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnNext.setText("Next");
        btnNext.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        btnRefresh.setText("Refresh");
        btnRefresh.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        btnEdit.setText("Edit");
        btnEdit.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        btnDelete.setText("Delete");
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        btnClaim.setText("Record Claim");
        btnClaim.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnClaim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClaimActionPerformed(evt);
            }
        });
        btnClose.setText("Close");
        btnClose.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(12).addGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(scrollItems, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(Details, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnClaim, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
                )
).addGap(12)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbSort, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnKeyword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(scrollItems, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(Details, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblPage, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClaim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGap(2)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        nameFilter = txtName.getText().trim();
        locationFilter = txtLocation.getText().trim();
        categoryFilter = cmbCategory.getSelectedItem().toString();
        statusFilter = cmbStatus.getSelectedItem().toString();
        keywordFilter = "";
        searched = true;
        page = 1;
        reloadItems();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnKeywordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKeywordActionPerformed
        if (txtKeyword.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a keyword to search.");
            return;
        }
        keywordFilter = txtKeyword.getText().trim();
        nameFilter = locationFilter = "";
        categoryFilter = statusFilter = "All";
        searched = true;
        page = 1;
        reloadItems();
    }//GEN-LAST:event_btnKeywordActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        searched = false;
        page = 1;
        reloadItems();
    }//GEN-LAST:event_btnClearActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        if (page > 1) { page--; reloadItems(); }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        if (page * PAGE_SIZE < total) { page++; reloadItems(); }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        reloadItems();
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        try {
            LostItemOperations item = selectedItem();
            if (item == null) return;
            if (!item.canEdit(user)) {
                JOptionPane.showMessageDialog(this, "You can edit only your own unclaimed reports.");
                return;
            }
            openForm(new EditLostItem(user, item, this));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            LostItemOperations item = selectedItem();
            if (item == null) return;
            if (!item.canEdit(user)) {
                JOptionPane.showMessageDialog(this, "You can delete only your own unclaimed reports.");
                return;
            }
            openForm(new DeleteLostItem(user, item, this));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnClaimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClaimActionPerformed
        try {
            LostItemOperations item = selectedItem();
            if (item == null) return;
            if (!item.canClaim()) {
                JOptionPane.showMessageDialog(this, "This item is no longer available to claim or hand over.");
                return;
            }
            openForm(new ClaimItem(user, item, this));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnClaimActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblName;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JButton btnSearch;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JComboBox<String> cmbSort;
    private javax.swing.JLabel lblKeyword;
    private javax.swing.JTextField txtKeyword;
    private javax.swing.JButton btnKeyword;
    private javax.swing.JButton btnClear;
    private javax.swing.JScrollPane scrollItems;
    private javax.swing.JTable tblItems;
    private javax.swing.JScrollPane Details;
    private javax.swing.JTextArea txtDetails;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JLabel lblPage;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnClaim;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
