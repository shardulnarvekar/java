package com.campus.lostandfound.UI;

import com.campus.lostandfound.DAO.UserDAO;
import com.campus.lostandfound.DTO.UserDTO;
import com.campus.lostandfound.Database.ConnectionFactory;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialDarkerIJTheme;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class LoginPage extends javax.swing.JFrame {

    private JTextField loginUserText;
    private JPasswordField loginPassText;
    private JComboBox<String> loginRoleCombo;
    private JTextField regNameText;
    private JTextField regLocationText;
    private JTextField regPhoneText;
    private JTextField regUserText;
    private JPasswordField regPassText;

    public LoginPage() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Campus Lost & Found System");
        setBounds(new java.awt.Rectangle(500, 120, 0, 0));
        setMinimumSize(new Dimension(430, 470));

        Font titleFont = new Font("Segoe UI", Font.BOLD, 24);
        Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel title = new JLabel("Campus Lost & Found System", SwingConstants.CENTER);
        title.setFont(titleFont);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(uiFont);
        tabs.addTab("Log In", buildLoginPanel(uiFont));
        tabs.addTab("Register", buildRegisterPanel(uiFont));

        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
        root.add(title, BorderLayout.NORTH);
        root.add(tabs, BorderLayout.CENTER);
        setContentPane(root);
        pack();
    }

    private JPanel buildLoginPanel(Font uiFont) {
        JPanel panel = new JPanel();
        JLabel userLabel = new JLabel("Student ID / Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel roleLabel = new JLabel("Role:");
        loginUserText = new JTextField();
        loginPassText = new JPasswordField();
        loginRoleCombo = new JComboBox<>(new String[]{"USER", "ADMIN"});
        JButton loginButton = new JButton("Log In");
        JButton clearButton = new JButton("Clear");

        userLabel.setFont(uiFont);
        passLabel.setFont(uiFont);
        roleLabel.setFont(uiFont);
        loginButton.setFont(uiFont);
        clearButton.setFont(uiFont);
        loginPassText.addActionListener(evt -> login());
        loginButton.addActionListener(evt -> login());
        clearButton.addActionListener(evt -> {
            loginUserText.setText("");
            loginPassText.setText("");
        });

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(userLabel).addComponent(passLabel).addComponent(roleLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(loginUserText)
                                        .addComponent(loginPassText)
                                        .addComponent(loginRoleCombo)))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(loginButton)
                                .addComponent(clearButton))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(userLabel).addComponent(loginUserText))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passLabel).addComponent(loginPassText))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(roleLabel).addComponent(loginRoleCombo))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(loginButton).addComponent(clearButton))
        );
        return panel;
    }

    private JPanel buildRegisterPanel(Font uiFont) {
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("Name:");
        JLabel locationLabel = new JLabel("Location:");
        JLabel phoneLabel = new JLabel("Phone:");
        JLabel userLabel = new JLabel("Student ID / Username:");
        JLabel passLabel = new JLabel("Password:");
        JLabel hintLabel = new JLabel("New accounts have the USER role.");
        regNameText = new JTextField();
        regLocationText = new JTextField("Campus");
        regPhoneText = new JTextField();
        regUserText = new JTextField();
        regPassText = new JPasswordField();
        JButton registerButton = new JButton("Register");

        nameLabel.setFont(uiFont);
        locationLabel.setFont(uiFont);
        phoneLabel.setFont(uiFont);
        userLabel.setFont(uiFont);
        passLabel.setFont(uiFont);
        hintLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        registerButton.setFont(uiFont);
        registerButton.addActionListener(evt -> register());

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(nameLabel).addComponent(locationLabel).addComponent(phoneLabel)
                                        .addComponent(userLabel).addComponent(passLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(regNameText)
                                        .addComponent(regLocationText)
                                        .addComponent(regPhoneText)
                                        .addComponent(regUserText)
                                        .addComponent(regPassText)))
                        .addComponent(hintLabel)
                        .addComponent(registerButton)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(nameLabel).addComponent(regNameText))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(locationLabel).addComponent(regLocationText))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(phoneLabel).addComponent(regPhoneText))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(userLabel).addComponent(regUserText))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(passLabel).addComponent(regPassText))
                        .addComponent(hintLabel)
                        .addComponent(registerButton)
        );
        return panel;
    }

    private void login() {
        String username = loginUserText.getText().trim();
        String password = new String(loginPassText.getPassword()).trim();
        String userType = String.valueOf(loginRoleCombo.getSelectedItem());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter your username and password.");
            return;
        }

        if (new ConnectionFactory().checkLogin(username, password, userType)) {
            UserDTO userDTO = new UserDTO();
            userDTO.setInTime(String.valueOf(LocalDateTime.now()));
            dispose();
            new Dashboard(username, userType, userDTO);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect username, password, or role.");
        }
    }

    private void register() {
        String name = regNameText.getText().trim();
        String location = regLocationText.getText().trim();
        String phone = regPhoneText.getText().trim();
        String username = regUserText.getText().trim();
        String password = new String(regPassText.getPassword()).trim();

        if (name.isEmpty() || location.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete all registration fields.");
            return;
        }

        UserDTO dto = new UserDTO();
        dto.setFullName(name);
        dto.setLocation(location);
        dto.setPhone(phone);
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setUserType("USER");
        if (new UserDAO().registerUser(dto)) {
            loginUserText.setText(username);
            loginRoleCombo.setSelectedItem("USER");
        }
    }

    public static void main(String[] args) {
        java.util.Locale.setDefault(java.util.Locale.ENGLISH);
        try {
            javax.swing.UIManager.setLookAndFeel(new FlatMaterialDarkerIJTheme());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> new LoginPage().setVisible(true));
    }
}
