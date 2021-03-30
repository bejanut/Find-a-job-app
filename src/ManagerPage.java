import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class ManagerPage extends JFrame implements ActionListener, ListSelectionListener, MouseListener {
    private Department selectedDepartment = null;
    private Employee selectedEmployee = null;
    private JList mainList, departmentsList;
    private Company company;
    private JLabel titleLabel;
    private JTextField textField;
    private JButton btnGetEmployees, btnSeeAvailablejobs;
    private JFrame departmentPicker = null;

    public ManagerPage(String companyName, Rectangle bounds) {
        company = Application.getInstance().getCompany(companyName);
        selectedDepartment = null;

        this.setTitle(company.manager.getFullName() +"'s page");
        this.setBounds(bounds);
        this.setMinimumSize(new Dimension(500,400));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        GridBagLayout gbl_adminPanel = new GridBagLayout();
        gbl_adminPanel.columnWidths = new int[]{0, 0, 0, 0};
        gbl_adminPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_adminPanel.columnWeights = new double[]{0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_adminPanel.rowWeights = new double[]{0.0, 0.5, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl_adminPanel);

        titleLabel = new JLabel("Employees");
        GridBagConstraints gbc_titleLabel = new GridBagConstraints();
        gbc_titleLabel.insets = new Insets(0, 0, 5, 0);
        gbc_titleLabel.gridwidth = 2;
        gbc_titleLabel.gridx = 1;
        gbc_titleLabel.gridy = 0;
        this.add(titleLabel, gbc_titleLabel);

        JLabel departmentsLabel = new JLabel("Departments");
        departmentsLabel.addMouseListener(this);
        GridBagConstraints gbc_departmentsLabel = new GridBagConstraints();
        gbc_departmentsLabel.insets = new Insets(0, 0, 5, 5);
        gbc_departmentsLabel.gridx = 0;
        gbc_departmentsLabel.gridy = 0;
        this.add(departmentsLabel, gbc_departmentsLabel);

        mainList = new JList<>();
        mainList.setName("main");
        JScrollPane mainScroll = new JScrollPane(mainList);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridheight = 8;
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 1;
        this.add(mainScroll, gbc_scrollPane);

        JButton btnMoveDepartment = new JButton("Move Department");
        btnMoveDepartment.addActionListener(this);
        GridBagConstraints gbc_btnMoveDepartment = new GridBagConstraints();
        gbc_btnMoveDepartment.anchor = GridBagConstraints.NORTH;
        gbc_btnMoveDepartment.insets = new Insets(0, 0, 0, 5);
        gbc_btnMoveDepartment.gridx = 0;
        gbc_btnMoveDepartment.gridy = 3;
        this.add(btnMoveDepartment, gbc_btnMoveDepartment);

        textField = new JTextField();
        textField.setHorizontalAlignment(SwingConstants.CENTER);
        textField.setText("0 lei");
        GridBagConstraints gbc_txtLei = new GridBagConstraints();
        gbc_txtLei.insets = new Insets(0, 0, 5, 5);
        gbc_txtLei.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtLei.gridx = 0;
        gbc_txtLei.gridy = 7;
        this.add(textField, gbc_txtLei);
        textField.setColumns(10);

        btnGetEmployees = new JButton("Get Employees");
        btnGetEmployees.setEnabled(false);
        btnGetEmployees.addActionListener(this);
        GridBagConstraints gbc_btnGetEmployees = new GridBagConstraints();
        gbc_btnGetEmployees.insets = new Insets(0, 0, 5, 5);
        gbc_btnGetEmployees.gridx = 0;
        gbc_btnGetEmployees.gridy = 4;
        this.add(btnGetEmployees, gbc_btnGetEmployees);

        btnSeeAvailablejobs = new JButton("See Available Jobs");
        btnSeeAvailablejobs.addActionListener(this);
        GridBagConstraints gbc_btnSeeAvailablejobs = new GridBagConstraints();
        gbc_btnSeeAvailablejobs.insets = new Insets(0, 0, 5, 5);
        gbc_btnSeeAvailablejobs.gridx = 0;
        gbc_btnSeeAvailablejobs.gridy = 5;
        this.add(btnSeeAvailablejobs, gbc_btnSeeAvailablejobs);

        departmentsList = new JList<>(getDepartmentModel(company.departments));
        departmentsList.setName("departments");
        departmentsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        departmentsList.addListSelectionListener(this);
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.gridheight = 2;
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 1;
        this.add(departmentsList, gbc_scrollPane_1);

        JLabel lblTotalSalary = new JLabel("Total Salary");
        GridBagConstraints gbc_lblTotalSalary = new GridBagConstraints();
        gbc_lblTotalSalary.insets = new Insets(0, 0, 5, 5);
        gbc_lblTotalSalary.anchor = GridBagConstraints.SOUTH;
        gbc_lblTotalSalary.gridx = 0;
        gbc_lblTotalSalary.gridy = 6;
        this.add(lblTotalSalary, gbc_lblTotalSalary);

        JButton btnCalculate = new JButton("Calculate");
        btnCalculate.addActionListener(this);
        GridBagConstraints gbc_btnCalculate = new GridBagConstraints();
        gbc_btnCalculate.insets = new Insets(0, 0, 0, 5);
        gbc_btnCalculate.anchor = GridBagConstraints.NORTH;
        gbc_btnCalculate.gridx = 0;
        gbc_btnCalculate.gridy = 8;
        this.add(btnCalculate, gbc_btnCalculate);

        JButton btnJobApplications = new JButton("Job Applications");
        btnJobApplications.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ApplicationGui GUI = ApplicationGui.getInstance();
                JFrame oldMain = GUI.getMainFrame(), mainFrame;

                oldMain.setVisible(false);
                GUI.pushStack(oldMain);
                mainFrame = new JobApplicationPage(company.manager, getBounds());
                mainFrame.setVisible(true);
                GUI.setMainFrame(mainFrame);
            }
        });
        GridBagConstraints gbc_btnJobApplication = new GridBagConstraints();
        gbc_btnJobApplication.insets = new Insets(0, 0, 0, 5);
        gbc_btnJobApplication.gridx = 1;
        gbc_btnJobApplication.gridy = 9;
        this.add(btnJobApplications, gbc_btnJobApplication);

        JButton btnChangePass = new JButton("Change Password");
        btnChangePass.addActionListener(new PasswordChangeListener(company.manager.getFullName(), this));
        GridBagConstraints gbc_btnChange = new GridBagConstraints();
        gbc_btnChange.anchor = GridBagConstraints.EAST;
        gbc_btnChange.insets = new Insets(0, 0, 0, 5);
        gbc_btnChange.gridx = 2;
        gbc_btnChange.gridy = 9;
        this.add(btnChangePass, gbc_btnChange);

        JButton btnBack;
        if(ApplicationGui.getInstance().oneFrameLeft())
            btnBack = new JButton("Log out");
        else
            btnBack = new JButton("Back");
        btnBack.addActionListener(new BackListener());
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.anchor = GridBagConstraints.SOUTHWEST;
        gbc_btnBack.insets = new Insets(0, 0, 0, 5);
        gbc_btnBack.gridx = 0;
        gbc_btnBack.gridy = 9;
        this.add(btnBack, gbc_btnBack);
    }

    private DefaultListModel<String> getDepartmentModel(ArrayList<Department> departments) {
        DefaultListModel<String> departmentModel = new DefaultListModel<>();
        for(Department d:departments) {
            departmentModel.addElement(d.departmentName);
        }
        return departmentModel;
    }
    private DefaultListModel<String> getEmployeesModel(Department department) {
        DefaultListModel<String> employeeModel = new DefaultListModel<>();
        for(Employee e:department.getEmployees()) {
            employeeModel.addElement(e.getFullName());
        }

        return employeeModel;
    }
    private DefaultListModel<String> getJobsModel(Department department) {
        DefaultListModel<String> employeeModel = new DefaultListModel<>();
        for(Job j:department.getJobs()) {
            employeeModel.addElement(j.name);
        }

        return employeeModel;
    }

    private void departmentPicker(JFrame parent) {
        JFrame frame = new JFrame();
        departmentPicker = frame;
        frame.addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                parent.setEnabled(true);
                departmentPicker = null;
            }
        });

        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setTitle("Choose a Department");
        frame.setBounds(getX()+100, getY()+100, 300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);

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
        frame.getContentPane().add(btnIt, gbc_btnIt);

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
        frame.getContentPane().add(btnFinance, gbc_btnFinance);

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
        frame.getContentPane().add(btnMarketing, gbc_btnMarketing);

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
        frame.getContentPane().add(btnManagement, gbc_btnManagement);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("IT")) {
            if(selectedDepartment == company.getDepartment("IT"))
                JOptionPane.showMessageDialog(this,
                        "Already in this department!");
            else {
                selectedEmployee.resume.experience.first().department = "IT";
                company.move(selectedEmployee, company.getDepartment("IT"));
                selectedDepartment.remove(selectedEmployee);
                this.setEnabled(true);
                departmentPicker.dispose();
                departmentPicker = null;
                mainList.setModel(getEmployeesModel(selectedDepartment));
            }
        }
        if(e.getActionCommand().equals("Finance")) {
            if(selectedDepartment == company.getDepartment("Finance"))
                JOptionPane.showMessageDialog(this,
                        "Already in this department!");
            else {
                selectedEmployee.resume.experience.first().department = "Finance";
                company.move(selectedEmployee, company.getDepartment("Finance"));
                selectedDepartment.remove(selectedEmployee);
                this.setEnabled(true);
                departmentPicker.dispose();
                departmentPicker = null;
                mainList.setModel(getEmployeesModel(selectedDepartment));
            }
        }
        if(e.getActionCommand().equals("Marketing")) {
            if(selectedDepartment == company.getDepartment("Marketing"))
                JOptionPane.showMessageDialog(this,
                        "Already in this department!");
            else {
                selectedEmployee.resume.experience.first().department = "Marketing";
                company.move(selectedEmployee, company.getDepartment("Marketing"));
                selectedDepartment.remove(selectedEmployee);
                this.setEnabled(true);
                departmentPicker.dispose();
                departmentPicker = null;
                mainList.setModel(getEmployeesModel(selectedDepartment));
            }
        }
        if(e.getActionCommand().equals("Management")) {
            if(selectedDepartment == company.getDepartment("Management"))
                JOptionPane.showMessageDialog(this,
                        "Already in this department!");
            else {
                selectedEmployee.resume.experience.first().department = "Management";
                company.move(selectedEmployee, company.getDepartment("Management"));
                selectedDepartment.remove(selectedEmployee);
                this.setEnabled(true);
                departmentPicker.dispose();
                departmentPicker = null;
                mainList.setModel(getEmployeesModel(selectedDepartment));
            }
        }
        if(selectedDepartment == null) {
            JOptionPane.showMessageDialog(this,
                    "Department not selected!\nPlease select a department!");
            return;
        }
        if(e.getActionCommand().equals("Calculate")){
            double cost = selectedDepartment.getTotalSalaryBudget();
            DecimalFormat df = new DecimalFormat("#.##");
            textField.setText(df.format(cost) + " lei");
        }
        if(e.getActionCommand().equals("Get Employees")
                && !titleLabel.getText().equals("Employees")) {
            mainList.removeListSelectionListener(this);
            mainList.setModel(getEmployeesModel(selectedDepartment));
            mainList.addListSelectionListener(this);
            titleLabel.setText("Employees");
            btnSeeAvailablejobs.setEnabled(true);
            btnGetEmployees.setEnabled(false);
        }
        if(e.getActionCommand().equals("See Available Jobs")
                && !titleLabel.getText().equals("Available Jobs")) {
            mainList.removeListSelectionListener(this);
            mainList.setModel(getJobsModel(selectedDepartment));
            mainList.addListSelectionListener(this);
            titleLabel.setText("Available Jobs");
            btnSeeAvailablejobs.setEnabled(false);
            btnGetEmployees.setEnabled(true);
        }
        if(e.getActionCommand().equals("Move Department")) {
            ArrayList<Department> departments = company.departments;
            ArrayList possibilities = new ArrayList();
            for(Department d:departments) {
                possibilities.add(d.departmentName);
            }
            possibilities.remove(selectedDepartment.departmentName);
            if(possibilities.isEmpty()) {
                JOptionPane.showMessageDialog(ApplicationGui.getInstance().getMainFrame(),
                        "There are no other departments apart from the one selected");
                return;
            }
            String s = (String)JOptionPane.showInputDialog(
                    ApplicationGui.getInstance().getMainFrame(),
                    "Choose where to move the " +
                            selectedDepartment.departmentName +
                            " departemnt.",
                    "Move Department",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities.toArray(),
                    possibilities.get(0));
            if(s != null){
                Department department = company.getDepartment(s);
                company.move(selectedDepartment, department);
                departmentsList.setModel(getDepartmentModel(company.departments));
                mainList.setModel(new DefaultListModel());
                selectedDepartment = null;
            }
        }
    }
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            JList source = (JList)e.getSource();
            if(source.getName().equals("departments") && source.getSelectedValue() != null) {
                selectedDepartment = company.getDepartment(source.getSelectedValue().toString());

                if(titleLabel.getText().equals("Available Jobs")) {
                    titleLabel.setText("Employees");
                    btnSeeAvailablejobs.setEnabled(true);
                    btnGetEmployees.setEnabled(false);
                }
                mainList.removeListSelectionListener(this);
                mainList.setModel(getEmployeesModel(selectedDepartment));
                mainList.addListSelectionListener(this);
                textField.setText("0 lei");
                source.clearSelection();
            } else {
                if(titleLabel.getText().equals("Available Jobs") && source.getSelectedValue() != null) {
                    jobDialog(selectedDepartment.getJobs().get(source.getSelectedIndex()));
                    source.clearSelection();
                }
                else {
                    if(source.getSelectedValue() != null) {
                        selectedEmployee = selectedDepartment.getEmployee(source.getSelectedValue().toString());
                        source.clearSelection();
                        employeeDialog(selectedEmployee);
                    }
                }
            }
        }
    }
    private void employeeDialog(Employee employee) {
        if(employee == null)
            return;
        String[] options;
        if(employee.isRecruiter())
            options = new String[]{"Change Department",
                    "Fire",
                    "Make Normal Employee"};
        else
            options = new String[]{"Change Department",
                    "Fire",
                    "Make Recruiter"};
        Information info = employee.resume.information;
        int option = JOptionPane.showOptionDialog(this,
                "Name:" + info.getName() +
                        "\nSurname:" + info.getSurname() +
                        "\nPosition:" + employee.resume.experience.first().position +
                        "\nSalary:" + employee.salary + " lei" +
                        "\nBirthday:" + info.getBirthday() +
                        "\nEmail:" + info.getEmail() +
                        "\nPhone:" + info.getPhone() +
                        "\nisRecruiter:" + employee.isRecruiter,
                "Edit Employee status",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]
        );
        if(option == 0) {
            this.setEnabled(false);
            departmentPicker(this);
        }
        if(option == 1) {
            if(selectedEmployee.isRecruiter || company.recruiters.size() == 1) {
                JOptionPane.showMessageDialog(this,
                        "This is the only recruiter left! You cant fire him!",
                        "Last Recruiter",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            company.remove(selectedEmployee);
            mainList.setModel(getEmployeesModel(selectedDepartment));
        }
        if(option == 2) {
            if(employee.isRecruiter()) {
                if(company.recruiters.size() == 1) {
                    JOptionPane.showMessageDialog(this,
                            "This is the only recruiter left! You cant remove him!",
                            "Last Recruiter",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                for(Recruiter r:company.recruiters) {
                    if(r.compareTo(employee) == 0) {
                        company.remove(r);
                        break;
                    }
                }
                employee.isRecruiter = false;
            } else {
                employee.isRecruiter = true;
                company.add(new Recruiter(employee));
            }
        }
    }
    private void jobDialog(Job job) {
        if(job == null)
            return;
        if (JOptionPane.showConfirmDialog(this,
                "Job Name:" + job.name +
                        "\nSalary:" + job.salary + " lei"+
                        "\nNumber of Positions:" + job.noPositions
                        + "\nNumber of Applications:" + company.manager.numberOfApplications(job) +
                        "\nDo you want to process this job?", job.name,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

            company.manager.process(job);
            mainList.setModel(getJobsModel(selectedDepartment));
        }
    }

    public void mouseClicked(MouseEvent e) {
        ArrayList<Department> departments = company.departments;
        String defaultDepartments[] = {"IT", "Finance", "Management", "Marketing"};
        ArrayList possibilities = new ArrayList(Arrays.asList(defaultDepartments));
        for(Department d:departments) {
            possibilities.remove(d.departmentName);
        }
        if(possibilities.isEmpty()) {
            JOptionPane.showMessageDialog(ApplicationGui.getInstance().getMainFrame(),
                    "There are no other departments you can add!",
                    "No Departments Left",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        String s = (String)JOptionPane.showInputDialog(
                ApplicationGui.getInstance().getMainFrame(),
                "Choose which department you want to add:",
                "Add Department",
                JOptionPane.PLAIN_MESSAGE,
                null,
                possibilities.toArray(),
                possibilities.get(0));
        if(s != null){
            company.add(DepartmentFactory.createNewDepartment(s));
            departmentsList.setModel(getDepartmentModel(company.departments));
            mainList.setModel(new DefaultListModel());
            selectedDepartment = null;
        }
    }
    public void mousePressed(MouseEvent e) {

    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
}
