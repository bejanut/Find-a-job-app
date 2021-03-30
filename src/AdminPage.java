import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage extends JFrame {
    JList usersList;

    public AdminPage(Rectangle bounds) {
        this.setTitle("AdminPage");
        this.setBounds(bounds);
        this.setMinimumSize(new Dimension(500,400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));


        GridBagLayout gbl_adminPanel = new GridBagLayout();
        gbl_adminPanel.columnWidths = new int[]{0, 0, 0};
        gbl_adminPanel.rowHeights = new int[]{0, 0, 0, 0};
        gbl_adminPanel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_adminPanel.rowWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl_adminPanel);

        JButton btnLogOut = new JButton("Log out");
        btnLogOut.addActionListener(new BackListener());
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.anchor = GridBagConstraints.WEST;
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton.gridx = 0;
        gbc_btnNewButton.gridy = 2;
        this.add(btnLogOut, gbc_btnNewButton);

        JLabel companyLabel = new JLabel("Companies");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        this.add(companyLabel, gbc_lblNewLabel);

        JLabel usersLabel = new JLabel("Users");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 0;
        this.add(usersLabel, gbc_lblNewLabel_1);

        JList companyList = new JList(getCompanyModel());
        usersList = new JList(getUserModel());

        usersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        usersList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    JList source = (JList) e.getSource();
                    if(source.getSelectedValue() != null) {
                        User user = Application.getInstance()
                                .userList.get(source.getSelectedIndex());
                        Information selectedInfo = user.resume.information;
                        String buttons[] = new String[]{"Visit Page", "Cancel"};
                        int option = JOptionPane.showOptionDialog(ApplicationGui.getInstance().getMainFrame(),
                                "Name:" + selectedInfo.getName() +
                                        "\nSurname:" + selectedInfo.getSurname() +
                                        "\nBirthday:" + selectedInfo.getBirthday() +
                                        "\nEmail:" + selectedInfo.getEmail() +
                                        "\nPhone:" + selectedInfo.getPhone(),
                                user.getFullName() + " selected",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                buttons,
                                null
                        );
                        if(option == 0) {
                            ApplicationGui GUI = ApplicationGui.getInstance();
                            JFrame oldMain = GUI.getMainFrame(), mainFrame;
                            oldMain.setVisible(false);
                            GUI.pushStack(oldMain);
                            mainFrame = new UserPage(user ,ApplicationGui.defaultBounds(getX(), getY()));
                            mainFrame.setVisible(true);
                            GUI.setMainFrame(mainFrame);
                        }

                    }
                }
            }
        });

        companyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        companyList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {
                    JList source = (JList) e.getSource();
                    if(source.getSelectedValue() != null) {
                        ApplicationGui GUI = ApplicationGui.getInstance();
                        JFrame oldMain = GUI.getMainFrame(), mainFrame;
                        String selectedCompany = source.getSelectedValue().toString();
                        oldMain.setVisible(false);
                        GUI.pushStack(oldMain);
                        mainFrame = new ManagerPage(selectedCompany, oldMain.getBounds());
                        mainFrame.setVisible(true);
                        GUI.setMainFrame(mainFrame);
                        source.clearSelection();
                    }
                }
            }
        });

        JScrollPane companyScroll = new JScrollPane(companyList);
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 1;
        this.add(companyScroll, gbc_scrollPane_1);

        JScrollPane usersScroll = new JScrollPane(usersList);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 1;
        this.add(usersScroll, gbc_scrollPane);

        JButton btnChangePass = new JButton("Change Password");
        btnChangePass.addActionListener(new PasswordChangeListener("admin", this));
        GridBagConstraints gbc_btnChange = new GridBagConstraints();
        gbc_btnChange.anchor = GridBagConstraints.EAST;
        gbc_btnChange.insets = new Insets(0, 0, 0, 5);
        gbc_btnChange.gridx = 1;
        gbc_btnChange.gridy = 2;
        this.getContentPane().add(btnChangePass, gbc_btnChange);
    }

    private DefaultListModel<String> getCompanyModel() {
        DefaultListModel<String> companyModel = new DefaultListModel<>();
        for(Company company:Application.getInstance().getCompanies()) {
            companyModel.addElement(company.name);
        }
        return companyModel;
    }
    private DefaultListModel<String> getUserModel() {
        DefaultListModel<String> userModel = new DefaultListModel<>();
        for(User u:Application.getInstance().userList) {
            userModel.addElement(u.getFullName());
        }
        return userModel;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        usersList.setModel(getUserModel());
    }
}
