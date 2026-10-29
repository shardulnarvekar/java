package campus.lost.and.found.system;

import java.sql.*;
import javax.swing.*;

public class MainFrame extends javax.swing.JFrame {
    private UserOperations user;
    private String inTime;
    private boolean loggedOut;

    public MainFrame() { initComponents(); }

    public MainFrame(UserOperations user) {
        this.user = user;
        inTime = java.time.LocalDateTime.now().toString();
        initComponents();
        lblUser.setText("Universal Ai University | " + user.name + " (" + user.userType + ")");
        mineMenu.setVisible(!"ADMIN".equals(user.userType));
        searchMenu.setText("ADMIN".equals(user.userType) ? "Admin Management" : "Search Lost Items");
        historyMenu.setText("ADMIN".equals(user.userType) ? "Claim & Handover History" : "My Claim History");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) { closeSession(false); }
        });
        setLocationRelativeTo(null);
        openForm(new UserProfile(user));
    }

    private void openForm(JInternalFrame form) {
        desktopPane.add(form);
        form.setVisible(true);
        try { form.setSelected(true); } catch (java.beans.PropertyVetoException e) { System.out.println(e); }
    }

    private void closeSession(boolean returnToLogin) {
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?",
                "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        if (!loggedOut && user != null) {
            try {
                UserOperations.recordLogout(user.username, inTime, java.time.LocalDateTime.now().toString());
                loggedOut = true;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Could not save the session log: " + e.getMessage());
            }
        }
        dispose();
        if (returnToLogin) new LoginForm().setVisible(true);
    }

    /** NetBeans GUI Builder layout; event code is below this method. */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        lblUser = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        desktopPane = new javax.swing.JDesktopPane();
        menuBar = new javax.swing.JMenuBar();
        reportsMenu = new javax.swing.JMenu("Lost & Found");
        newMenu = new javax.swing.JMenuItem();
        mineMenu = new javax.swing.JMenuItem();
        searchMenu = new javax.swing.JMenuItem();
        historyMenu = new javax.swing.JMenuItem();
        accountMenu = new javax.swing.JMenu("Account");
        profileMenu = new javax.swing.JMenuItem();
        logoutMenu = new javax.swing.JMenuItem();
        exitMenu = new javax.swing.JMenuItem();

        setTitle("Universal Ai University | Lost & Found");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        lblUser.setText("Universal Ai University");
        lblUser.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnLogout.setText("Log Out");
        btnLogout.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });
        newMenu.setText("Report a Lost Item");
        newMenu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        newMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newMenuActionPerformed(evt);
            }
        });
        reportsMenu.add(newMenu);
        mineMenu.setText("My Reports");
        mineMenu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        mineMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mineMenuActionPerformed(evt);
            }
        });
        reportsMenu.add(mineMenu);
        searchMenu.setText("Search Lost Items");
        searchMenu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        searchMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMenuActionPerformed(evt);
            }
        });
        reportsMenu.add(searchMenu);
        historyMenu.setText("My Claim History");
        historyMenu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        historyMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historyMenuActionPerformed(evt);
            }
        });
        reportsMenu.add(historyMenu);
        menuBar.add(reportsMenu);
        profileMenu.setText("User Profile");
        profileMenu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        profileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileMenuActionPerformed(evt);
            }
        });
        accountMenu.add(profileMenu);
        logoutMenu.setText("Log Out");
        logoutMenu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        logoutMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutMenuActionPerformed(evt);
            }
        });
        accountMenu.add(logoutMenu);
        exitMenu.setText("Exit");
        exitMenu.setFont(new java.awt.Font("Segoe UI", 0, 14));
        exitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuActionPerformed(evt);
            }
        });
        accountMenu.add(exitMenu);
        menuBar.add(accountMenu);
        setJMenuBar(menuBar);
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(12).addGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(10)
                    .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
                )
                .addGroup(layout.createSequentialGroup()
                    .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, Short.MAX_VALUE)
                )
).addGap(12)));
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup().addGap(12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
            ).addGap(10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(desktopPane, javax.swing.GroupLayout.PREFERRED_SIZE, 620, Short.MAX_VALUE)
            ).addGap(10)
            .addGap(2)));
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        closeSession(true);
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void profileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileMenuActionPerformed
        openForm(new UserProfile(user));
    }//GEN-LAST:event_profileMenuActionPerformed

    private void newMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newMenuActionPerformed
        openForm(new NewLostItem(user));
    }//GEN-LAST:event_newMenuActionPerformed

    private void mineMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mineMenuActionPerformed
        openForm(new SearchLostItem(user, "MINE"));
    }//GEN-LAST:event_mineMenuActionPerformed

    private void searchMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMenuActionPerformed
        openForm(new SearchLostItem(user, "SEARCH"));
    }//GEN-LAST:event_searchMenuActionPerformed

    private void historyMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historyMenuActionPerformed
        openForm(new SearchLostItem(user, "HISTORY"));
    }//GEN-LAST:event_historyMenuActionPerformed

    private void logoutMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutMenuActionPerformed
        closeSession(true);
    }//GEN-LAST:event_logoutMenuActionPerformed

    private void exitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuActionPerformed
        closeSession(false);
    }//GEN-LAST:event_exitMenuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblUser;
    private javax.swing.JButton btnLogout;
    private javax.swing.JDesktopPane desktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu reportsMenu;
    private javax.swing.JMenuItem newMenu;
    private javax.swing.JMenuItem mineMenu;
    private javax.swing.JMenuItem searchMenu;
    private javax.swing.JMenuItem historyMenu;
    private javax.swing.JMenu accountMenu;
    private javax.swing.JMenuItem profileMenu;
    private javax.swing.JMenuItem logoutMenu;
    private javax.swing.JMenuItem exitMenu;
    // End of variables declaration//GEN-END:variables
}
