import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame implements ActionListener {
    JPasswordField txtPassword;
    JTextField txtId;

    public LoginPage() {
        this.setTitle("Login");
        this.setMinimumSize(new Dimension(300,200));
        this.setBounds(100, 100, 300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gridBagLayout);

        JLabel lblId = new JLabel("ID");
        lblId.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_lblId = new GridBagConstraints();
        gbc_lblId.insets = new Insets(0, 0, 5, 0);
        gbc_lblId.anchor = GridBagConstraints.WEST;
        gbc_lblId.gridx = 0;
        gbc_lblId.gridy = 0;
        this.getContentPane().add(lblId, gbc_lblId);

        txtId = new JTextField();
        GridBagConstraints gbc_txtId = new GridBagConstraints();
        gbc_txtId.insets = new Insets(0, 0, 5, 0);
        gbc_txtId.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtId.gridx = 0;
        gbc_txtId.gridy = 1;
        this.getContentPane().add(txtId, gbc_txtId);
        txtId.setColumns(10);

        JLabel lblPassword = new JLabel("Password");
        GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.insets = new Insets(0, 0, 5, 0);
        gbc_lblPassword.anchor = GridBagConstraints.WEST;
        gbc_lblPassword.gridx = 0;
        gbc_lblPassword.gridy = 2;
        this.getContentPane().add(lblPassword, gbc_lblPassword);

        txtPassword = new JPasswordField();
        GridBagConstraints gbc_txtPassword = new GridBagConstraints();
        gbc_txtPassword.insets = new Insets(0, 0, 5, 0);
        gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtPassword.gridx = 0;
        gbc_txtPassword.gridy = 3;
        this.getContentPane().add(txtPassword, gbc_txtPassword);
        txtPassword.setColumns(10);

        JButton button = new JButton("Login");
        button.addActionListener(this);
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.gridx = 0;
        gbc_button.gridy = 4;
        this.getContentPane().add(button, gbc_button);
    }

    public void actionPerformed(ActionEvent e) {
        AccountsDatabase database = AccountsDatabase.getInstance();
        AccountsDatabase.UserBundle user = database.testCredentials(txtId.getText(),
                String.valueOf(txtPassword.getPassword()));
        txtPassword.setText("");
        if(user == null) {
            JOptionPane.showMessageDialog(this,
                    "Wrong username or password!",
                    "Wrong credientials",
                    JOptionPane.ERROR_MESSAGE);
        }
        else {
            txtId.setText("");
            ApplicationGui GUI = ApplicationGui.getInstance();
            JFrame oldMain = GUI.getMainFrame(), mainFrame;
            oldMain.setVisible(false);
            GUI.pushStack(oldMain);
            switch (user.getType()) {
                case 1:{
                    mainFrame = new AdminPage(ApplicationGui.defaultBounds(getX(), getY()));
                    mainFrame.setVisible(true);
                    GUI.setMainFrame(mainFrame);
                    return;
                }
                case 2:{
                    mainFrame = new ManagerPage(((Manager) user.getExtra()).companyName,
                            ApplicationGui.defaultBounds(getX(), getY()));
                    mainFrame.setVisible(true);
                    GUI.setMainFrame(mainFrame);
                    return;}
                case 3:{
                    mainFrame = new EmployeePage(((Employee) user.getExtra()),
                            ApplicationGui.defaultBounds(getX(), getY()));
                    mainFrame.setVisible(true);
                    GUI.setMainFrame(mainFrame);
                    return;}
                case 4:{
                    mainFrame = new UserPage((User) user.getExtra(),
                            ApplicationGui.defaultBounds(getX(), getY()));
                    mainFrame.setVisible(true);
                    GUI.setMainFrame(mainFrame);
                    return;}
            }
        }
    }
}
