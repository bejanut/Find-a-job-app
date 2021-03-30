import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.TreeSet;

public class JobApplicationPage extends JFrame implements ListSelectionListener, ActionListener {
    JButton btnBack;
    JList educationList, experienceList, jobsList, requestsList;
    JTextArea informationText;
    private Manager manager;
    private Request<Job, Consumer> selectedRequest = null;

    public void updateJobs() {
        jobsList.setModel(getAvailableJobs(manager.availableJobs));
    }
    private DefaultListModel<String> getRequests(ArrayList<Request<Job, Consumer>> requests) {
        DefaultListModel<String> model = new DefaultListModel<>();
        DecimalFormat df = new DecimalFormat("#.##");
        for(Request<Job, Consumer> r:requests) {
            model.addElement ("" + r.getValue1().getFullName() + "-" + df.format(r.getScore()));
        }

        return model;
    }
    private DefaultListModel<String> getAvailableJobs(ArrayList<Job> jobs) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Job j:jobs) {
            model.addElement(j.name);
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

    public JobApplicationPage(Manager manager, Rectangle bounds) {
        this.manager = manager;
        this.setTitle(manager.companyName+"'s Jobs Management Page");
        this.setBounds(bounds);
        this.setMinimumSize(ApplicationGui.defaultMinimumSize());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0,0,0,0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0,0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        this.getContentPane().setLayout(gridBagLayout);

        JLabel lblReuqests = new JLabel("Requests");
        GridBagConstraints gbc_lblReuqests = new GridBagConstraints();
        gbc_lblReuqests.gridwidth = 2;
        gbc_lblReuqests.insets = new Insets(0, 0, 5, 5);
        gbc_lblReuqests.gridx = 0;
        gbc_lblReuqests.gridy = 0;
        this.getContentPane().add(lblReuqests, gbc_lblReuqests);

        JLabel lblUserInfo = new JLabel("Selected User Information");
        GridBagConstraints gbc_lblUserInfo = new GridBagConstraints();
        gbc_lblUserInfo.gridwidth = 2;
        gbc_lblUserInfo.insets = new Insets(0, 0, 5, 0);
        gbc_lblUserInfo.gridx = 2;
        gbc_lblUserInfo.gridy = 0;
        this.getContentPane().add(lblUserInfo, gbc_lblUserInfo);

        requestsList = new JList(getRequests(manager.requests));
        requestsList.setName("requests");
        requestsList.addListSelectionListener(this);
        requestsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollRequests = new JScrollPane(requestsList);
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.gridwidth = 2;
        gbc_scrollPane_1.gridheight = 2;
        gbc_scrollPane_1.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 1;
        this.getContentPane().add(scrollRequests, gbc_scrollPane_1);

        JLabel lblEducation = new JLabel("Education");
        GridBagConstraints gbc_lblEducation = new GridBagConstraints();
        gbc_lblEducation.insets = new Insets(0, 0, 5, 0);
        gbc_lblEducation.gridx = 2;
        gbc_lblEducation.gridy = 3;
        this.getContentPane().add(lblEducation, gbc_lblEducation);

        informationText = new JTextArea();
        informationText.setEditable(false);
        JScrollPane scrollInformation = new JScrollPane(informationText);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 2;
        gbc_scrollPane.gridheight = 2;
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 2;
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
        gbc_scrollPane_2.gridx = 2;
        gbc_scrollPane_2.gridy = 4;
        this.getContentPane().add(scrollEducation, gbc_scrollPane_2);

        JLabel lblOpenJobs = new JLabel("Open Jobs");
        GridBagConstraints gbc_lblOpenJobs = new GridBagConstraints();
        gbc_lblOpenJobs.gridwidth = 2;
        gbc_lblOpenJobs.insets = new Insets(0, 0, 5, 5);
        gbc_lblOpenJobs.gridx = 0;
        gbc_lblOpenJobs.gridy = 3;
        this.getContentPane().add(lblOpenJobs, gbc_lblOpenJobs);

        JLabel lblExperience = new JLabel("Experience");
        GridBagConstraints gbc_lblExperience = new GridBagConstraints();
        gbc_lblExperience.insets = new Insets(0, 0, 5, 0);
        gbc_lblExperience.gridx = 3;
        gbc_lblExperience.gridy = 3;
        this.getContentPane().add(lblExperience, gbc_lblExperience);

        jobsList = new JList(getAvailableJobs(manager.availableJobs));
        jobsList.setName("jobs");
        jobsList.addListSelectionListener(this);
        jobsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollJobs = new JScrollPane(jobsList);
        GridBagConstraints gbc_scrollPane_4 = new GridBagConstraints();
        gbc_scrollPane_4.gridwidth = 2;
        gbc_scrollPane_4.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane_4.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_4.gridx = 0;
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
        gbc_scrollPane_3.gridx = 3;
        gbc_scrollPane_3.gridy = 4;
        this.getContentPane().add(scrollExperience, gbc_scrollPane_3);

        JButton btnCreateNewJob = new JButton("Add Job");
        btnCreateNewJob.addActionListener(this);
        GridBagConstraints gbc_btnCreateNewJob = new GridBagConstraints();
        gbc_btnCreateNewJob.gridwidth = 2;
        gbc_btnCreateNewJob.insets = new Insets(0, 0, 5, 5);
        gbc_btnCreateNewJob.gridx = 0;
        gbc_btnCreateNewJob.gridy = 5;
        this.getContentPane().add(btnCreateNewJob, gbc_btnCreateNewJob);

        JButton btnAccept = new JButton("Accept");
        btnAccept.addActionListener(this);
        btnAccept.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbc_btnAccept = new GridBagConstraints();
        gbc_btnAccept.anchor = GridBagConstraints.EAST;
        gbc_btnAccept.insets = new Insets(0, 0, 5, 5);
        gbc_btnAccept.gridx = 2;
        gbc_btnAccept.gridy = 5;
        this.getContentPane().add(btnAccept, gbc_btnAccept);

        JButton btnDismiss = new JButton("Decline");
        btnDismiss.addActionListener(this);
        btnDismiss.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbc_btnDismiss = new GridBagConstraints();
        gbc_btnDismiss.insets = new Insets(0, 0, 5, 0);
        gbc_btnDismiss.anchor = GridBagConstraints.WEST;
        gbc_btnDismiss.gridx = 3;
        gbc_btnDismiss.gridy = 5;
        this.getContentPane().add(btnDismiss, gbc_btnDismiss);

        btnBack = new JButton("Back");
        btnBack.addActionListener(new BackListener());
        GridBagConstraints gbc_btnBack = new GridBagConstraints();
        gbc_btnBack.anchor = GridBagConstraints.WEST;
        gbc_btnBack.insets = new Insets(0, 0, 0, 5);
        gbc_btnBack.gridx = 0;
        gbc_btnBack.gridy = 6;
        this.getContentPane().add(btnBack, gbc_btnBack);
    }

    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()) {
            JList source = (JList) e.getSource();
            if(source.getName().equals("requests") && source.getSelectedValue() != null) {
                //se verifiva daca userul a fost sau nu angajat
                Request<Job, Consumer> selected = manager.requests.get(source.getSelectedIndex());
                if(!Application.getInstance().userList.contains((User) selected.getValue1())) {
                    JOptionPane.showMessageDialog(this,
                            "This User has already been hired!");
                    manager.requests.remove(selected);
                    //daca un user a fost angajat se for sterge toate cererile sale de angajare
                    manager.removeOtherRequests((User) selected.getValue1());
                    requestsList.setModel(getRequests(manager.requests));

                    if(requestsList.getModel().getSize() == 0)
                        informationText.setText("There are no requests at the moment.");
                    else
                        informationText.setText("Please select a request");
                    experienceList.setModel(new DefaultListModel());
                    educationList.setModel(new DefaultListModel());
                    return;
                }
                selectedRequest = selected;
                Resume usersResume = selected.getValue1().resume;
                Information info = usersResume.information;
                DecimalFormat df = new DecimalFormat("#.##");
                informationText.setText("Name:" + info.getName() +
                        "\nSurname:" + info.getSurname() +
                        "\nJob:" + selected.getKey().name +
                        "\nScore:" + df.format(selected.getScore()) +
                        "\nRecruiter:" + selected.getValue2().getFullName() +
                        "\nBirthday:" + info.getBirthday() +
                        "\nEmail:" + info.getEmail() +
                        "\nPhone:" + info.getPhone());
                educationList.setModel(getEducation(usersResume.education));
                experienceList.setModel(getExperience(usersResume.experience));
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
            if(source.getName().equals("jobs") && source.getSelectedValue() != null) {
                Job selectedJob = manager.availableJobs.get(source.getSelectedIndex());
                source.clearSelection();
                if (JOptionPane.showConfirmDialog(this,
                                "Job Name:" + selectedJob.name +
                                "\nSalary:" + selectedJob.salary + " lei"+
                                "\nNumber of Positions:" + selectedJob.noPositions
                                + "\nNumber of Applications:" + manager.numberOfApplications(selectedJob) +
                                        "\nDo you want to process this job?", selectedJob.name,
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){

                    manager.process(selectedJob);
                    jobsList.setModel(getAvailableJobs(manager.availableJobs));
                    requestsList.setModel(getRequests(manager.requests));
                    if(requestsList.getModel().getSize() == 0)
                        informationText.setText("There are no requests at the moment.");
                    else
                        informationText.setText("Please select a request");
                    experienceList.setModel(new DefaultListModel());
                    educationList.setModel(new DefaultListModel());
                    selectedRequest = null;
                }
            }
        }
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("Add Job")) {
            JFrame selection = new JobDialog(this, manager, getBounds());
            selection.setVisible(true);
            this.setEnabled(false);
        }
        if(e.getActionCommand().equals("Accept")) {
            if(selectedRequest == null) {
                JOptionPane.showMessageDialog(this,
                        "No request is selected.\nPlease select a request!");
                return;
            }
            manager.hire(selectedRequest);
            if(selectedRequest.getKey().noPositions == 0)
                manager.closeJob(selectedRequest.getKey());
            jobsList.setModel(getAvailableJobs(manager.availableJobs));
            requestsList.setModel(getRequests(manager.requests));
            if(requestsList.getModel().getSize() == 0)
                informationText.setText("There are no requests at the moment.");
            else
                informationText.setText("Please select a request");
            experienceList.setModel(new DefaultListModel());
            educationList.setModel(new DefaultListModel());
            selectedRequest = null;
        }
        if(e.getActionCommand().equals("Decline")) {
            if(selectedRequest == null) {
                JOptionPane.showMessageDialog(this,
                        "No request is selected.\nPlease select a request!");
                return;
            }
            manager.decline(selectedRequest);
            requestsList.setModel(getRequests(manager.requests));
            if(requestsList.getModel().getSize() == 0)
                informationText.setText("There are no requests at the moment.");
            else
                informationText.setText("Please select a request");
            experienceList.setModel(new DefaultListModel());
            educationList.setModel(new DefaultListModel());
            selectedRequest = null;
        }
    }
}
class Pair<T> {
        private T carry;
        private String string;

        public Pair(String string, T carry) {
            this.string = string;
            this.carry = carry;
        }

        public T getCarry() {
            return carry;
        }

        public void setCarry(T carry) {
            this.carry = carry;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }
