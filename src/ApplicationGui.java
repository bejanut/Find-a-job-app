import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import javax.swing.*;

public class ApplicationGui{
    private static final ApplicationGui instance = new ApplicationGui();
    private JFrame mainFrame;
    private Stack<JFrame> history = new Stack<>();

    public static void main(String[] args) throws Exception {
        Test.main(args);
        ApplicationGui application = getInstance();
        application.mainFrame.setVisible(true);
    }

    private ApplicationGui() {
        mainFrame = new LoginPage();
    }
    public static ApplicationGui getInstance() {
        return instance;
    }
    public static Rectangle defaultBounds(int x, int y) {
        return new Rectangle(x, y, 500, 400);
    }
    public static Dimension defaultMinimumSize() {
        return new Dimension(500, 400);
    }
    public JFrame getMainFrame() {
        return mainFrame;
    }
    public void setMainFrame(JFrame frame) {
        mainFrame = frame;
    }
    public JFrame popStack() {
        return history.pop();
    }
    public void pushStack(JFrame frame) {
        history.push(frame);
    }
    public boolean oneFrameLeft() {
        return history.size() == 1;
    }
}

class PasswordChangeListener implements ActionListener {
    String username;
    JFrame parent;

    public PasswordChangeListener(String username, JFrame parent) {
        super();
        this.username = username;
        this.parent = parent;
    }
    public void actionPerformed(ActionEvent e) {
        String oldPassword = (String)JOptionPane.showInputDialog(
                ApplicationGui.getInstance().getMainFrame(),
                "Enter old password",
                "Verify password",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
        String newPassword = (String)JOptionPane.showInputDialog(
                ApplicationGui.getInstance().getMainFrame(),
                "Enter new password",
                "Changing password",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);
        if(AccountsDatabase.getInstance().changePassword(username,
                oldPassword,
                newPassword)) {
            JOptionPane.showMessageDialog(parent,
                    "Password changed succesfully!",
                    "Password changing status",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(parent,
                    "Old password entered is incorrect!",
                    "Password changing status",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
class BackListener implements ActionListener{

    public void actionPerformed(ActionEvent e) {
        ApplicationGui GUI = ApplicationGui.getInstance();
        JFrame oldMain = GUI.getMainFrame(), mainFrame;
        oldMain.dispose();
        mainFrame = GUI.popStack();
        Rectangle r = oldMain.getBounds();
        if(e.getActionCommand().equals("Back")) {
            mainFrame.setBounds(r);
        } else
            mainFrame.setBounds((int)r.getX(), (int)r.getY(), 300, 200);
        mainFrame.setVisible(true);
        GUI.setMainFrame(mainFrame);
    }
}