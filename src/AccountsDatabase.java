import java.util.Hashtable;

public class AccountsDatabase {
    private Hashtable<String, PasswordBundle> databse;
    private static AccountsDatabase instance = new AccountsDatabase();

    private AccountsDatabase() {
        databse = new Hashtable();
        databse.put("admin", new PasswordBundle("admin", new UserBundle(1, null)));
    }

    public static AccountsDatabase getInstance() {
        return instance;
    }
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        PasswordBundle bundle = databse.get(username);
        if(bundle != null && bundle.getPassword().equals(oldPassword)) {
            bundle.setPassword(newPassword);
            return true;
        } else
            return false;
    }
    public UserBundle testCredentials(String username, String password) {
        PasswordBundle bundle = databse.get(username);
        if(bundle != null && bundle.getPassword().equals(password)) {
            return bundle.getUserBundle();
        } else
            return null;
    }

    public void addConsumer(String username, String password, User user) {
        databse.put(username, new PasswordBundle(password, new UserBundle(4, user)));
    }
    public void addConsumer(String username, String password, Employee employee) {
        databse.put(username, new PasswordBundle(password, new UserBundle(3, employee)));
    }
    public void addConsumer(String username, String password, Manager manager) {
        databse.put(username, new PasswordBundle(password, new UserBundle(2, manager)));
    }

    private class PasswordBundle {
        private String password;
        private UserBundle userBundle;

        public PasswordBundle(String password, UserBundle userBundle) {
            this.password = password;
            this.userBundle = userBundle;
        }

        public String getPassword() {
            return password;
        }

        public UserBundle getUserBundle() {
            return userBundle;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    class UserBundle {
        //tipurile vor fi 1-admin, 2-manager, 3-Employees, 4-Users
        private int type;
        private Consumer extra;

        public UserBundle(int type, Consumer extra) {
            this.type = type;
            this.extra = extra;
        }

        public int getType() {
            return type;
        }

        public Consumer getExtra() {
            return extra;
        }

    }

}

