import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeSet;

public class UserPage extends JFrame implements ListSelectionListener, ActionListener {
    JButton btnBack;
    User user;
    JList educationList, experienceList, companiesList, notificationsList;

    private DefaultListModel<String> getNotifications(ArrayList<ObserverNotification> notifications) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for(ObserverNotification n: notifications) {
            model.addElement (n.getSubject());
        }
        return model;
    }
    private DefaultListModel<String> getCompanies(ArrayList<String> companies) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for(String s: companies) {
            model.addElement (s);
        }
        return model;
    }
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

    public UserPage(User user, Rectangle bounds) {
        this.user = user;
        this.setTitle(user.getFullName()+"'s Page");
        this.setBounds(bounds);
        this.setMinimumSize(ApplicationGui.defaultMinimumSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0,0,0,0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0,0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gridBagLayout);

        JLabel lblNotifications = new JLabel("Notifications");
        GridBagConstraints gbc_lblReuqests = new GridBagConstraints();
        gbc_lblReuqests.gridwidth = 2;
        gbc_lblReuqests.insets = new Insets(0, 0, 5, 5);
        gbc_lblReuqests.gridx = 2;
        gbc_lblReuqests.gridy = 0;
        this.getContentPane().add(lblNotifications, gbc_lblReuqests);

        JLabel lblUserInfo = new JLabel("User Information");
        GridBagConstraints gbc_lblUserInfo = new GridBagConstraints();
        gbc_lblUserInfo.gridwidth = 2;
        gbc_lblUserInfo.insets = new Insets(0, 0, 5, 0);
        gbc_lblUserInfo.gridx = 0;
        gbc_lblUserInfo.gridy = 0;
        this.getContentPane().add(lblUserInfo, gbc_lblUserInfo);

        notificationsList = new JList(getNotifications(user.observerNotificantions));
        notificationsList.setName("notifications");
        notificationsList.addListSelectionListener(this);
        notificationsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollRequests = new JScrollPane(notificationsList);
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.gridwidth = 2;
        gbc_scrollPane_1.gridheight = 2;
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 2;
        gbc_scrollPane_1.gridy = 1;
        this.getContentPane().add(scrollRequests, gbc_scrollPane_1);

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

        JLabel lblOpenJobs = new JLabel("Companies of Interest");
        GridBagConstraints gbc_lblOpenJobs = new GridBagConstraints();
        gbc_lblOpenJobs.gridwidth = 2;
        gbc_lblOpenJobs.insets = new Insets(0, 0, 5, 5);
        gbc_lblOpenJobs.gridx = 2;
        gbc_lblOpenJobs.gridy = 3;
        this.getContentPane().add(lblOpenJobs, gbc_lblOpenJobs);

        JLabel lblExperience = new JLabel("Experience");
        GridBagConstraints gbc_lblExperience = new GridBagConstraints();
        gbc_lblExperience.insets = new Insets(0, 0, 5, 0);
        gbc_lblExperience.gridx = 1;
        gbc_lblExperience.gridy = 3;
        this.getContentPane().add(lblExperience, gbc_lblExperience);

        companiesList = new JList(getCompanies(user.interestedCompany));
        companiesList.setName("companies");
        companiesList.addListSelectionListener(this);
        companiesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollJobs = new JScrollPane(companiesList);
        GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
        gbc_scrollPane_4.gridwidth = 2;
        gbc_scrollPane_4.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_4.gridx = 2;
        gbc_scrollPane_4.gridy = 4;
        this.getContentPane().add(scrollJobs, gbc_scrollPane_4);

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

        JButton btnCreateNewJob = new JButton("Add company of interest");
        btnCreateNewJob.addActionListener(this);
        GridBagConstraints gbc_btnCreateNewJob = new GridBagConstraints();
        gbc_btnCreateNewJob.gridwidth = 2;
        gbc_btnCreateNewJob.insets = new Insets(0, 0, 5, 5);
        gbc_btnCreateNewJob.gridx = 2;
        gbc_btnCreateNewJob.gridy = 5;
        this.getContentPane().add(btnCreateNewJob, gbc_btnCreateNewJob);

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
        btnChangePass.addActionListener(new PasswordChangeListener(user.getFullName(), this));
        GridBagConstraints gbc_btnChange = new GridBagConstraints();
        gbc_btnChange.anchor = GridBagConstraints.EAST;
        gbc_btnChange.insets = new Insets(0, 0, 0, 5);
        gbc_btnChange.gridx = 3;
        gbc_btnChange.gridy = 6;
        this.getContentPane().add(btnChangePass, gbc_btnChange);

        Information info = user.resume.information;
        informationText.setText("Name:" + info.getName() +
                "\nSurname:" + info.getSurname() +
                "\nBirthday:" + info.getBirthday() +
                "\nEmail:" + info.getEmail() +
                "\nPhone:" + info.getPhone());
        educationList.setModel(getEducation(user.resume.education));
        experienceList.setModel(getExperience(user.resume.experience));
    }

    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            JList source = (JList) e.getSource();
            if(source.getName().equals("notifications") && source.getSelectedValue() != null) {
                ObserverNotification notification = user.observerNotificantions.get(source.getSelectedIndex());
                if(notification.getSubject().equals("New Job Available")) {
                    String buttons[] = new String[]{"Keep", "Apply to job", "Delete"};
                    int option = JOptionPane.showOptionDialog(this,
                            notification.getMessage(),
                            notification.getSubject(),
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            buttons,
                            null
                    );
                    if(option == 1) {
                        Job job = (Job) notification.getCarry();
                        if(job.isOpen) {
                            job.apply(user);
                            JOptionPane.showMessageDialog(this, "Application Complete");
                        } else
                            JOptionPane.showMessageDialog(this, "Job is no longer available!,",
                                    "Job not available", JOptionPane.ERROR_MESSAGE);
                        option = 2;
                    }
                    if(option == 2) {
                        user.observerNotificantions.remove(notification);
                        notificationsList.setModel(getNotifications(user.observerNotificantions));
                    }
                } else {
                        String buttons[] = new String[]{"Keep", "Delete"};
                        int option = JOptionPane.showOptionDialog(this,
                                notification.getMessage(),
                                notification.getSubject(),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                buttons,
                                null
                        );
                        if(option == 1) {
                            user.observerNotificantions.remove(notification);
                            notificationsList.setModel(getNotifications(user.observerNotificantions));
                        }
                }
                source.clearSelection();
            }
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
            if(source.getName().equals("companies") && source.getSelectedValue() != null) {
                String company = user.interestedCompany.get(source.getSelectedIndex());
                int option = JOptionPane.showOptionDialog(this,
                        "Would you like to remove this company from your interests?",
                        company,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null
                );
                if(option == 0) {
                    user.interestedCompany.remove(company);
                    Application.getInstance().getCompany(company).removeObserver(user);
                    companiesList.setModel(getCompanies(user.interestedCompany));
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("Add company of interest")) {
            ArrayList<Company> companies = Application.getInstance().getCompanies();
            ArrayList possibilities = new ArrayList();
            for(Company c:companies) {
                possibilities.add(c.name);
            }
            possibilities.removeAll(user.interestedCompany);
            if(possibilities.isEmpty()) {
                JOptionPane.showMessageDialog(ApplicationGui.getInstance().getMainFrame(),
                        "There are no companies left to follow");
                return;
            }
            String s = (String)JOptionPane.showInputDialog(
                    ApplicationGui.getInstance().getMainFrame(),
                    "Choose a new company of interest",
                    "Add company",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    possibilities.toArray(),
                    possibilities.get(0));
            if(s != null){
                user.interestedCompany.add(s);
                Application.getInstance().getCompany(s).addObserver(user);
                companiesList.setModel(getCompanies(user.interestedCompany));
            }
        }
    }
}