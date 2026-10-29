package campus.lost.and.found.system;

import java.sql.*;
import javax.swing.*;

public class UserProfile extends javax.swing.JInternalFrame {
    private UserOperations user;

    public UserProfile() { initComponents(); }

    public UserProfile(UserOperations currentUser) {
        initComponents();
        try {
            user = UserOperations.searchUser(currentUser.username);
            if (user == null) throw new SQLException("User account no longer exists.");
            txtName.setText(user.name);
            txtLocation.setText(user.location);
            txtPhone.setText(user.phone);
            txtUsername.setText(user.username);
            txtRole.setText(user.userType);
            txtPassword.setText(user.password);
        } catch (SQLException e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
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
        lblRole = new javax.swing.JLabel();
        txtRole = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnShow = new javax.swing.JButton();
        btnChange = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();

        setTitle("User Profile");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        lblTitle.setText("User Profile");
        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 24));
        lblName.setText("Name:");
        lblName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtName.setEditable(false);
        lblLocation.setText("Location:");
        lblLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtLocation.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtLocation.setEditable(false);
        lblPhone.setText("Phone:");
        lblPhone.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPhone.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPhone.setEditable(false);
        lblUsername.setText("Student ID / Username:");
        lblUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtUsername.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtUsername.setEditable(false);
        lblRole.setText("Role:");
        lblRole.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtRole.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtRole.setEditable(false);
        lblPassword.setText("Password:");
        lblPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPassword.setFont(new java.awt.Font("Segoe UI", 0, 14));
        txtPassword.setEditable(false);
        btnShow.setText("Show");
        btnShow.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });
        btnChange.setText("Change Password");
        btnChange.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnChange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeActionPerformed(evt);
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
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 300, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addComponent(lblRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtRole, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnShow, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnClose, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGap(2)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        boolean show = txtPassword.getEchoChar() != 0;
        txtPassword.setEchoChar(show ? (char) 0 : '*');
        btnShow.setText(show ? "Hide" : "Show");
    }//GEN-LAST:event_btnShowActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
        try {
            JPasswordField oldPassword = new JPasswordField();
            JPasswordField newPassword = new JPasswordField();
            Object[] fields = {"Current Password:", oldPassword, "New Password:", newPassword};
            if (JOptionPane.showConfirmDialog(this, fields, "Change Password", JOptionPane.OK_CANCEL_OPTION)
                    != JOptionPane.OK_OPTION) return;
            String oldValue = new String(oldPassword.getPassword()).trim();
            String newValue = new String(newPassword.getPassword()).trim();
            if (oldValue.isEmpty() || newValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter both your current and new passwords.");
                return;
            }
            if (UserOperations.changePassword(user.username, oldValue, newValue)) {
                user.password = newValue;
                txtPassword.setText(newValue);
                JOptionPane.showMessageDialog(this, "Password changed successfully.");
            } else JOptionPane.showMessageDialog(this, "The current password is incorrect.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnChangeActionPerformed

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
    private javax.swing.JLabel lblRole;
    private javax.swing.JTextField txtRole;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JButton btnShow;
    private javax.swing.JButton btnChange;
    private javax.swing.JButton btnClose;
    // End of variables declaration//GEN-END:variables
}
