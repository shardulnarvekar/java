package campus.lost.and.found.system;

import java.sql.*;
import javax.swing.*;

public class NewLostItem extends javax.swing.JInternalFrame {
    private UserOperations user;
    private LostItemOperations source;
    private SearchLostItem parent;

    public NewLostItem() {
        initComponents();
        spnDate.setModel(new SpinnerDateModel());
        spnDate.setEditor(new JSpinner.DateEditor(spnDate, "yyyy-MM-dd HH:mm"));
        cmbStatus.setEnabled(false);
    }

    public NewLostItem(UserOperations user) {
        this();
        this.user = user;
        txtPerson.setText(user.name);
        txtContact.setText(user.phone);
    }

    /** NetBeans GUI Builder layout; event code is below this method. */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblTitle = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        lblCategory = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox<>();
        lblLocation = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        lblDate = new javax.swing.JLabel();
        spnDate = new javax.swing.JSpinner();
        lblPerson = new javax.swing.JLabel();
        txtPerson = new javax.swing.JTextField();
        lblContact = new javax.swing.JLabel();
        txtContact = new javax.swing.JTextField();
        lblStatus = new javax.swing.JLabel();
        cmbStatus = new javax.swing.JComboBox<>();
        lblDescription = new javax.swing.JLabel();
        Description = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        btnSave = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setTitle("Report a Lost Item");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        lblTitle.setText("Report a Lost Item");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblName.setText("Item Name:");
        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblCategory.setText("Category:");
        lblCategory.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbCategory.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Electronics", "Identification", "Clothing & Accessories", "Study Supplies", "Everyday Items", "Valuables", "Other"}));
        lblLocation.setText("Found Location:");
        lblLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblDate.setText("Date Found:");
        lblDate.setFont(new java.awt.Font("Segoe UI", 0, 14));
        spnDate.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblPerson.setText("Found By:");
        lblPerson.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPerson.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblContact.setText("Contact:");
        lblContact.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtContact.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblStatus.setText("Status:");
        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbStatus.setFont(new java.awt.Font("Segoe UI", 0, 14));
        cmbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"Unclaimed", "Claimed", "Handed Over"}));
        lblDescription.setText("Item Description:");
        lblDescription.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtDescription.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        Description.setViewportView(txtDescription);
        btnSave.setText("Submit Report");
        btnSave.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
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
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(Description, javax.swing.GroupLayout.PREFERRED_SIZE, 600, Short.MAX_VALUE)
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
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(spnDate, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPerson, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblContact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtContact, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(cmbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(Description, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGap(2)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            spnDate.commitEdit();
            LostItemOperations item = new LostItemOperations(0, txtName.getText().trim(),
                    cmbCategory.getSelectedItem().toString(), txtLocation.getText().trim(),
                    new Timestamp(((java.util.Date) spnDate.getValue()).getTime()),
                    txtDescription.getText().trim(), txtPerson.getText().trim(), txtContact.getText().trim(), user.username);
            item.addItem();
            JOptionPane.showMessageDialog(this, "Lost item report submitted successfully.");
            txtName.setText("");
            txtLocation.setText("");
            txtDescription.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblName;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel lblCategory;
    private javax.swing.JComboBox<String> cmbCategory;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JLabel lblDate;
    private javax.swing.JSpinner spnDate;
    private javax.swing.JLabel lblPerson;
    private javax.swing.JTextField txtPerson;
    private javax.swing.JLabel lblContact;
    private javax.swing.JTextField txtContact;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JComboBox<String> cmbStatus;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JScrollPane Description;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
