import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    public HomePage() {
        this.setTitle("HomePage");
        this.setMinimumSize(new Dimension(500,400));
        this.setBounds(100, 100, 500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{ 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.4, 0.0, 1.0, 0.5, 2.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gridBagLayout);

        JTextField title = new JTextField();
        title.setEditable(false);
        title.setBorder(BorderFactory.createEmptyBorder());
        title.setFont(new Font("Dialog", Font.PLAIN, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setText("Select which page you want to access");
        title.setBackground(this.getBackground());
        GridBagConstraints gbc_txtSelect = new GridBagConstraints();
        gbc_txtSelect.gridwidth = 3;
        gbc_txtSelect.insets = new Insets(0, 0, 5, 5);
        gbc_txtSelect.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtSelect.gridx = 0;
        gbc_txtSelect.gridy = 1;
        this.getContentPane().add(title, gbc_txtSelect);

        JButton btnAdmin = new JButton("Admin Page");
        btnAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ApplicationGui GUI = ApplicationGui.getInstance();
                JFrame oldMain = GUI.getMainFrame();
                GUI.pushStack(oldMain);
                oldMain.dispose();
                GUI.setMainFrame(new AdminPage(oldMain.getBounds()));
                GUI.getMainFrame().setVisible(true);
            }
        });
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton.gridx = 0;
        gbc_btnNewButton.gridy = 3;
        this.getContentPane().add(btnAdmin, gbc_btnNewButton);

        JButton btnManager = new JButton("Manager Page");
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
        gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_1.gridx = 1;
        gbc_btnNewButton_1.gridy = 3;
        this.getContentPane().add(btnManager, gbc_btnNewButton_1);

        JButton btnUser = new JButton("User Page");
        GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
        gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
        gbc_btnNewButton_2.gridx = 2;
        gbc_btnNewButton_2.gridy = 3;
        this.getContentPane().add(btnUser, gbc_btnNewButton_2);
    }
}
