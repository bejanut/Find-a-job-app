import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeSet;

public class EmployeePage extends JFrame implements ListSelectionListener{
    JButton btnBack;
    Employee employee;
    JList educationList, experienceList;

    private DefaultListModel<Pair<Education>> getEducation(TreeSet<Education> education) {
        DefaultListModel<Pair<Education>> model = new DefaultListModel<>();
        for(Education e:education) {
            model.addElement(new Pair<Education>(e.educationInstitute, e));
        }
        return model;
    }
    private DefaultListModel<Pair<Experience>> getExperience(TreeSet<Experience> experience) {
        DefaultListModel<Pair<Experience>> model = new DefaultListModel<>();
        for(Experience e:experience) {
            model.addElement(new Pair<Experience>(e.company, e));
        }
        return model;
    }

    public EmployeePage(Employee employee, Rectangle bounds) {
        this.employee = employee;
        this.setTitle(employee.getFullName()+"'s Page");
        this.setBounds(bounds);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0,0,0,0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0,0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gridBagLayout);

        JLabel lblUserInfo = new JLabel("Employee Information");
        GridBagConstraints gbc_lblUserInfo = new GridBagConstraints();
        gbc_lblUserInfo.gridwidth = 2;
        gbc_lblUserInfo.insets = new Insets(0, 0, 5, 0);
        gbc_lblUserInfo.gridx = 0;
        gbc_lblUserInfo.gridy = 0;
        this.getContentPane().add(lblUserInfo, gbc_lblUserInfo);

        JLabel lblEducation = new JLabel("Education");
        GridBagConstraints gbc_lblEducation = new GridBagConstraints();
        gbc_lblEducation.insets = new Insets(0, 0, 5, 0);
        gbc_lblEducation.gridx = 0;
        gbc_lblEducation.gridy = 3;
        this.getContentPane().add(lblEducation, gbc_lblEducation);

        JTextArea informationText = new JTextArea();
        informationText.setEditable(false);
        JScrollPane scrollInformation = new JScrollPane(informationText);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridheight = 2;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 1;
        this.getContentPane().add(scrollInformation, gbc_scrollPane);

        educationList = new JList();
        educationList.setName("education");
        educationList.addListSelectionListener(this);
        educationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollEducation = new JScrollPane(educationList);
        GridBagConstraints gbc_scrollPane_2 = new GridBagConstraints();
        gbc_scrollPane_2.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane_2.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_2.gridheight = 2;
        gbc_scrollPane_2.gridx = 0;
        gbc_scrollPane_2.gridy = 4;
        this.getContentPane().add(scrollEducation, gbc_scrollPane_2);

        JLabel lblExperience = new JLabel("Experience");
        GridBagConstraints gbc_lblExperience = new GridBagConstraints();
        gbc_lblExperience.insets = new Insets(0, 0, 5, 0);
        gbc_lblExperience.gridx = 1;
        gbc_lblExperience.gridy = 3;
        this.getContentPane().add(lblExperience, gbc_lblExperience);

        experienceList = new JList();
        experienceList.setName("experience");
        experienceList.addListSelectionListener(this);
        experienceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollExperience = new JScrollPane(experienceList);
        GridBagConstraints gbc_scrollPane_3 = new GridBagConstraints();
        gbc_scrollPane_3.insets = new Insets(0, 0, 5, 0);
        gbc_scrollPane_3.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_3.gridheight = 2;
        gbc_scrollPane_3.gridx = 1;
        gbc_scrollPane_3.gridy = 4;
        this.getContentPane().add(scrollExperience, gbc_scrollPane_3);

        if(ApplicationGui.getInstance().oneFrameLeft())
            btnBack = new JButton("Log out");
        else
            btnBack = new JButton("Back");
        btnBack.addActionListener(new BackListener());
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.anchor = GridBagConstraints.WEST;
        gbc_btnBack.insets = new Insets(0, 0, 0, 5);
        gbc_btnBack.gridx = 0;
        gbc_btnBack.gridy = 6;
        this.getContentPane().add(btnBack, gbc_btnBack);

        JButton btnChangePass = new JButton("Change Password");
        btnChangePass.addActionListener(new PasswordChangeListener(employee.getFullName(), this));
        GridBagConstraints gbc_btnChange = new GridBagConstraints();
        gbc_btnChange.anchor = GridBagConstraints.EAST;
        gbc_btnChange.insets = new Insets(0, 0, 0, 5);
        gbc_btnChange.gridx = 1;
        gbc_btnChange.gridy = 6;
        this.getContentPane().add(btnChangePass, gbc_btnChange);

        Information info = employee.resume.information;
        informationText.setText("Name:" + info.getName() +
                        "\nSurname:" + info.getSurname() +
                        "\nPosition:" + employee.resume.experience.first().position +
                        "\nSalary:" + employee.salary + " lei" +
                        "\nBirthday:" + info.getBirthday() +
                        "\nEmail:" + info.getEmail() +
                        "\nPhone:" + info.getPhone() +
                        "\nisRecruiter:" + employee.isRecruiter);
        educationList.setModel(getEducation(employee.resume.education));
        experienceList.setModel(getExperience(employee.resume.experience));
    }

    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            JList source = (JList) e.getSource();
            if(source.getName().equals("education") && source.getSelectedValue() != null) {
                Education education = ((Pair<Education>)source.getSelectedValue()).getCarry();
                JOptionPane.showMessageDialog(this,
                        "Institution:" + education.educationInstitute +
                                "\nLevel:" + education.educationLevel +
                                "\nGrade:" + education.finalGrade +
                                "\nBetween:" + education.begin + " - " + education.end
                );
                source.clearSelection();
            }
            if(source.getName().equals("experience") && source.getSelectedValue() != null) {
                Experience experience = ((Pair<Experience>)source.getSelectedValue()).getCarry();
                JOptionPane.showMessageDialog(this,
                        "Company:" + experience.company +
                                "\nPosition:" + experience.position +
                                "\nBetween:" + experience.begin + " - " + experience.end
                );
                source.clearSelection();
            }
        }
    }
}
