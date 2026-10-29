package campus.lost.and.found.system;

import java.sql.*;
import javax.swing.*;

public class RegisterUser extends javax.swing.JFrame {
    public RegisterUser() {
        initComponents();
        setLocationRelativeTo(null);
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
        lblPhone = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblHint = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setTitle("Register | Universal Ai University");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lblTitle.setText("Register a new account");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblName.setText("Name:");
        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblLocation.setText("Location:");
        lblLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtLocation.setText("Karjat, Mumbai, Maharashtra, India");
        txtLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblPhone.setText("Phone:");
        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblUsername.setText("Student ID / Username:");
        lblUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblPassword.setText("Password:");
        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        lblHint.setText("New accounts have the USER role.");
        lblHint.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnRegister.setText("Register");
        btnRegister.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
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
                    .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 460, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblHint, javax.swing.GroupLayout.PREFERRED_SIZE, 460, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblHint, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGap(2)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        try {
            UserOperations user = new UserOperations(txtName.getText().trim(), txtLocation.getText().trim(),
                    txtPhone.getText().trim(), txtUsername.getText().trim(),
                    new String(txtPassword.getPassword()).trim(), "USER");
            user.registerUser();
            JOptionPane.showMessageDialog(this, "Registration successful. Please log in with your new account.");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblName;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel lblLocation;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JLabel lblPhone;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JTextField txtUsername;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JLabel lblHint;
    private javax.swing.JButton btnRegister;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
