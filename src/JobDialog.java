import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class JobDialog extends JFrame implements ActionListener, MouseListener {
    private JTextField jobName;
    private JTextField graduationLow, graduationHigh,
            experinceLow, experienceHigh,
            gradeLow, gradeHigh,
            salary, poNumber;
    private JLabel lblJobName, lblGraduationYear, spacer;
    private Department department;
    private Manager manager;
    private JobApplicationPage parent;

    public JobDialog(JobApplicationPage mPage, Manager manager, Rectangle bounds) {
        parent = mPage;
        this.manager = manager;
        Company company = Application.getInstance().getCompany(manager.companyName);

        this.addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parent.setEnabled(true);
            }
        });

        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setTitle("Choose a Department");
        this.setBounds((int)bounds.getX()+100, (int)bounds.getY()+100, 300, 200);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gridBagLayout);

        JButton btnIt = new JButton("IT");
        if(company.getDepartment("IT") == null)
            btnIt.setEnabled(false);
        else
            btnIt.addActionListener(this);
        GridBagConstraints gbc_btnIt = new GridBagConstraints();
        gbc_btnIt.fill = GridBagConstraints.BOTH;
        gbc_btnIt.insets = new Insets(0, 0, 5, 5);
        gbc_btnIt.gridx = 0;
        gbc_btnIt.gridy = 0;
        this.getContentPane().add(btnIt, gbc_btnIt);

        JButton btnFinance = new JButton("Finance");
        if(company.getDepartment("Finance") == null)
            btnFinance.setEnabled(false);
        else
            btnFinance.addActionListener(this);
        GridBagConstraints gbc_btnFinance = new GridBagConstraints();
        gbc_btnFinance.fill = GridBagConstraints.BOTH;
        gbc_btnFinance.insets = new Insets(0, 0, 5, 5);
        gbc_btnFinance.gridx = 1;
        gbc_btnFinance.gridy = 0;
        this.getContentPane().add(btnFinance, gbc_btnFinance);

        JButton btnMarketing = new JButton("Marketing");
        if(company.getDepartment("Marketing") == null)
            btnMarketing.setEnabled(false);
        else
            btnMarketing.addActionListener(this);
        GridBagConstraints gbc_btnMarketing = new GridBagConstraints();
        gbc_btnMarketing.fill = GridBagConstraints.BOTH;
        gbc_btnMarketing.insets = new Insets(0, 0, 0, 5);
        gbc_btnMarketing.gridx = 0;
        gbc_btnMarketing.gridy = 1;
        this.getContentPane().add(btnMarketing, gbc_btnMarketing);

        JButton btnManagement = new JButton("Management");
        if(company.getDepartment("Management") == null)
            btnManagement.setEnabled(false);
        else
            btnManagement.addActionListener(this);
        GridBagConstraints gbc_btnManagement = new GridBagConstraints();
        gbc_btnManagement.fill = GridBagConstraints.BOTH;
        gbc_btnManagement.insets = new Insets(0, 0, 0, 5);
        gbc_btnManagement.gridx = 1;
        gbc_btnManagement.gridy = 1;
        this.getContentPane().add(btnManagement, gbc_btnManagement);
    }

    public JobDialog(JobApplicationPage mPage, Manager manager, Department department, Rectangle bounds) {
        parent = mPage;
        this.department = department;
        this.manager = manager;

        this.addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parent.setEnabled(true);
            }
        });
        this.setTitle("Create New Job");
        this.setAlwaysOnTop(true);
        this.setBounds((int)bounds.getX(), (int)bounds.getY(), 350, 210);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0,0,0,0};
        gridBagLayout.columnWeights = new double[]{0.0, 3.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0,0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gridBagLayout);

        lblJobName = new JLabel("Job Name:");
        GridBagConstraints gbc_lblJobName = new GridBagConstraints();
        gbc_lblJobName.insets = new Insets(0, 0, 5, 5);
        gbc_lblJobName.gridx = 0;
        gbc_lblJobName.gridy = 0;
        this.getContentPane().add(lblJobName, gbc_lblJobName);

        jobName = new JTextField();
        GridBagConstraints gbc_jobName = new GridBagConstraints();
        gbc_jobName.gridwidth = 3;
        gbc_jobName.insets = new Insets(0, 0, 5, 5);
        gbc_jobName.fill = GridBagConstraints.HORIZONTAL;
        gbc_jobName.gridx = 1;
        gbc_jobName.gridy = 0;
        this.getContentPane().add(jobName, gbc_jobName);
        jobName.setColumns(1);

        lblGraduationYear = new JLabel("Graduation Year:");
        GridBagConstraints gbc_lblGraduationYear = new GridBagConstraints();
        gbc_lblGraduationYear.insets = new Insets(0, 0, 5, 5);
        gbc_lblGraduationYear.gridx = 0;
        gbc_lblGraduationYear.gridy = 1;
        this.getContentPane().add(lblGraduationYear, gbc_lblGraduationYear);

        graduationLow = new JTextField();
        GridBagConstraints gbc_graduationLow = new GridBagConstraints();
        gbc_graduationLow.insets = new Insets(0, 0, 5, 5);
        gbc_graduationLow.fill = GridBagConstraints.HORIZONTAL;
        gbc_graduationLow.gridx = 1;
        gbc_graduationLow.gridy = 1;
        this.getContentPane().add(graduationLow, gbc_graduationLow);
        graduationLow.setColumns(1);

        spacer = new JLabel("<->");
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.insets = new Insets(0, 0, 5, 5);
        gbc_label.anchor = GridBagConstraints.EAST;
        gbc_label.gridx = 2;
        gbc_label.gridy = 1;
        this.getContentPane().add(spacer, gbc_label);

        graduationHigh = new JTextField();
        GridBagConstraints gbc_graduationHigh = new GridBagConstraints();
        gbc_graduationHigh.insets = new Insets(0, 0, 5, 0);
        gbc_graduationHigh.fill = GridBagConstraints.HORIZONTAL;
        gbc_graduationHigh.gridx = 3;
        gbc_graduationHigh.gridy = 1;
        this.getContentPane().add(graduationHigh, gbc_graduationHigh);
        graduationHigh.setColumns(1);

        JLabel lblExperienceYears = new JLabel("Experience Years:");
        GridBagConstraints gbc_lblExperienceYears = new GridBagConstraints();
        gbc_lblExperienceYears.insets = new Insets(0, 0, 5, 5);
        gbc_lblExperienceYears.gridx = 0;
        gbc_lblExperienceYears.gridy = 2;
        this.getContentPane().add(lblExperienceYears, gbc_lblExperienceYears);

        experinceLow = new JTextField();
        GridBagConstraints gbc_experienceLow = new GridBagConstraints();
        gbc_experienceLow.insets = new Insets(0, 0, 5, 5);
        gbc_experienceLow.fill = GridBagConstraints.HORIZONTAL;
        gbc_experienceLow.gridx = 1;
        gbc_experienceLow.gridy = 2;
        this.getContentPane().add(experinceLow, gbc_experienceLow);
        experinceLow.setColumns(1);

        JLabel spacer2 = new JLabel("<->");
        GridBagConstraints gbc_label2 = new GridBagConstraints();
        gbc_label2.insets = new Insets(0, 0, 5, 5);
        gbc_label2.anchor = GridBagConstraints.EAST;
        gbc_label2.gridx = 2;
        gbc_label2.gridy = 2;
        this.getContentPane().add(spacer2, gbc_label2);

        experienceHigh = new JTextField();
        GridBagConstraints gbc_experienceHigh = new GridBagConstraints();
        gbc_experienceHigh.insets = new Insets(0, 0, 5, 0);
        gbc_experienceHigh.fill = GridBagConstraints.HORIZONTAL;
        gbc_experienceHigh.gridx = 3;
        gbc_experienceHigh.gridy = 2;
        this.getContentPane().add(experienceHigh, gbc_experienceHigh);
        experienceHigh.setColumns(1);

        JLabel lblGrade = new JLabel("Grade:");
        GridBagConstraints gbc_lblGrade = new GridBagConstraints();
        gbc_lblGrade.insets = new Insets(0, 0, 5, 5);
        gbc_lblGrade.gridx = 0;
        gbc_lblGrade.gridy = 3;
        this.getContentPane().add(lblGrade, gbc_lblGrade);

        gradeLow = new JTextField();
        GridBagConstraints gbc_gradeLow = new GridBagConstraints();
        gbc_gradeLow.insets = new Insets(0, 0, 5, 5);
        gbc_gradeLow.fill = GridBagConstraints.HORIZONTAL;
        gbc_gradeLow.gridx = 1;
        gbc_gradeLow.gridy = 3;
        this.getContentPane().add(gradeLow, gbc_gradeLow);
        gradeLow.setColumns(1);

        JLabel spacer3 = new JLabel("<->");
        GridBagConstraints gbc_label3 = new GridBagConstraints();
        gbc_label3.insets = new Insets(0, 0, 5, 5);
        gbc_label3.anchor = GridBagConstraints.EAST;
        gbc_label3.gridx = 2;
        gbc_label3.gridy = 3;
        this.getContentPane().add(spacer3, gbc_label3);

        gradeHigh = new JTextField();
        GridBagConstraints gbc_gradeHigh = new GridBagConstraints();
        gbc_gradeHigh.insets = new Insets(0, 0, 5, 0);
        gbc_gradeHigh.fill = GridBagConstraints.HORIZONTAL;
        gbc_gradeHigh.gridx = 3;
        gbc_gradeHigh.gridy = 3;
        this.getContentPane().add(gradeHigh, gbc_gradeHigh);
        gradeHigh.setColumns(1);

        JLabel lblSalary = new JLabel("Salary:");
        GridBagConstraints gbc_lblSalary = new GridBagConstraints();
        gbc_lblSalary.insets = new Insets(0, 0, 5, 5);
        gbc_lblSalary.gridx = 0;
        gbc_lblSalary.gridy = 4;
        this.getContentPane().add(lblSalary, gbc_lblSalary);

        salary = new JTextField();
        GridBagConstraints gbc_salary = new GridBagConstraints();
        gbc_salary.gridwidth = 3;
        gbc_salary.insets = new Insets(0, 0, 5, 0);
        gbc_salary.fill = GridBagConstraints.HORIZONTAL;
        gbc_salary.gridx = 1;
        gbc_salary.gridy = 4;
        this.getContentPane().add(salary, gbc_salary);
        salary.setColumns(1);

        JLabel lblNumberOfPositions = new JLabel("Number of positions:");
        GridBagConstraints gbc_lblNumberOfPositions = new GridBagConstraints();
        gbc_lblNumberOfPositions.insets = new Insets(0, 0, 0, 5);
        gbc_lblNumberOfPositions.gridx = 0;
        gbc_lblNumberOfPositions.gridy = 5;
        this.getContentPane().add(lblNumberOfPositions, gbc_lblNumberOfPositions);

        poNumber = new JTextField();
        GridBagConstraints gbc_poNumber = new GridBagConstraints();
        gbc_poNumber.gridwidth = 3;
        gbc_poNumber.insets = new Insets(0, 0, 5, 0);
        gbc_poNumber.fill = GridBagConstraints.HORIZONTAL;
        gbc_poNumber.gridx = 1;
        gbc_poNumber.gridy = 5;
        this.getContentPane().add(poNumber, gbc_poNumber);
        poNumber.setColumns(1);

        JButton btnCreate = new JButton("Create");
        btnCreate.addActionListener(this);
        GridBagConstraints gbc_btnCreate = new GridBagConstraints();
        gbc_btnCreate.fill = GridBagConstraints.BOTH;
        gbc_btnCreate.gridwidth = 2;
        gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
        gbc_btnCreate.gridx = 0;
        gbc_btnCreate.gridy = 6;
        this.getContentPane().add(btnCreate, gbc_btnCreate);

        JButton btnCancel = new JButton("Cancel");
        GridBagConstraints gbc_btnCancel = new GridBagConstraints();
        gbc_btnCancel.fill = GridBagConstraints.BOTH;
        gbc_btnCancel.gridwidth = 2;
        gbc_btnCancel.insets = new Insets(0, 0, 0, 5);
        gbc_btnCancel.gridx = 2;
        gbc_btnCancel.gridy = 6;
        this.getContentPane().add(btnCancel, gbc_btnCancel);

        jobName.addMouseListener(this);
        graduationLow.addMouseListener(this);
        graduationHigh.addMouseListener(this);
        gradeLow.addMouseListener(this);
        gradeHigh.addMouseListener(this);
        experinceLow.addMouseListener(this);
        experienceHigh.addMouseListener(this);
        salary.addMouseListener(this);
        poNumber.addMouseListener(this);
        btnCancel.addActionListener(this);
    }

    private boolean isACorectTF(JTextField TF) {
        if(isNumeric(TF.getText()))
            return true;
        else {
            TF.setForeground(Color.RED);
            TF.setText("error");
            return false;
        }
    }
    private Job createJob() {
        return new Job(jobName.getText(),
                manager.companyName, true,
                new Constraint<>(getInteger(graduationLow), getInteger(graduationHigh)),
                new Constraint<>(getInteger(experinceLow),getInteger(experienceHigh)),
                new Constraint<>(getDouble(gradeLow), getDouble(gradeHigh)),
                getInteger(salary),
                getInteger(poNumber));
    }
    private Double getDouble(JTextField TF) {
        if(TF.getText().equals("") || TF.getText().equals("null"))
            return null;
        else
            return Double.parseDouble(TF.getText());
    }
    private Integer getInteger(JTextField TF) {
        if(TF.getText().equals("") || TF.getText().equals("null"))
            return null;
        else
            return Integer.parseInt(TF.getText());
    }
    public static boolean isNumeric(String str) {
        if(str.equals("") || str.equals("null"))
            return true;
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static boolean isNumericWithoutNull(String str) {
        return str.matches("-?\\d+(\\d+)?");
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Create")) {
            boolean isCorect = true;
            if(jobName.getText().equals("")) {
                jobName.setForeground(Color.RED);
                jobName.setText("Enter a name");
                isCorect = false;
            }

            isCorect = isACorectTF(graduationLow) && isCorect;
            isCorect = isACorectTF(graduationHigh) && isCorect;
            isCorect = isACorectTF(experinceLow) && isCorect;
            isCorect = isACorectTF(experienceHigh) && isCorect;
            isCorect = isACorectTF(gradeLow) && isCorect;
            isCorect = isACorectTF(gradeHigh) && isCorect;
            if(!isNumericWithoutNull(salary.getText())) {
                salary.setForeground(Color.RED);
                salary.setText("error");
                isCorect = false;
            }
            if(!isNumericWithoutNull(poNumber.getText())) {
                poNumber.setForeground(Color.RED);
                poNumber.setText("error");
                isCorect = false;
            }
            if(isCorect) {
                manager.add(createJob(), department);
                JOptionPane.showMessageDialog(this, "Job created successfully");
                parent.updateJobs();
                parent.setEnabled(true);
                dispose();
            }
        } else if(e.getActionCommand().equals("Cancel")){
            parent.setEnabled(true);
            dispose();
        } else {
            Company company = Application.getInstance().getCompany(manager.companyName);
            Department selectedDepartment = company.getDepartment(e.getActionCommand());
            JFrame goToJobCreation = new JobDialog(parent, manager, selectedDepartment, getBounds());
            goToJobCreation.setVisible(true);
            this.dispose();
        }

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        JTextField textField = (JTextField) e.getSource();
        if(textField.getForeground().equals(Color.RED)) {
            textField.setForeground(Color.BLACK);
            textField.setText("");
        }
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
