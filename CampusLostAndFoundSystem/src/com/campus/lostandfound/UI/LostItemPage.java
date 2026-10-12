package com.campus.lostandfound.UI;

import com.campus.lostandfound.DAO.LostItemDAO;
import com.campus.lostandfound.DAO.UserDAO;
import com.campus.lostandfound.DTO.LostItemDTO;
import com.campus.lostandfound.DTO.UserDTO;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class LostItemPage extends JPanel {

    public static final String MODE_SUBMIT = "SUBMIT";
    public static final String MODE_MINE = "MINE";
    public static final String MODE_SEARCH = "SEARCH";
    public static final String MODE_ADMIN = "ADMIN";
    public static final String MODE_CLAIM_HISTORY = "CLAIM_HISTORY";

    private static final int PAGE_SIZE = 18;
    private static final Color CLAIM_BLUE = new Color(90, 185, 255);
    private static final Color CLAIM_BLUE_DISABLED = new Color(165, 210, 235);
    private static final String[] CATEGORY_FILTER = {"All", "Electronics", "Identification", "Clothing & Accessories", "Study Supplies", "Everyday Items", "Valuables", "Other"};
    private static final String[] STATUS_FILTER = {"All", "Unclaimed", "Claimed", "Handed Over"};

    private final LostItemDAO dao = new LostItemDAO();
    private final String username;
    private final String userType;
    private final String mode;
    private final boolean admin;

    private JTable table;
    private JTextArea detailArea;
    private JTextField nameSearch;
    private JTextField locationSearch;
    private JComboBox<String> categorySearch;
    private JComboBox<String> statusSearch;
    private JTextField fuzzySearch;
    private JComboBox<String> orderCombo;
    private JLabel pageLabel;
    private JButton claimButton;
    private int page = 1;
    private int total = 0;
    private String lastSearchType = "NONE";
    private Integer selectedId = null;

    public LostItemPage(String username, String userType, String mode) {
        this.username = username;
        this.userType = userType;
        this.mode = mode;
        this.admin = "ADMIN".equalsIgnoreCase(userType);
        init();
    }

    private void init() {
        setLayout(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        if (MODE_SUBMIT.equals(mode)) {
            add(buildSubmitPanel(), BorderLayout.CENTER);
            return;
        }

        add(buildTopPanel(), BorderLayout.NORTH);
        add(buildTableAndDetail(), BorderLayout.CENTER);
        add(buildActionPanel(), BorderLayout.SOUTH);

        if (MODE_MINE.equals(mode)) {
            loadMine();
        } else if (MODE_CLAIM_HISTORY.equals(mode)) {
            loadClaimHistory();
        } else {
            clearTable("Enter search criteria and click Search, or use keyword search.");
        }
    }

    private JPanel buildSubmitPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 12));
        JLabel title = new JLabel("Report a Lost Item");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);
        panel.add(buildEditor(null, true), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        JLabel title = new JLabel(pageTitle());
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        if (MODE_MINE.equals(mode) || MODE_CLAIM_HISTORY.equals(mode)) {
            JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            orderCombo = new JComboBox<>(new String[]{"Newest first", "Oldest first"});
            JButton refreshSortButton = new JButton("Refresh");
            styleNormalButton(refreshSortButton);
            refreshSortButton.addActionListener(evt -> refreshByMode());
            sortPanel.add(new JLabel("Sort by time:"));
            sortPanel.add(orderCombo);
            sortPanel.add(refreshSortButton);
            panel.add(sortPanel, BorderLayout.CENTER);
            return panel;
        }

        JPanel search = new JPanel(new GridLayout(3, 1, 6, 6));
        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        nameSearch = new JTextField(12);
        locationSearch = new JTextField(12);
        categorySearch = new JComboBox<>(CATEGORY_FILTER);
        statusSearch = new JComboBox<>(STATUS_FILTER);
        fuzzySearch = new JTextField(18);
        orderCombo = new JComboBox<>(new String[]{"Newest first", "Oldest first"});
        JButton queryButton = new JButton("Search");
        JButton fuzzyButton = new JButton("Keyword Search");
        JButton clearButton = new JButton("Clear Results");
        styleNormalButton(queryButton);
        styleNormalButton(fuzzyButton);
        styleNormalButton(clearButton);

        row1.add(new JLabel("Name:"));
        row1.add(nameSearch);
        row1.add(new JLabel("Location:"));
        row1.add(locationSearch);
        row1.add(queryButton);
        row2.add(new JLabel("Category:"));
        row2.add(categorySearch);
        row2.add(new JLabel("Status:"));
        row2.add(statusSearch);
        row2.add(new JLabel("Sort by time:"));
        row2.add(orderCombo);
        row3.add(new JLabel("Keyword:"));
        row3.add(fuzzySearch);
        row3.add(fuzzyButton);
        row3.add(clearButton);
        search.add(row1);
        search.add(row2);
        search.add(row3);
        panel.add(search, BorderLayout.CENTER);

        queryButton.addActionListener(evt -> {
            page = 1;
            lastSearchType = "COMBO";
            loadComboSearch();
        });
        fuzzyButton.addActionListener(evt -> {
            page = 1;
            lastSearchType = "FUZZY";
            loadFuzzySearch();
        });
        clearButton.addActionListener(evt -> clearTable("Results cleared. Run a new search."));
        return panel;
    }

    private JPanel buildTableAndDetail() {
        JPanel panel = new JPanel(new GridLayout(1, 2, 12, 0));
        table = new JTable();
        table.setRowHeight(24);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) showSelectedDetail();
        });

        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);
        detailArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailArea.setText("Select a record to view its details.");

        panel.add(new JScrollPane(table));
        panel.add(new JScrollPane(detailArea));
        return panel;
    }

    private JPanel buildActionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");
        JButton refreshButton = new JButton("Refresh");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        claimButton = new JButton(admin ? "Record Handover" : "Record Claim");
        styleNormalButton(prevButton);
        styleNormalButton(nextButton);
        styleNormalButton(refreshButton);
        styleNormalButton(editButton);
        styleNormalButton(deleteButton);
        styleClaimButton(claimButton);
        setClaimButtonEnabled(false);
        pageLabel = new JLabel("Page 1");
        pageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        panel.add(prevButton);
        panel.add(nextButton);
        panel.add(pageLabel);
        panel.add(refreshButton);
        if (MODE_MINE.equals(mode) || MODE_ADMIN.equals(mode)) {
            panel.add(editButton);
            panel.add(deleteButton);
        }
        if (!MODE_MINE.equals(mode) && !MODE_CLAIM_HISTORY.equals(mode)) {
            panel.add(claimButton);
        }

        prevButton.addActionListener(evt -> {
            if (page > 1) {
                page--;
                reloadCurrent();
            }
        });
        nextButton.addActionListener(evt -> {
            if (page * PAGE_SIZE < total) {
                page++;
                reloadCurrent();
            }
        });
        refreshButton.addActionListener(evt -> refreshByMode());
        editButton.addActionListener(evt -> editSelected());
        deleteButton.addActionListener(evt -> deleteSelected());
        claimButton.addActionListener(evt -> claimSelected());
        return panel;
    }

    private void styleNormalButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(112, 40));
    }

    private void styleClaimButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setPreferredSize(new Dimension(160, 44));
        button.setForeground(CLAIM_BLUE);
        button.setBackground(new Color(245, 252, 255));
        button.setBorder(BorderFactory.createLineBorder(CLAIM_BLUE, 2));
        button.setOpaque(true);
        button.setFocusPainted(false);
    }

    private void setClaimButtonEnabled(boolean enabled) {
        if (claimButton == null) return;
        claimButton.setEnabled(enabled);
        claimButton.setForeground(enabled ? CLAIM_BLUE : CLAIM_BLUE_DISABLED);
        claimButton.setBackground(enabled ? new Color(245, 252, 255) : new Color(238, 246, 250));
        claimButton.setBorder(BorderFactory.createLineBorder(enabled ? CLAIM_BLUE : CLAIM_BLUE_DISABLED, 2));
    }

    private String pageTitle() {
        if (MODE_MINE.equals(mode)) return admin ? "Manage Reports" : "My Reports";
        if (MODE_ADMIN.equals(mode)) return "Manage Lost Items";
        if (MODE_CLAIM_HISTORY.equals(mode)) return admin ? "Claim & Handover History" : "My Claim History";
        return "Search Lost Items";
    }

    private void refreshByMode() {
        if (MODE_MINE.equals(mode)) loadMine();
        else if (MODE_CLAIM_HISTORY.equals(mode)) loadClaimHistory();
        else reloadCurrent();
    }

    private void reloadCurrent() {
        if ("FUZZY".equals(lastSearchType)) loadFuzzySearch();
        else if ("COMBO".equals(lastSearchType)) loadComboSearch();
        else if (MODE_MINE.equals(mode)) loadMine();
        else if (MODE_CLAIM_HISTORY.equals(mode)) loadClaimHistory();
        else clearTable("Enter search criteria and click Search, or use keyword search.");
    }

    private void loadMine() {
        try {
            ResultSet rs = dao.getBySubmitter(username, currentOrder());
            fillTable(rs);
            total = table.getRowCount();
            pageLabel.setText("My reports: " + total + " items");
        } catch (SQLException e) {
            showError("Could not load your reports.");
        }
    }

    private void loadClaimHistory() {
        try {
            String claimant = admin ? null : username;
            total = dao.countSearch(null, "All", null, "All", null, claimant, true);
            ResultSet rs = dao.searchLostItems(null, "All", null, "All", null, claimant, true, page, PAGE_SIZE, currentOrder());
            fillTable(rs);
            updatePageLabel();
        } catch (SQLException e) {
            showError("Could not load claim history.");
        }
    }

    private void loadComboSearch() {
        try {
            String submitter = MODE_MINE.equals(mode) ? username : null;
            total = dao.countSearch(nameSearch.getText(), String.valueOf(categorySearch.getSelectedItem()),
                    locationSearch.getText(), String.valueOf(statusSearch.getSelectedItem()), submitter, null, false);
            ResultSet rs = dao.searchLostItems(nameSearch.getText(), String.valueOf(categorySearch.getSelectedItem()),
                    locationSearch.getText(), String.valueOf(statusSearch.getSelectedItem()), submitter, null, false, page, PAGE_SIZE, currentOrder());
            fillTable(rs);
            updatePageLabel();
        } catch (SQLException e) {
            showError("Search failed.");
        }
    }

    private void loadFuzzySearch() {
        String keyword = fuzzySearch.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a keyword to search.");
            return;
        }
        try {
            String submitter = MODE_MINE.equals(mode) ? username : null;
            total = dao.countFuzzy(keyword, submitter, null, false);
            ResultSet rs = dao.fuzzySearch(keyword, submitter, null, false, page, PAGE_SIZE, currentOrder());
            fillTable(rs);
            updatePageLabel();
        } catch (SQLException e) {
            showError("Keyword search failed.");
        }
    }

    private void fillTable(ResultSet rs) throws SQLException {
        String[] columns = {"ID", "Item Name", "Category", "Found Location", "Date Found", "Found By", "Status", "Claimant", "Claim Date", "Submitted By", "Contact", "Description", "Claimant Contact", "Claimant Account"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        while (rs.next()) {
            Timestamp pickup = rs.getTimestamp("pickup_time");
            Timestamp claim = rs.getTimestamp("claim_time");
            model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("item_name"),
                    rs.getString("category"),
                    rs.getString("pickup_location"),
                    pickup == null ? "" : fmt.format(pickup.toLocalDateTime()),
                    rs.getString("pickup_person"),
                    rs.getString("status"),
                    rs.getString("claimant"),
                    claim == null ? "" : fmt.format(claim.toLocalDateTime()),
                    rs.getString("submitter_username"),
                    rs.getString("contact"),
                    rs.getString("description"),
                    rs.getString("claim_contact"),
                    rs.getString("claimant_username")
            });
        }
        table.setModel(model);
        hideColumn(0);
        hideColumn(9);
        hideColumn(10);
        hideColumn(11);
        hideColumn(12);
        hideColumn(13);
        configureVisibleColumns();
        selectedId = null;
        setClaimButtonEnabled(false);
        detailArea.setText("Select a record to view its details.");
    }

    private void clearTable(String message) {
        if (table == null) return;
        table.setModel(new DefaultTableModel(new String[]{"Item Name", "Category", "Found Location", "Date Found", "Status"}, 0));
        detailArea.setText(message);
        selectedId = null;
        total = 0;
        page = 1;
        setClaimButtonEnabled(false);
        if (pageLabel != null) pageLabel.setText("Page 1");
    }

    private void showSelectedDetail() {
        int row = table.getSelectedRow();
        if (row < 0 || table.getColumnCount() < 14) {
            selectedId = null;
            setClaimButtonEnabled(false);
            return;
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        selectedId = (Integer) model.getValueAt(row, 0);
        setClaimButtonEnabled(canClaimFromModel(model, row));
        detailArea.setText(
                "Item Name: " + model.getValueAt(row, 1) + "\n"
                        + "Category: " + model.getValueAt(row, 2) + "\n"
                        + "Found Location: " + model.getValueAt(row, 3) + "\n"
                        + "Date Found: " + model.getValueAt(row, 4) + "\n"
                        + "Found By: " + model.getValueAt(row, 5) + "\n"
                        + "Status: " + model.getValueAt(row, 6) + "\n"
                        + "Contact: " + model.getValueAt(row, 10) + "\n"
                        + "Item Description: " + model.getValueAt(row, 11) + "\n"
                        + "Claimant: " + nullText(model.getValueAt(row, 7)) + "\n"
                        + "Claimant Contact: " + nullText(model.getValueAt(row, 12)) + "\n"
                        + "Claim Date: " + nullText(model.getValueAt(row, 8))
        );
    }

    private JPanel buildEditor(LostItemDTO source, boolean submitMode) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;

        JTextField itemName = new JTextField(source == null ? "" : source.getItemName());
        JComboBox<String> category = new JComboBox<>(LostItemDTO.DEFAULT_CATEGORIES);
        JTextField location = new JTextField(source == null ? "" : source.getPickupLocation());
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        setDateChooserBlue(dateChooser);
        JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
        UserDTO profile = new UserDAO().getUserProfile(username);
        JTextField person = new JTextField(source == null ? nullText(profile.getFullName()) : source.getPickupPerson());
        JTextField contact = new JTextField(source == null ? nullText(profile.getPhone()) : source.getContact());
        JTextArea desc = new JTextArea(source == null ? "" : nullText(source.getDescription()), 4, 20);
        desc.setLineWrap(true);
        JComboBox<String> status = new JComboBox<>(LostItemDTO.DEFAULT_STATUSES);
        if (source != null) {
            category.setSelectedItem(source.getCategory());
            status.setSelectedItem(source.getStatus());
            Date date = Date.from(source.getPickupTime().toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant());
            dateChooser.setDate(date);
            timeSpinner.setValue(date);
        } else {
            dateChooser.setDate(new Date());
        }
        status.setEnabled(admin && !submitMode);

        addRow(panel, c, 0, "Item Name:", itemName);
        addRow(panel, c, 1, "Category:", category);
        addRow(panel, c, 2, "Found Location:", location);
        JPanel datePanel = new JPanel(new GridLayout(1, 2, 6, 0));
        datePanel.add(dateChooser);
        datePanel.add(timeSpinner);
        addRow(panel, c, 3, "Date Found:", datePanel);
        addRow(panel, c, 4, "Found By:", person);
        addRow(panel, c, 5, "Contact:", contact);
        addRow(panel, c, 6, "Status:", status);
        c.gridx = 0;
        c.gridy = 7;
        c.weightx = 0;
        panel.add(new JLabel("Item Description:"), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(new JScrollPane(desc), c);
        JButton save = new JButton(submitMode ? "Submit Report" : "Save Changes");
        styleNormalButton(save);
        c.gridx = 1;
        c.gridy = 8;
        panel.add(save, c);

        save.addActionListener(evt -> {
            LostItemDTO dto = new LostItemDTO();
            if (source != null) dto.setId(source.getId());
            dto.setItemName(itemName.getText().trim());
            dto.setCategory(String.valueOf(category.getSelectedItem()));
            dto.setPickupLocation(location.getText().trim());
            dto.setPickupPerson(person.getText().trim());
            dto.setContact(contact.getText().trim());
            dto.setDescription(desc.getText().trim());
            dto.setStatus(admin && !submitMode ? String.valueOf(status.getSelectedItem()) : "Unclaimed");
            dto.setSubmitterUsername(username);
            dto.setPickupTime(getDateTime(dateChooser, timeSpinner));
            if (!validateDto(dto)) return;
            if (submitMode) {
                dao.addLostItem(dto);
                itemName.setText("");
                location.setText("");
                desc.setText("");
            } else {
                dao.updateLostItem(dto);
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window != null) window.dispose();
                refreshByMode();
            }
        });
        return panel;
    }

    private void addRow(JPanel panel, GridBagConstraints c, int row, String label, JComponent component) {
        c.gridx = 0;
        c.gridy = row;
        c.weightx = 0;
        panel.add(new JLabel(label), c);
        c.gridx = 1;
        c.weightx = 1;
        panel.add(component, c);
    }

    private void editSelected() {
        LostItemDTO dto = selectedDto();
        if (dto == null) return;
        if (!admin && !dao.canUserEditOrDelete(dto.getId(), username)) {
            JOptionPane.showMessageDialog(this, "You can edit only your own unclaimed reports.");
            return;
        }
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Edit Lost Item", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setContentPane(buildEditor(dto, false));
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteSelected() {
        LostItemDTO dto = selectedDto();
        if (dto == null) return;
        if (!admin && !dao.canUserEditOrDelete(dto.getId(), username)) {
            JOptionPane.showMessageDialog(this, "You can delete only your own unclaimed reports.");
            return;
        }
        int opt = JOptionPane.showConfirmDialog(this, "Delete this report?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            dao.deleteLostItem(dto.getId());
            refreshByMode();
        }
    }

    private void claimSelected() {
        LostItemDTO dto = selectedDto();
        if (dto == null) return;
        if (!canClaim(dto)) {
            JOptionPane.showMessageDialog(this, "This item cannot be " + (admin ? "handed over" : "claimed") + ". It may already be claimed or handed over.");
            setClaimButtonEnabled(false);
            return;
        }
        if (admin) {
            transferSelected(dto);
            return;
        }
        UserDTO profile = new UserDAO().getUserProfile(username);
        JTextField claimant = new JTextField(nullText(profile.getFullName()), 12);
        JTextField contact = new JTextField(nullText(profile.getPhone()), 12);
        JTextField time = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        time.setEditable(false);
        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.add(new JLabel("Claimant:"));
        panel.add(claimant);
        panel.add(new JLabel("Contact:"));
        panel.add(contact);
        panel.add(new JLabel("Claim Date:"));
        panel.add(time);
        int result = JOptionPane.showConfirmDialog(this, panel, "Record Claim", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if (claimant.getText().trim().isEmpty() || contact.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter the claimant's name and contact information.");
                return;
            }
            dao.claimItem(dto.getId(), username, claimant.getText().trim(), contact.getText().trim());
            refreshByMode();
        }
    }

    private void transferSelected(LostItemDTO dto) {
        JTextField studentUsername = new JTextField(12);
        JTextField time = new JTextField(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        time.setEditable(false);
        JPanel panel = new JPanel(new GridLayout(2, 2, 8, 8));
        panel.add(new JLabel("Recipient Student ID:"));
        panel.add(studentUsername);
        panel.add(new JLabel("Handover Date:"));
        panel.add(time);
        int result = JOptionPane.showConfirmDialog(this, panel, "Record Handover", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String inputUsername = studentUsername.getText().trim();
            if (inputUsername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter the recipient's student ID.");
                return;
            }
            String matchedUsername = new UserDAO().findUsernameByUsername(inputUsername);
            UserDTO matchedProfile = matchedUsername == null ? null : new UserDAO().getUserProfile(matchedUsername);
            String transferName = matchedProfile == null || nullText(matchedProfile.getFullName()).isEmpty()
                    ? inputUsername : matchedProfile.getFullName();
            String transferContact = matchedProfile == null ? "" : nullText(matchedProfile.getPhone());
            dao.transferItem(dto.getId(), matchedUsername, transferName, transferContact);
            refreshByMode();
        }
    }

    private LostItemDTO selectedDto() {
        int row = table.getSelectedRow();
        if (row < 0 || table.getColumnCount() < 14) {
            JOptionPane.showMessageDialog(this, "Select a record first.");
            return null;
        }
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        LostItemDTO dto = new LostItemDTO();
        dto.setId((Integer) model.getValueAt(row, 0));
        dto.setItemName(String.valueOf(model.getValueAt(row, 1)));
        dto.setCategory(String.valueOf(model.getValueAt(row, 2)));
        dto.setPickupLocation(String.valueOf(model.getValueAt(row, 3)));
        dto.setPickupTime(Timestamp.valueOf(LocalDateTime.parse(String.valueOf(model.getValueAt(row, 4)), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        dto.setPickupPerson(String.valueOf(model.getValueAt(row, 5)));
        dto.setStatus(String.valueOf(model.getValueAt(row, 6)));
        dto.setClaimant(nullIfBlank(model.getValueAt(row, 7)));
        dto.setContact(String.valueOf(model.getValueAt(row, 10)));
        dto.setDescription(nullIfBlank(model.getValueAt(row, 11)));
        dto.setClaimContact(nullIfBlank(model.getValueAt(row, 12)));
        String claimTime = nullText(model.getValueAt(row, 8));
        if (!claimTime.isEmpty()) {
            dto.setClaimTime(Timestamp.valueOf(LocalDateTime.parse(claimTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
        }
        dto.setSubmitterUsername(String.valueOf(model.getValueAt(row, 9)));
        return dto;
    }

    private boolean canClaimFromModel(DefaultTableModel model, int row) {
        if (MODE_CLAIM_HISTORY.equals(mode)) return false;
        String status = nullText(model.getValueAt(row, 6)).trim();
        String claimant = nullText(model.getValueAt(row, 7)).trim();
        String claimTime = nullText(model.getValueAt(row, 8)).trim();
        return !isClosedClaimStatus(status) && claimant.isEmpty() && claimTime.isEmpty();
    }

    private boolean canClaim(LostItemDTO dto) {
        if (MODE_CLAIM_HISTORY.equals(mode)) return false;
        return !isClosedClaimStatus(dto.getStatus())
                && nullText(dto.getClaimant()).trim().isEmpty()
                && dto.getClaimTime() == null;
    }

    private boolean isClosedClaimStatus(String status) {
        String value = nullText(status).trim();
        return "Claimed".equals(value) || "Handed Over".equals(value);
    }

    private String currentOrder() {
        return orderCombo != null && "Oldest first".equals(orderCombo.getSelectedItem()) ? "ASC" : "DESC";
    }

    private Timestamp getDateTime(JDateChooser dateChooser, JSpinner timeSpinner) {
        Date date = dateChooser.getDate();
        if (date == null) return null;
        LocalDateTime d = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime t = ((Date) timeSpinner.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        return Timestamp.valueOf(LocalDateTime.of(d.toLocalDate(), t.toLocalTime().withSecond(0).withNano(0)));
    }

    private boolean validateDto(LostItemDTO dto) {
        if (dto.getItemName().isEmpty() || dto.getPickupLocation().isEmpty()
                || dto.getPickupPerson().isEmpty() || dto.getContact().isEmpty() || dto.getPickupTime() == null) {
            JOptionPane.showMessageDialog(this, "Complete all required fields.");
            return false;
        }
        return true;
    }

    private void updatePageLabel() {
        int pages = Math.max(1, (int) Math.ceil(total / (double) PAGE_SIZE));
        pageLabel.setText("Page " + page + " of " + pages + " (" + total + " items)");
    }

    private void hideColumn(int index) {
        table.getColumnModel().getColumn(index).setMinWidth(0);
        table.getColumnModel().getColumn(index).setMaxWidth(0);
        table.getColumnModel().getColumn(index).setWidth(0);
    }

    private void configureVisibleColumns() {
        int[] widths = {0, 130, 90, 130, 150, 90, 80, 90, 150, 0, 0, 0, 0, 0};
        for (int i = 0; i < widths.length && i < table.getColumnModel().getColumnCount(); i++) {
            if (widths[i] > 0) {
                table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
            }
        }
    }

    private void setDateChooserBlue(JDateChooser chooser) {
        chooser.setForeground(Color.BLUE);
        if (chooser.getDateEditor().getUiComponent() != null) {
            chooser.getDateEditor().getUiComponent().setForeground(Color.BLUE);
            chooser.getDateEditor().getUiComponent().setBackground(Color.WHITE);
        }
        setComponentTreeBlue(chooser.getJCalendar());
    }

    private void setComponentTreeBlue(Component component) {
        if (component == null) return;
        component.setForeground(Color.BLUE);
        if (component instanceof Container) {
            for (Component child : ((Container) component).getComponents()) {
                setComponentTreeBlue(child);
            }
        }
    }

    private String nullText(Object value) {
        if (value == null) return "";
        String text = String.valueOf(value);
        return "null".equals(text) ? "" : text;
    }

    private String nullIfBlank(Object value) {
        String text = nullText(value);
        return text.trim().isEmpty() ? null : text;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
