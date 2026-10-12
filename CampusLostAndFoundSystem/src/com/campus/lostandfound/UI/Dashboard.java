package com.campus.lostandfound.UI;

import com.campus.lostandfound.DAO.UserDAO;
import com.campus.lostandfound.DTO.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDateTime;

// Main window after login
public class Dashboard extends javax.swing.JFrame {

    private final CardLayout layout = new CardLayout();
    private final String username;
    private final String userType;
    private final UserDTO userDTO;
    private JPanel navPanel;
    private JPanel displayPanel;
    private JLabel nameLabel;
    private UserDTO currentProfile;

    public Dashboard(String username, String userType, UserDTO userDTO) {
        this.username = username;
        this.userType = userType;
        this.userDTO = userDTO;

        initComponents();
        currentUserSession();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                recordLogout();
                super.windowClosing(e);
            }
        });
        setTitle("Campus Lost & Found System");
        setVisible(true);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(220, 70, 0, 0));
        setMinimumSize(new Dimension(1260, 760));

        Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
        JPanel root = new JPanel(new BorderLayout(8, 0));
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        navPanel = new JPanel();
        displayPanel = new JPanel(layout);

        JButton menuButton = new JButton("Menu");
        menuButton.setFont(uiFont);
        menuButton.addActionListener(evt -> navPanel.setVisible(!navPanel.isVisible()));
        nameLabel = new JLabel("User: ");
        nameLabel.setFont(uiFont);
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setFont(uiFont);
        logoutButton.addActionListener(evt -> logout());

        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        topPanel.add(menuButton, BorderLayout.WEST);
        topRightPanel.add(nameLabel);
        topRightPanel.add(logoutButton);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(170, 0));
        navPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        buildPages();

        root.add(topPanel, BorderLayout.NORTH);
        root.add(navPanel, BorderLayout.WEST);
        root.add(displayPanel, BorderLayout.CENTER);
        setContentPane(root);
        pack();
    }

    private void buildPages() {
        boolean isAdmin = "ADMIN".equalsIgnoreCase(userType);
        addNavPage("User Profile", "Center", buildCenterPanel());
        addNavPage("Report a Lost Item", "Submit", new LostItemPage(username, userType, LostItemPage.MODE_SUBMIT));
        if (isAdmin) {
            addNavPage("Admin Management", "Admin", new LostItemPage(username, userType, LostItemPage.MODE_ADMIN));
            addNavPage("Claim & Handover History", "ClaimHistory", new LostItemPage(username, userType, LostItemPage.MODE_CLAIM_HISTORY));
        } else {
            addNavPage("My Reports", "Mine", new LostItemPage(username, userType, LostItemPage.MODE_MINE));
            addNavPage("Search Lost Items", "Search", new LostItemPage(username, userType, LostItemPage.MODE_SEARCH));
            addNavPage("My Claim History", "ClaimHistory", new LostItemPage(username, userType, LostItemPage.MODE_CLAIM_HISTORY));
        }
        layout.show(displayPanel, "Center");
    }

    private void addNavPage(String text, String key, JPanel page) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(146, 38));
        button.addActionListener(evt -> layout.show(displayPanel, key));
        navPanel.add(button);
        navPanel.add(Box.createVerticalStrut(8));
        displayPanel.add(key, page);
    }

    private JPanel buildCenterPanel() {
        currentProfile = new UserDAO().getUserProfile(username);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(36, 40, 36, 40));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JLabel title = new JLabel("User Profile");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        panel.add(title, c);

        addProfileRow(panel, c, 1, "Name:", currentProfile.getFullName());
        addProfileRow(panel, c, 2, "Location:", currentProfile.getLocation());
        addProfileRow(panel, c, 3, "Phone:", currentProfile.getPhone());
        addProfileRow(panel, c, 4, "Student ID / Username:", currentProfile.getUsername());
        addProfileRow(panel, c, 5, "Role:", currentProfile.getUserType());

        JPasswordField passwordField = new JPasswordField(currentProfile.getPassword());
        passwordField.setEditable(false);
        JButton eyeButton = new JButton("Show");
        eyeButton.setToolTipText("Show or hide password");
        eyeButton.addActionListener(evt -> passwordField.setEchoChar(passwordField.getEchoChar() == 0 ? '•' : (char) 0));
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 6;
        c.weightx = 0;
        panel.add(new JLabel("Password:"), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(passwordField, c);
        c.gridx = 2;
        c.weightx = 0;
        panel.add(eyeButton, c);

        JButton changePassButton = new JButton("Change Password");
        changePassButton.addActionListener(evt -> changePassword(passwordField));
        c.gridx = 1;
        c.gridy = 7;
        c.gridwidth = 1;
        panel.add(changePassButton, c);
        return panel;
    }

    private void addProfileRow(JPanel panel, GridBagConstraints c, int row, String label, String value) {
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        panel.add(new JLabel(label), c);
        JTextField field = new JTextField(value == null ? "" : value);
        field.setEditable(false);
        c.gridx = 1;
        c.gridwidth = 2;
        c.weightx = 1;
        panel.add(field, c);
    }

    private void changePassword(JPasswordField passwordField) {
        JPasswordField oldPass = new JPasswordField();
        JPasswordField newPass = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        panel.add(new JLabel("Current Password:"));
        panel.add(oldPass);
        panel.add(new JLabel("New Password:"));
        panel.add(newPass);
        int result = JOptionPane.showConfirmDialog(this, panel, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String oldValue = new String(oldPass.getPassword()).trim();
            String newValue = new String(newPass.getPassword()).trim();
            if (oldValue.isEmpty() || newValue.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter both your current and new passwords.");
                return;
            }
            if (new UserDAO().changePass(username, oldValue, newValue)) {
                passwordField.setText(newValue);
            }
        }
    }

    private void logout() {
        int opt = JOptionPane.showConfirmDialog(
                null,
                "<html>Are you sure you want to log out?<br>You will need to log in again.</html>",
                "Confirm",
                JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            recordLogout();
            dispose();
            new LoginPage().setVisible(true);
        }
    }

    private void recordLogout() {
        userDTO.setOutTime(String.valueOf(LocalDateTime.now()));
        userDTO.setUsername(username);
        new UserDAO().addUserLogin(userDTO);
    }

    public void currentUserSession() {
        UserDTO currentUser = new UserDTO();
        new UserDAO().getFullName(currentUser, username);
        nameLabel.setText("User: " + currentUser.getFullName() + " (" + userType + ")");
    }
}
