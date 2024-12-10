package librarysystem;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddMemberPanel extends MainWindowPanel {
    private JTextField memberIdField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField zipField;
    private JTextField phoneField;
    private JTextField stateField;

    private final ControllerInterface ci;

    public AddMemberPanel(BackListener backListener) {
        super(backListener);
        this.ci = new SystemController();
    }

    @Override
    public void initialize() {
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Add Member Panel");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        JPanel panel = new JPanel();
        panel.add(label);
        add(panel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(9, 2, 10, 10));

        formPanel.add(new JLabel("Member ID:"));
        memberIdField = new JTextField();
        formPanel.add(memberIdField);

        formPanel.add(new JLabel("First Name:"));
        firstNameField = new JTextField();
        firstNameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(firstNameField);

        formPanel.add(new JLabel("Last Name:"));
        lastNameField = new JTextField();
        lastNameField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(lastNameField);

        formPanel.add(new JLabel("Street:"));
        streetField = new JTextField();
        streetField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(streetField);

        formPanel.add(new JLabel("City:"));
        cityField = new JTextField();
        cityField.setPreferredSize(new Dimension(100, 30));
        formPanel.add(cityField);

        formPanel.add(new JLabel("State:"));
        stateField = new JTextField();
        formPanel.add(stateField);

        formPanel.add(new JLabel("Zip:"));
        zipField = new JTextField();
        formPanel.add(zipField);

        formPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        phoneField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(phoneField);

        add(formPanel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String memberId = memberIdField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String street = streetField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String zip = zipField.getText();
            String phone = phoneField.getText();

            // Basic validation
            if (memberId.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                JOptionPane.showMessageDialog(AddMemberPanel.this,
                        "Member ID, First Name, and Last Name are required.", "Validation Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Persist data
            try {
                ci.addNewMember(memberId, firstName, lastName, phone, street, city, state, zip);
                JOptionPane.showMessageDialog(AddMemberPanel.this, "Member added successfully!");
                clearFields();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(AddMemberPanel.this, "Error saving member: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearFields() {
        memberIdField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        streetField.setText("");
        cityField.setText("");
        stateField.setText("");
        zipField.setText("");
        phoneField.setText("");
    }
}
