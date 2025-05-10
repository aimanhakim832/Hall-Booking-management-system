package com.group.oodjAssignment.manager;

//ManagerGUI.java
import com.group.oodjAssignment.LoginForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ManagerGUI extends JFrame {

    private Manager manager;
    private ArrayList<SalesData> salesDataList;
    private ArrayList<MaintenanceIssue> maintenanceIssues;
    private String[] staffNames = {"Ali", "Ahmed", "Omar", "Danial"};  // Schedulers

    public ManagerGUI(Manager manager, ArrayList<SalesData> salesDataList, ArrayList<MaintenanceIssue> maintenanceIssues) {
        this.manager = manager;
        this.salesDataList = salesDataList;
        this.maintenanceIssues = maintenanceIssues;
        createGUI();
    }

    private void createGUI() {
        setTitle("Manager Dashboard");
        setSize(400, 300);  // Smaller size for the login window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window on the screen

        // Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());  // Using GridBagLayout for better control over alignment
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Padding around components

        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");
        JTextField txtUsername = new JTextField(15);
        JPasswordField txtPassword = new JPasswordField(15);
        JButton btnLogin = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(lblUsername, gbc);

        gbc.gridx = 1;
        loginPanel.add(txtUsername, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(lblPassword, gbc);

        gbc.gridx = 1;
        loginPanel.add(txtPassword, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(btnLogin, gbc);

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));  // Adding spacing between buttons
        JButton btnViewSales = new JButton("View Sales Data");
        JButton btnManageIssues = new JButton("Manage Maintenance Issues");
        JButton btnLogout = new JButton("Logout");

        mainPanel.add(btnViewSales);
        mainPanel.add(btnManageIssues);
        mainPanel.add(btnLogout);

        // Font improvements for buttons
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        btnViewSales.setFont(buttonFont);
        btnManageIssues.setFont(buttonFont);
        btnLogout.setFont(buttonFont);

        // Add panels to frame
//        add(loginPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Login Action
//        btnLogin.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                String username = txtUsername.getText();
//                String password = new String(txtPassword.getPassword());
//                if (manager.getUsername().equals(username) && manager.checkPassword(password)) {
//                    remove(loginPanel);
//                    setSize(500, 400);  // Resize for the main dashboard
//                    setLocationRelativeTo(null);  // Center the window again
//                    add(mainPanel, BorderLayout.CENTER);
//                    revalidate();
//                    repaint();
//                } else {
//                    JOptionPane.showMessageDialog(null, "Invalid login credentials!");
//                }
//            }
//        });
        // View Sales Data Action
        btnViewSales.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSalesData();
            }
        });

        // Manage Maintenance Issues Action
        btnManageIssues.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                manageMaintenanceIssues();
            }
        });

        // Logout Action
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
//                remove(mainPanel);
//                setSize(400, 300);  // Resize back to login window size
//                setLocationRelativeTo(null);
//                add(loginPanel, BorderLayout.CENTER);
//                revalidate();
//                repaint();
                new LoginForm().setVisible(true);
                dispose();
            }
        });
    }

    // Function to display sales data (includes yearly sales)
    private void showSalesData() {
        StringBuilder salesInfo = new StringBuilder("Sales Data:\n");
        for (SalesData data : salesDataList) {
            salesInfo.append(data.getTimePeriod()).append(": RM ").append(data.getTotalSales()).append("\n");
        }
        JOptionPane.showMessageDialog(null, salesInfo.toString());
    }

    // Function to manage maintenance issues, includes assigning schedulers
    private void manageMaintenanceIssues() {
        String[] issueDescriptions = new String[maintenanceIssues.size()];
        for (int i = 0; i < maintenanceIssues.size(); i++) {
            issueDescriptions[i] = maintenanceIssues.get(i).getIssueDescription() + " (" + maintenanceIssues.get(i).getStatus() + ")";
        }

        String selectedIssue = (String) JOptionPane.showInputDialog(null, "Select an issue to manage:",
                "Maintenance Issues", JOptionPane.QUESTION_MESSAGE, null, issueDescriptions, issueDescriptions[0]);

        for (MaintenanceIssue issue : maintenanceIssues) {
            if (selectedIssue.contains(issue.getIssueDescription())) {
                // Assigning staff
                String assignedStaff = (String) JOptionPane.showInputDialog(null, "Assign a scheduler (staff):",
                        "Assign Staff", JOptionPane.QUESTION_MESSAGE, null, staffNames, staffNames[0]);

                String[] options = {"In Progress", "Done", "Closed", "Cancelled"};
                String newStatus = (String) JOptionPane.showInputDialog(null, "Set the status:",
                        "Update Issue Status", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

                issue.setStatus(newStatus);
                JOptionPane.showMessageDialog(null, "Issue assigned to " + assignedStaff + " and status updated to " + newStatus);
            }
        }
    }
}
