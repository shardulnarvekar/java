package campus.lost.and.found.system;

import java.sql.*;
import javax.swing.*;

public class DeleteLostItem extends javax.swing.JInternalFrame {
    private UserOperations user;
    private LostItemOperations item;
    private SearchLostItem parent;

    public DeleteLostItem() { initComponents(); txtDetails.setEditable(false); }
    public DeleteLostItem(UserOperations user, LostItemOperations item, SearchLostItem parent) {
        this();
        this.user = user;
        this.item = item;
        this.parent = parent;
        txtDetails.setText(item.details());
    }

    /** NetBeans GUI Builder layout; event code is below this method. */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        Details = new javax.swing.JScrollPane();
        txtDetails = new javax.swing.JTextArea();
        btnDelete = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setTitle("Delete Lost Item");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        lblTitle.setText("Delete Lost Item");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        txtDetails.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtDetails.setLineWrap(true);
        txtDetails.setWrapStyleWord(true);
        Details.setViewportView(txtDetails);
        btnDelete.setText("Delete Report");
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
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
                    .addComponent(Details, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(Details, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGap(2)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            if (JOptionPane.showConfirmDialog(this, "Delete this report?", "Confirm",
                    JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
            if (LostItemOperations.deleteItem(item.id, user.username)) {
                JOptionPane.showMessageDialog(this, "Lost item report deleted.");
                parent.reloadItems();
                dispose();
            } else JOptionPane.showMessageDialog(this, "You can delete only your own unclaimed reports. Refresh and try again.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JScrollPane Details;
    private javax.swing.JTextArea txtDetails;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
