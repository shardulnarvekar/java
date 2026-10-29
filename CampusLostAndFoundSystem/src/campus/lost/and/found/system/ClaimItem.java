package campus.lost.and.found.system;

import java.sql.*;
import javax.swing.*;

public class ClaimItem extends javax.swing.JInternalFrame {
    private UserOperations user;
    private LostItemOperations item;
    private SearchLostItem parent;

    public ClaimItem() { initComponents(); }
    public ClaimItem(UserOperations user, LostItemOperations item, SearchLostItem parent) {
        this();
        this.user = user;
        this.item = item;
        this.parent = parent;
        boolean admin = "ADMIN".equals(user.userType);
        String title = admin ? "Record Handover" : "Record Claim";
        setTitle(title);
        lblTitle.setText(title);
        btnSave.setText(title);
        txtItem.setText(item.itemName);
        txtRecipient.setEnabled(admin);
        txtName.setEnabled(!admin);
        txtContact.setEnabled(!admin);
        if (!admin) {
            txtRecipient.setText(user.username);
            txtName.setText(user.name);
            txtContact.setText(user.phone);
        }
        txtDate.setText(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    /** NetBeans GUI Builder layout; event code is below this method. */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        lblItem = new javax.swing.JLabel();
        txtItem = new javax.swing.JTextField();
        lblRecipient = new javax.swing.JLabel();
        txtRecipient = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblContact = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setTitle("Record Claim / Handover");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        lblTitle.setText("Record Claim / Handover");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblItem.setText("Item:");
        lblItem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtItem.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtItem.setEditable(false);
        lblRecipient.setText("Recipient Student ID:");
        lblRecipient.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtRecipient.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblName.setText("Claimant:");
        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblContact.setText("Contact:");
        lblContact.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtContact.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblDate.setText("Claim / Handover Date:");
        lblDate.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtDate.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtDate.setEditable(false);
        btnSave.setText("Record Claim");
        btnSave.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        btnClose.setText("Cancel");
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
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblItem, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblRecipient, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtRecipient, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(lblItem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblRecipient, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtRecipient, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGap(2)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            boolean changed;
            if ("ADMIN".equals(user.userType)) {
                changed = LostItemOperations.handoverItem(item.id, user.username, txtRecipient.getText().trim());
            } else {
                changed = LostItemOperations.claimItem(item.id, user.username, txtName.getText().trim(), txtContact.getText().trim());
            }
            if (changed) {
                JOptionPane.showMessageDialog(this, "Claim / handover recorded successfully.");
                parent.reloadItems();
                dispose();
            } else JOptionPane.showMessageDialog(this, "This item is no longer available. Refresh and try again.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblItem;
    private javax.swing.JTextField txtItem;
    private javax.swing.JLabel lblRecipient;
    private javax.swing.JTextField txtRecipient;
    private javax.swing.JLabel lblName;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel lblContact;
    private javax.swing.JTextField txtContact;
    private javax.swing.JLabel lblDate;
    private javax.swing.JTextField txtDate;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
