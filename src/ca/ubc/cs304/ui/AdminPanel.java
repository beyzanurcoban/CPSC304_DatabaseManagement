package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.AdminPanelDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.util.SpringUtilities;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class AdminPanel extends JFrame {
    private static final int TEXT_FIELD_WIDTH = 10;

    private static final int NEW_ENTRY = 0;
    private static final int UPDATE_ENTRY = 1;
    private static final int DELETE_ENTRY = 2;
    // delegate
    private AdminPanelDelegate delegate;

    public AdminPanel() {
        super("AdminPanel - Orders");
    }

    private String[] getUpdateEntryLabels(String panelName) {
        switch (panelName) {
            case "Driver":
                return new String[]{
                        "DriverID: ",
                        "Name: ",
                        "Phone Number: ",
                        "Email: ",
                        "LicenseNumber: ",
                        "SIN: "
                };
            case "Merchant":
                return new String[]{
                        "MerchantID: ",
                        "Company Name: ",
                        "Phone Number: ",
                        "Email: ",
                        "Opening Hour: ",
                        "Closing Hour: ",
                        "City: ",
                        "Province: ",
                        "Country",
                        "Street Name: ",
                        "Street Number: ",
                        "Unit Number: ",
                        "Postal Code: ",
                        "Location Type: ",
                        "Merchant Type: "
                };
            case "Customer":
                return new String[]{
                        "CustomerID: ",
                        "Name: ",
                        "Phone Number: ",
                        "City: ",
                        "Province: ",
                        "Country: ",
                        "Street Name: ",
                        "Street Number: ",
                        "Unit Number: ",
                        "Buzz Code: ",
                        "Postal Code: ",
                        "Note: ",
                        "Property Type: "
                };
            case "Location":
                return new String[] {
                        "LocationID: ",
                        "City: ",
                        "Province: ",
                        "Street Name: ",
                        "Street Number: ",
                        "Unit Number: ",
                        "Postal Code:",
                        "Note: "
                };
            case "MerchantLocation":
                return new String[] {
                        "LocationID: ",
                        "Type: ",
                        "Landline Number: ",
                        "MerchantID: "
                };
            default:
                return new String[0];
        }
    }

    private String[] getNewEntryLabels(String panelName) {
        switch (panelName) {
            case "Order":
                return new String[]{
                        "Delivery Date: ",
                        "Delivery Address: ",
                        "Pickup Date: ",
                        "Pickup Address: ",
                        "Price: ",
                        "Payment Method: ",
                        "CustomerID: ",
                        "MerchantID: "
                };
            case "Task":
                return new String[]{
                        "Type: ",
                        "Destination: ",
                        "Complete Before: ",
                        "CompleteAfter: ",
                        "Contact Name: ",
                        "Contact Phone: ",
                        "Task Note"
                };
            case "Driver":
                return new String[]{
                        "Name: ",
                        "Phone Number: ",
                        "Email: ",
                        "LicenseNumber: ",
                        "SIN: "
                };
            case "Merchant":
                return new String[]{
                        "Company Name: ",
                        "Phone Number: ",
                        "Email: ",
                        "Opening Hour: ",
                        "Closing Hour: ",
                        "City: ",
                        "Province: ",
                        "Country",
                        "Street Name: ",
                        "Street Number: ",
                        "Unit Number: ",
                        "Postal Code: ",
                        "Location Type: ",
                        "Merchant Type: "
                };
            case "Customer":
                return new String[]{
                        "Name: ",
                        "Phone Number: ",
                        "City: ",
                        "Province: ",
                        "Country: ",
                        "Street Name: ",
                        "Street Number: ",
                        "Unit Number: ",
                        "Buzz Code: ",
                        "Postal Code: ",
                        "Note: ",
                        "Property Type: "
                };
            case "Location":
                return new String[] {
                        "City: ",
                        "Province: ",
                        "Street Name: ",
                        "Street Number: ",
                        "Unit Number: ",
                        "Postal Code:",
                        "Note: "
                };
            case "MerchantLocation":
                return new String[] {
                        "Type: ",
                        "Landline Number: ",
                        "MerchantID: "
                };
            default:
                return new String[0];
        }
    }

    private String[] getDeleteEntryLabels(String panelName) {
        switch (panelName) {
            case "Driver":
                return new String[]{
                        "DriverID: "
                };
            case "Merchant":
                return new String[]{
                        "MerchantID: "
                };
            case "Customer":
                return new String[]{
                        "CustomerID: "
                };
            case "Location":
            case "MerchantLocation":
                return new String[] {
                        "LocationID: "
                };
            default:
                return new String[0];
        }
    }

    private void changePanel(JPanel panel, String[] labels, String panelName, int mode) {
        // Components of the panel
        JButton submitBtn = new JButton("Submit");

        int numPairs = labels.length;


        // Populate the panel.
        panel.setLayout(new SpringLayout());
        panel.setPreferredSize(new Dimension(400, 350));

        if (mode == DELETE_ENTRY) {
            numPairs = 1;
        }
        for (int i = 0; i < numPairs; i++) {
            JLabel label = new JLabel(labels[i], JLabel.TRAILING);
            panel.add(label);
            JTextField textField = new JTextField(10);
            label.setLabelFor(textField);
            panel.add(textField);
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(panel,
                numPairs, 2, //rows, cols
                1, 1,        //initX, initY
                0, 0);       //xPad, yPad

        submitBtn.setFocusable(false);
        switch (mode) {
            case NEW_ENTRY:
                submitBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JButton source = (JButton) e.getSource();
                        JPanel form = (JPanel) source.getParent();
                        Component[] components = form.getComponents();

                        delegate.insertIntoTables(panelName, components);

                        // go back to main panel
                        panel.getParent().removeAll();
                        showFrame(delegate);
                    }
                });
                break;
            case UPDATE_ENTRY:
                submitBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JButton source = (JButton) e.getSource();
                        JPanel form = (JPanel) source.getParent();
                        Component[] components = form.getComponents();

                        delegate.updateEntryInTables(panelName, components);

                        // go back to main panel
                        panel.getParent().removeAll();
                        showFrame(delegate);
                    }
                });
                break;
            case DELETE_ENTRY:
                submitBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        JButton source = (JButton) e.getSource();
                        JPanel form = (JPanel) source.getParent();
                        Component[] components = form.getComponents();

                        delegate.deleteEntryFromTables(panelName, components);

                        // go back to main panel
                        panel.getParent().removeAll();
                        showFrame(delegate);
                    }
                });
                break;
            default:
                break;
        }
        panel.add(submitBtn);


        panel.updateUI();
    }

    private JPanel setUpPanel(String panelName, Object[] column) {
        // Components of the panel
        JPanel panel = new JPanel();
        JButton newEntryButton = new JButton("New " + panelName);
        JButton deleteAllButton = new JButton("Delete All");
        JButton updateButton = new JButton("Update " + panelName);
        JButton deleteButton = new JButton("Delete " + panelName);
        JScrollPane scrollPane = new JScrollPane();
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel();

        // layout components using the GridBag layout manager
        GridBagLayout gb = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();

        panel.setLayout(gb);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        // Place the NewEntry Button
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.insets = new Insets(5, 10, 5, 10);
        c.weightx = 1;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(newEntryButton, c);
        newEntryButton.setFocusable(false);
        newEntryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton source = (JButton)e.getSource();
                String panelName = source.getText().replace("New ", "");
                panel.removeAll();
                panel.updateUI();

                changePanel(panel, getNewEntryLabels(panelName), panelName, NEW_ENTRY);
            }
        });

        // Place the Update Button
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(updateButton, c);
        updateButton.setFocusable(false);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO:
                JButton source = (JButton)e.getSource();
                String panelName = source.getText().replace("Update ", "");
                panel.removeAll();
                panel.updateUI();

                changePanel(panel, getUpdateEntryLabels(panelName), panelName, UPDATE_ENTRY);
            }
        });

        // Place the Delete Button
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridx = 2;
        c.gridy = 0;
        panel.add(deleteButton, c);
        deleteButton.setFocusable(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO:
                JButton source = (JButton)e.getSource();
                String panelName = source.getText().replace("Delete ", "");
                panel.removeAll();
                panel.updateUI();

                changePanel(panel, getDeleteEntryLabels(panelName), panelName, DELETE_ENTRY);
            }
        });

        // Place the DeleteAll Button
        c.anchor = GridBagConstraints.FIRST_LINE_END;
        c.gridx = 3;
        c.gridy = 0;
        panel.add(deleteAllButton, c);
        deleteAllButton.setFocusable(false);
        deleteAllButton.setBackground(Color.red);
        deleteAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: warning/confirmation
                delegate.deleteAllFromTable(panelName);
                panel.removeAll();
                panel.updateUI();
                showFrame(delegate);
            }
        });

        // Place ScrollPane that'll hold the table
        c.anchor = GridBagConstraints.NORTH;
        c.weightx = 1;
        c.weighty = 1;
        c.ipadx = 750;
        c.ipady = 425;
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        panel.add(scrollPane, c);

        // Place Table
        table.setBackground(new Color(176, 196, 222));
        scrollPane.setViewportView(table);
        model.setColumnIdentifiers(column);
        table.setModel(model);

        ArrayList<Object[]> rows = delegate.fillTables(panelName);

        for (int i = 0; i < rows.size(); i++) {
            model.addRow(rows.get(i));
        }

        return panel;
    }

    private JPanel setUpOrderPanel() {
        Object[] column = {"ID", "Pickup address", "Pickup date", "Delivery address",
                "delivery date", "Payment method", "Price", "Status", "Completion time"};
        return setUpPanel("Order", column);
    }

    private JPanel setUpTaskPanel() {
        Object[] column = {"ID", "Destination", "CompleteBefore", "CompleteAfter", "Note", "Status"};
        return setUpPanel("Task", column);
    }

    private JPanel setUpDriverPanel() {
        Object[] column = {"ID", "Name", "Phone", "Email", "License number", "SIN"};
        return setUpPanel("Driver", column);
    }

    private JPanel setUpMerchantPanel() {
        Object[] column = {"ID", "Name", "Type", "Phone", "Email", "Sign up date", "Opening hour", "Closing hour"};
        return setUpPanel("Merchant", column);
    }

    private JPanel setUpCustomerPanel() {
        Object[] column = {"ID", "Name", "Phone", "Sign up date", "Note"};
        return setUpPanel("Customer", column);
    }

    private JPanel setupLocationPanel() {
        Object[] column = {"ID", "City", "Province", "StreetName", "StreetNumber", "UnitNumber", "Postal Code", "Note"};
        return setUpPanel("Location", column);
    }

    private JPanel setupMerchantLocationPanel() {
        Object[] column = {"ID", "Type", "Phone", "MerchantID"};
        return setUpPanel("MerchantLocation", column);
    }

    private JPanel setUpAnalyticsPanel() {
        // Components of the panel
        JPanel panel = new JPanel();
        JButton projectionButton = new JButton("Projection");
        JButton selectionButton = new JButton("Selection");
        JButton aggregationButton = new JButton("Aggregation");
        JButton nestedAggregationButton = new JButton("Nested Aggregation");
        JButton joinButton = new JButton("Join");
        JButton divisionButton = new JButton("Division");

        // layout components using the GridLayout layout manager
        GridLayout g = new GridLayout(6, 2, 5, 5);
        panel.setLayout(g);

        // Projection interface
        JPanel projPanel = new JPanel();
        GridLayout projLay = new GridLayout(2, 3, 5, 5);
        projPanel.setLayout(projLay);
        JCheckBox checkBox1 = new JCheckBox("TaskID");
        JCheckBox checkBox2 = new JCheckBox("Status");
        JCheckBox checkBox3 = new JCheckBox("Destination");
        JCheckBox checkBox4 = new JCheckBox("TaskNote");
        JCheckBox checkBox5 = new JCheckBox("DistanceTravelled");
        JCheckBox checkBox6 = new JCheckBox("DriverID");
        projPanel.add(checkBox1);
        projPanel.add(checkBox2);
        projPanel.add(checkBox3);
        projPanel.add(checkBox4);
        projPanel.add(checkBox5);
        projPanel.add(checkBox6);

        // Selection interface
        JPanel selPanel = new JPanel();
        GridLayout selLay = new GridLayout(1, 3, 5, 5);
        selPanel.setLayout(selLay);
        JLabel text1 = new JLabel("MerchantType");
        JLabel text2 = new JLabel("=");
        String [] selOps = {"Super Market", "Sports Goods", "Restaurant", "Clothing Goods"};
        JComboBox combo1 = new JComboBox(selOps);
        selPanel.add(text1);
        selPanel.add(text2);
        selPanel.add(combo1);

        // Other interfaces
        JLabel field1 = new JLabel("No options");
        JLabel field2 = new JLabel("No options");
        JLabel field3 = new JLabel("No options");
        JLabel field4 = new JLabel("No options");

        // Place the buttons
        panel.add(projectionButton);
        panel.add(projPanel);
        panel.add(selectionButton);
        panel.add(selPanel);
        panel.add(aggregationButton);
        panel.add(field1);
        panel.add(nestedAggregationButton);
        panel.add(field2);
        panel.add(joinButton);
        panel.add(field3);
        panel.add(divisionButton);
        panel.add(field4);

        projectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: projection sql query
                ArrayList<String> checked = new ArrayList<>();
                if(checkBox1.isSelected()){
                    checked.add(checkBox1.getText());
                }
                if(checkBox2.isSelected()){
                    checked.add(checkBox2.getText());
                }
                if(checkBox3.isSelected()){
                    checked.add(checkBox3.getText());
                }
                if(checkBox4.isSelected()){
                    checked.add(checkBox4.getText());
                }
                if(checkBox5.isSelected()){
                    checked.add(checkBox5.getText());
                }
                if(checkBox6.isSelected()){
                    checked.add(checkBox6.getText());
                }

                ArrayList<String> result = delegate.projection(checked);

                int rows = result.size()/checked.size();
                int columns = checked.size();

                String[] columnNames = new String[columns];
                for(int i=0; i<checked.size(); i++){
                    columnNames[i] = checked.get(i);
                }

                Object[][] data = new Object[rows][columns];

                for(int i=0; i<rows; i++) {
                    for(int j=0; j<columns; j++){
                        data[i][j] = result.get(j+(columns*i));
                    }
                }

                JTable table = new JTable(data, columnNames);
                UIManager.put("OptionPane.minimumSize",new Dimension(800,400));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
        });

        selectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: selection sql query

                ArrayList<String> result = delegate.selection((String)combo1.getSelectedItem());

                String[] columnNames = {"CompanyName",
                        "MerchantType",
                        "EmailAddress",
                        "PhoneNumber",
                        "OpeningHour",
                        "ClosingHour"};

                Object[][] data = new Object[result.size()/6][6];

                for(int i=0; i<result.size()/6; i++) {
                    data[i][0] = result.get(0+(6*i));
                    data[i][1] = result.get(1+(6*i));
                    data[i][2] = result.get(2+(6*i));
                    data[i][3] = result.get(3+(6*i));
                    data[i][4] = result.get(4+(6*i));
                    data[i][5] = result.get(5+(6*i));
                }
                JTable table = new JTable(data, columnNames);
                UIManager.put("OptionPane.minimumSize",new Dimension(800,400));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
        });

        aggregationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: aggregation sql query
                ArrayList<String> result = delegate.aggregation();

                String[] columnNames = {"MerchantID",
                        "CompanyName",
                        "SignUpDate"};

                Object[][] data = new Object[result.size()/3][3];

                for(int i=0; i<result.size()/3; i++) {
                    data[i][0] = result.get(0+(3*i));
                    data[i][1] = result.get(1+(3*i));
                    data[i][2] = result.get(2+(3*i));
                }

                JTable table = new JTable(data, columnNames);
                UIManager.put("OptionPane.minimumSize",new Dimension(800,400));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
        });

        nestedAggregationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: nested aggregation sql query
                ArrayList<String> result = delegate.nestedAggregation();

                String[] columnNames = {"AverageItemPrice",
                        "CompanyName"};

                Object[][] data = new Object[result.size()/2][2];

                for(int i=0; i<result.size()/2; i++) {
                    data[i][0] = result.get(0+(2*i));
                    data[i][1] = result.get(1+(2*i));
                }

                JTable table = new JTable(data, columnNames);
                UIManager.put("OptionPane.minimumSize",new Dimension(800,400));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
        });

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: join sql query
                ArrayList<String> result = delegate.join();

                String[] columnNames = {"ItemID",
                        "ItemName",
                        "ItemPrice",
                        "MerchantCompanyName"};

                Object[][] data = new Object[result.size()/4][4];

                for(int i=0; i<result.size()/4; i++) {
                    data[i][0] = result.get(0+(4*i));
                    data[i][1] = result.get(1+(4*i));
                    data[i][2] = result.get(2+(4*i));
                    data[i][3] = result.get(3+(4*i));
                }

                JTable table = new JTable(data, columnNames);
                UIManager.put("OptionPane.minimumSize",new Dimension(800,400));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
        });

        divisionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> result = delegate.division();

                String[] columnNames = {"MerchantID",
                        "CompanyName",
                        "SignUpDate"};

                Object[][] data = new Object[result.size()/3][3];

                for(int i=0; i<result.size()/3; i++) {
                    data[i][0] = result.get(0+(3*i));
                    data[i][1] = result.get(1+(3*i));
                    data[i][2] = result.get(2+(3*i));
                }

                JTable table = new JTable(data, columnNames);
                UIManager.put("OptionPane.minimumSize",new Dimension(800,400));
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            }
        });

        return panel;
    }

    public void showFrame(AdminPanelDelegate delegate) {
        this.delegate = delegate;

        JTabbedPane tabbedPane = new JTabbedPane();
        this.setContentPane(tabbedPane);
        this.setSize(1000, 800);
        tabbedPane.setFocusable(false);

        JPanel orderPanel = setUpOrderPanel();
        JPanel taskPanel = setUpTaskPanel();
        JPanel driverPanel = setUpDriverPanel();
        JPanel merchantPanel = setUpMerchantPanel();
        JPanel customerPanel = setUpCustomerPanel();
        JPanel locationPanel = setupLocationPanel();
        JPanel merchantLocationPanel = setupMerchantLocationPanel();
        JPanel analyticsPanel = setUpAnalyticsPanel();

        tabbedPane.add("Orders", orderPanel);
        tabbedPane.add("Tasks", taskPanel);
        tabbedPane.add("Drivers", driverPanel);
        tabbedPane.add("Merchants", merchantPanel);
        tabbedPane.add("Customers", customerPanel);
        tabbedPane.add("Location", locationPanel);
        tabbedPane.add("MerchantLocation", merchantLocationPanel);
        tabbedPane.add("Analytics", analyticsPanel);

        // anonymous inner class for closing the window
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                delegate.programExited();
            }
        });

        // center the frame
        Dimension d = this.getToolkit().getScreenSize();
        Rectangle r = this.getBounds();
        this.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

        // make the window visible
        this.setVisible(true);
    }

}
